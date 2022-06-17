package org.starcoin.dao.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.starcoin.bean.RpcStateWithProof;
import org.starcoin.dao.api.utils.JsonRpcClient;
import org.starcoin.dao.data.model.DaoVotingResource;
import org.starcoin.dao.data.model.Proposal;
import org.starcoin.dao.data.model.ProposalId;
import org.starcoin.dao.data.repo.DaoVotingResourceRepository;
import org.starcoin.dao.data.repo.ProposalRepository;
import org.starcoin.dao.vo.GetVotingPowerResponse;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class VotingPowerService {
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
            BigInteger detailVotingPower = BigInteger.ONE;//todo get this from stateWithProof
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
