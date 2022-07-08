package org.starcoin.dao.service;

import com.novi.serde.DeserializationError;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.starcoin.bcs.sab.BCSPath;
import org.starcoin.bcs.sab.ParseException;
import org.starcoin.bean.RpcStateWithProof;
import org.starcoin.dao.api.utils.JsonRpcClient;
import org.starcoin.dao.data.model.*;
import org.starcoin.dao.data.repo.DaoRepository;
import org.starcoin.dao.data.repo.DaoVotingResourceRepository;
import org.starcoin.dao.data.repo.ProposalRepository;
import org.starcoin.dao.vo.GetVotingPowerResponse;
import org.starcoin.types.AccountAddress;
import org.starcoin.utils.AccountAddressUtils;
import org.starcoin.utils.HexUtils;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

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

    public GetVotingPowerResponse getAccountVotingPower(String accountAddress, String daoId, String proposalNumber) {
        AccountAddress addressObj = AccountAddressUtils.create(accountAddress);// make sure it is legal
        Optional<Proposal> proposal = proposalRepository.findById(new ProposalId(daoId, proposalNumber));
        if (!proposal.isPresent()) {
            return null;
        }
        return getAccountVotingPowerByStateRoot(addressObj, daoId, proposal.get().getBlockStateRoot());
    }

    private GetVotingPowerResponse getAccountVotingPowerByStateRoot(AccountAddress accountAddress, String daoId, String stateRoot) {
        BigInteger totalVotingPower = BigInteger.ZERO;
        GetVotingPowerResponse r = new GetVotingPowerResponse();
        List<DaoVotingResource> daoVotingResources = getDaoVotingResources(daoId);
        List<GetVotingPowerResponse.VotingPowerDetail> details = new ArrayList<>();
        for (DaoVotingResource daoVotingResource : daoVotingResources) {
            GetVotingPowerResponse.VotingPowerDetail detail = new GetVotingPowerResponse.VotingPowerDetail();
            RpcStateWithProof stateWithProof = jsonRpcClient.getStateWithProofByRoot(
                    JsonRpcClient.getResourceStateAccessPath(
                            AccountAddressUtils.hex(accountAddress), daoVotingResource.getResourceStructTag()),
                    stateRoot);
            BigInteger detailVotingPower = BigInteger.ZERO;
            if (stateWithProof.getState() != null) {
                byte[] state = HexUtils.hexToByteArray(stateWithProof.getState());
                if (state.length > 0) {
                    Object amount = null;
                    try {
                        amount = BCSPath.select(state, daoVotingResource.getVotingPowerBcsPath());
                    } catch (DeserializationError e) {
                        throw new IllegalStateException("BCSPath.select DeserializationError.", e);
                    } catch (ParseException e) {
                        throw new IllegalStateException("BCSPath.select ParseException.", e);
                    }
                    detailVotingPower = (BigInteger) amount;
                }
            }
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

    private List<DaoVotingResource> getDaoVotingResources(String daoId) {
        DaoStrategy daoStrategy = daoStrategyService.getPrimaryDaoStrategy(daoId);
        if (daoStrategy != null) {
            if (DaoStrategy.STRATEGY_ID_SBT.equals(daoStrategy.getDaoStrategyId().getStrategyId())) {
                Dao dao = daoRepository.findById(daoId).orElseThrow(() -> new IllegalStateException("Dao not found."));
                DaoVotingResource daoVotingResource = new DaoVotingResource();
                daoVotingResource.setDaoVotingResourceId(new DaoVotingResourceId(daoId, "0"));
                daoVotingResource.setResourceStructTag(String.format(daoSbtSettings.getDaoSbtResourceStructTagFormat(), dao.getDaoTypeTag()));
                daoVotingResource.setVotingPowerBcsPath(daoSbtSettings.getIdentifierNftSbtValueBcsPath());
                return Collections.singletonList(daoVotingResource);
            } else if (DaoStrategy.STRATEGY_ID_VOTING_RESOURCES.equals(daoStrategy.getDaoStrategyId().getStrategyId())) {
                //
            } else {
                throw new IllegalStateException("Unsupported DaoStrategy.");
            }
        }
        List<DaoVotingResource> daoVotingResources = daoVotingResourceRepository.findByDaoVotingResourceId_DaoId(daoId);
        return daoVotingResources;
    }

}