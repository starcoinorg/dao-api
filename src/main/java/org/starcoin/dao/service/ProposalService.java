package org.starcoin.dao.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.starcoin.dao.data.model.Proposal;
import org.starcoin.dao.data.model.ProposalId;
import org.starcoin.dao.data.repo.ProposalRepository;

@Service
public class ProposalService {
    private static final Logger LOG = LoggerFactory.getLogger(ProposalService.class);

    @Autowired
    private ProposalRepository proposalRepository;

    public Page<Proposal> findPaginatedByDaoId(String daoId, Pageable pageable) {
        Proposal proposal = new Proposal();
        ProposalId proposalId = new ProposalId(daoId, null);
        proposal.setProposalId(proposalId);
        Example<Proposal> example = Example.of(proposal);
        return proposalRepository.findAll(example, pageable);
    }

}
