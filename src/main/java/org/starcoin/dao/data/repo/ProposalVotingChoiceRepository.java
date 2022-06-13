package org.starcoin.dao.data.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.starcoin.dao.data.model.ProposalId;
import org.starcoin.dao.data.model.ProposalVotingChoice;
import org.starcoin.dao.data.model.ProposalVotingChoiceId;

import java.util.List;

public interface ProposalVotingChoiceRepository extends JpaRepository<ProposalVotingChoice, ProposalVotingChoiceId> {

    List<ProposalVotingChoice> findByProposalVotingChoiceId_ProposalId(ProposalId proposalId);

}
