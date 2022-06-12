package org.starcoin.dao.data.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.starcoin.dao.data.model.ProposalVotingChoice;
import org.starcoin.dao.data.model.ProposalVotingChoiceId;

public interface ProposalVotingChoiceRepository extends JpaRepository<ProposalVotingChoice, ProposalVotingChoiceId> {


}
