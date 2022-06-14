package org.starcoin.dao.data.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.starcoin.dao.data.model.DaoVotingResource;
import org.starcoin.dao.data.model.DaoVotingResourceId;

import java.util.List;

public interface DaoVotingResourceRepository extends JpaRepository<DaoVotingResource, DaoVotingResourceId> {

    List<DaoVotingResource> findByDaoVotingResourceId_DaoId(String daoId);

}
