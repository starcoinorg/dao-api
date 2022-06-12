package org.starcoin.dao.data.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.starcoin.dao.data.model.AccountVote;
import org.starcoin.dao.data.model.AccountVoteId;

public interface AccountVoteRepository extends JpaRepository<AccountVote, AccountVoteId> {

}
