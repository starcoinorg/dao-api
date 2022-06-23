package org.starcoin.dao.data.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.starcoin.dao.data.model.Proposal;
import org.starcoin.dao.data.model.ProposalId;

import java.util.List;

public interface ProposalRepository extends JpaRepository<Proposal, ProposalId> {
    List<Proposal> findByProposalId_DaoId(String daoId);
}
