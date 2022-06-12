package org.starcoin.dao.data.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.starcoin.dao.data.model.Dao;

public interface DaoRepository extends JpaRepository<Dao, String> {
}
