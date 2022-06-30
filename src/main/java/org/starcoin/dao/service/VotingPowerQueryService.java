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
import org.starcoin.dao.data.model.DaoVotingResource;
import org.starcoin.dao.data.model.Proposal;
import org.starcoin.dao.data.model.ProposalId;
import org.starcoin.dao.data.repo.DaoVotingResourceRepository;
import org.starcoin.dao.data.repo.ProposalRepository;
import org.starcoin.dao.vo.GetVotingPowerResponse;
import org.starcoin.utils.HexUtils;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class VotingPowerQueryService {
    private static final Logger LOG = LoggerFactory.getLogger(VotingPowerQueryService.class);

    @Autowired
    private JsonRpcClient jsonRpcClient;

    @Autowired
    private ProposalRepository proposalRepository;

    @Autowired
    private DaoVotingResourceRepository daoVotingResourceRepository;

    public GetVotingPowerResponse getAccountVotingPower(String accountAddress, String daoId, String proposalNumber) {
        Optional<Proposal> proposal = proposalRepository.findById(new ProposalId(daoId, proposalNumber));
        if (!proposal.isPresent()) {
            return null;
        }
        return getAccountVotingPowerByStateRoot(accountAddress, daoId, proposal.get().getBlockStateRoot());
    }

    public GetVotingPowerResponse getAccountVotingPowerByStateRoot(String accountAddress, String daoId, String stateRoot) {
        BigInteger totalVotingPower = BigInteger.ZERO;
        GetVotingPowerResponse r = new GetVotingPowerResponse();
        List<DaoVotingResource> daoVotingResources = daoVotingResourceRepository.findByDaoVotingResourceId_DaoId(daoId);
        List<GetVotingPowerResponse.VotingPowerDetail> details = new ArrayList<>();
        for (DaoVotingResource daoVotingResource : daoVotingResources) {
            GetVotingPowerResponse.VotingPowerDetail detail = new GetVotingPowerResponse.VotingPowerDetail();
            RpcStateWithProof stateWithProof = jsonRpcClient.getStateWithProofByRoot(
                    JsonRpcClient.getAccountResourceAccessPath(accountAddress, daoVotingResource.getResourceStructTag()),
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

}
