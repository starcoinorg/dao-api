package org.starcoin.dao.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.starcoin.dao.data.model.*;
import org.starcoin.dao.data.repo.DaoRepository;
import org.starcoin.dao.data.repo.DaoVotingResourceRepository;
import org.starcoin.dao.data.repo.ProposalRepository;
import org.starcoin.dao.utils.JsonRpcClient;
import org.starcoin.dao.vo.GetVotingPowerResponse;
import org.starcoin.types.AccountAddress;
import org.starcoin.utils.AccountAddressUtils;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.starcoin.dao.utils.MiscUtils.getResourceFieldAsBigInteger;

@Service
public class VotingPowerQueryService {
    private static final Logger LOG = LoggerFactory.getLogger(VotingPowerQueryService.class);

    @Autowired
    private DaoSbtSettings daoSbtSettings;

    @Autowired
    private JsonRpcClient jsonRpcClient;

    @Autowired
    private ProposalRepository proposalRepository;

    @Autowired
    private DaoVotingResourceRepository daoVotingResourceRepository;

    @Autowired
    private DaoStrategyService daoStrategyService;

    @Autowired
    private DaoRepository daoRepository;

    public static List<DaoVotingResource> getDaoVotingResources(String daoId, String daoTypeTag,
                                                                String strategyId,
                                                                DaoVotingResourceRepository daoVotingResourceRepository,
                                                                DaoSbtSettings daoSbtSettings) {
        List<DaoVotingResource> daoVotingResources;
        if (strategyId != null) {
            if (Strategy.STRATEGY_ID_SBT.equals(strategyId)) {
                DaoVotingResource daoVotingResource = new DaoVotingResource();
                daoVotingResource.setDaoVotingResourceId(new DaoVotingResourceId(daoId, "0"));
                daoVotingResource.setResourceStructTag(String.format(daoSbtSettings.getDaoSbtResourceStructTagFormat(), daoTypeTag));
                daoVotingResource.setVotingPowerBcsPath(daoSbtSettings.getIdentifierNftSbtValueBcsPath());
                daoVotingResources = Collections.singletonList(daoVotingResource);
            } else if (Strategy.STRATEGY_ID_VOTING_RESOURCES.equals(strategyId)) {
                daoVotingResources = daoVotingResourceRepository.findByDaoVotingResourceId_DaoId(daoId);
            } else {
                throw new IllegalStateException("Unsupported DaoStrategy.");
            }
        } else {
            // no strategy, is this ok?
            daoVotingResources = Collections.emptyList();
        }

        return daoVotingResources;
    }

    public GetVotingPowerResponse getAccountVotingPower(String accountAddress, String daoId, String proposalNumber) {
        AccountAddress addressObj = AccountAddressUtils.create(accountAddress);// make sure it is legal
        Optional<Proposal> proposal = proposalRepository.findById(new ProposalId(daoId, proposalNumber));
        if (!proposal.isPresent()) {
            return null;
        }
        return getAccountVotingPowerByStateRoot(addressObj, daoId, proposalNumber, proposal.get().getBlockStateRoot());
    }

    private GetVotingPowerResponse getAccountVotingPowerByStateRoot(AccountAddress accountAddress,
                                                                    String daoId,
                                                                    String proposalNumber,
                                                                    String stateRoot) {
        BigInteger totalVotingPower = BigInteger.ZERO;
        GetVotingPowerResponse r = new GetVotingPowerResponse();
        List<DaoVotingResource> daoVotingResources = getDaoVotingResources(daoId, proposalNumber);
        List<GetVotingPowerResponse.VotingPowerDetail> details = new ArrayList<>();
        for (DaoVotingResource daoVotingResource : daoVotingResources) {
            GetVotingPowerResponse.VotingPowerDetail detail = new GetVotingPowerResponse.VotingPowerDetail();
            BigInteger detailVotingPower = getResourceFieldAsBigInteger(jsonRpcClient, stateRoot,
                    AccountAddressUtils.hex(accountAddress), daoVotingResource.getResourceStructTag(),
                    daoVotingResource.getVotingPowerBcsPath());

            detail.setSequenceId(daoVotingResource.getDaoVotingResourceId().getSequenceId());
            detail.setResourceStructTag(daoVotingResource.getResourceStructTag());
            detail.setPower(detailVotingPower);
            details.add(detail);
            totalVotingPower = totalVotingPower.add(detailVotingPower);
        }
        r.setDetails(details);
        r.setTotalVotingPower(totalVotingPower);
        return r;
    }

    private List<DaoVotingResource> getDaoVotingResources(String daoId,
                                                          String proposalNumber// ignored proposalNumber!
    ) {
        Dao dao = daoRepository.findById(daoId).orElseThrow(() -> new IllegalStateException("Dao not found."));
        DaoStrategy daoStrategy = daoStrategyService.getPrimaryDaoStrategy(daoId);
        String strategyId = daoStrategy != null ? daoStrategy.getDaoStrategyId().getStrategyId() : null;
        return getDaoVotingResources(daoId, dao.getDaoTypeTag(), strategyId);
    }

    public List<DaoVotingResource> getDaoVotingResources(String daoId, String daoTypeTag, String strategyId) {
        return getDaoVotingResources(daoId, daoTypeTag, strategyId, daoVotingResourceRepository, daoSbtSettings);
    }

}
