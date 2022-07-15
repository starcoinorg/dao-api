package org.starcoin.dao.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.starcoin.dao.data.model.Proposal;
import org.starcoin.dao.data.model.ProposalId;
import org.starcoin.dao.data.model.ProposalVotingChoice;
import org.starcoin.dao.data.model.ProposalVotingChoiceId;
import org.starcoin.dao.data.repo.ProposalRepository;
import org.starcoin.dao.data.repo.ProposalVotingChoiceRepository;

@Service
public class ProposalService {
    private static final Logger LOG = LoggerFactory.getLogger(ProposalService.class);

    @Autowired
    private ProposalRepository proposalRepository;

    @Autowired
    private ProposalVotingChoiceRepository proposalVotingChoiceRepository;

    public Page<Proposal> findPaginatedByDaoId(String daoId, Pageable pageable) {
        Proposal proposal = new Proposal();
        ProposalId proposalId = new ProposalId(daoId, null);
        proposal.setProposalId(proposalId);
        Example<Proposal> example = Example.of(proposal);
        return proposalRepository.findAll(example, pageable);
    }

    public void addOrUpdateProposal(Proposal proposal) {
        ProposalId proposalId = proposal.getProposalId();
        Proposal p = proposalRepository.findById(proposalId).orElse(null);
        if (p == null) {
            proposalRepository.save(proposal);
        } else {
            BeanUtils.copyProperties(proposal, p);
            proposalRepository.save(p);
        }
    }

    public void addOrUpdateProposalVotingChoice(ProposalVotingChoice proposalVotingChoice) {
        ProposalVotingChoiceId proposalVotingChoiceId = proposalVotingChoice.getProposalVotingChoiceId();
        ProposalVotingChoice p = proposalVotingChoiceRepository.findById(proposalVotingChoiceId).orElse(null);
        if (p == null) {
            proposalVotingChoiceRepository.save(proposalVotingChoice);
        } else {
            BeanUtils.copyProperties(proposalVotingChoice, p);
            proposalVotingChoiceRepository.save(p);
        }
    }
}
