package org.starcoin.dao.data.repo;


import org.springframework.data.jpa.repository.JpaRepository;
import org.starcoin.dao.data.model.DaoStrategyLockedVotingPower;
import org.starcoin.dao.data.model.DaoStrategyLockedVotingPowerId;

public interface DaoStrategyLockedVotingPowerRepository extends JpaRepository<DaoStrategyLockedVotingPower, DaoStrategyLockedVotingPowerId> {

}
