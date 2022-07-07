package org.starcoin.dao.data.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.starcoin.dao.data.model.DaoStrategy;
import org.starcoin.dao.data.model.DaoStrategyId;

public interface DaoStrategyRepository extends JpaRepository<DaoStrategy, DaoStrategyId> {

    DaoStrategy findFirstByDaoStrategyId_DaoIdOrderBySequenceId(String daoId);

}
