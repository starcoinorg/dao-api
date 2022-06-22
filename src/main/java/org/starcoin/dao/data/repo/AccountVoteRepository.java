package org.starcoin.dao.data.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.starcoin.dao.data.model.AccountVote;
import org.starcoin.dao.data.model.AccountVoteId;
import org.starcoin.dao.data.model.AccountVoteSummary;

import java.util.List;

public interface AccountVoteRepository extends JpaRepository<AccountVote, AccountVoteId> {
    @Query(value = "select * from account_vote v where dao_id = :daoId and proposal_number = :proposalNumber",
            nativeQuery = true)
    List<AccountVote> findByDaoIdAndProposalNumber(String daoId, String proposalNumber);

    @Query(value = "select v.choice_sequence_id as choiceSequenceId, " +
            "sum(v.voting_power) as subtotalVotingPower " +
            "from account_vote v " +
            "where dao_id = :daoId and proposal_number = :proposalNumber " +
            "group by choice_sequence_id", nativeQuery = true)
    List<AccountVoteSummary> sumAccountVotesGroupByChoice(String daoId, String proposalNumber);

}
