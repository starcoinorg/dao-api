package org.starcoin.dao.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.starcoin.dao.data.model.*;
import org.starcoin.dao.data.repo.DaoStrategyRepository;
import org.starcoin.dao.data.repo.ProposalRepository;
import org.starcoin.dao.data.repo.ProposalVotingChoiceRepository;
import org.starcoin.dao.utils.JsonRpcClient;

import java.math.BigInteger;

@Service
public class ProposalService {
    private static final Logger LOG = LoggerFactory.getLogger(ProposalService.class);

    @Autowired
    private ProposalRepository proposalRepository;

    @Autowired
    private ProposalVotingChoiceRepository proposalVotingChoiceRepository;

    @Autowired
    private DaoStrategyService daoStrategyService;

    @Autowired
    private JsonRpcClient jsonRpcClient;

    @Autowired
    private DaoStrategyRepository daoStrategyRepository;

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

    public void addProposalIfNotExists(Proposal proposal) {
        ProposalId proposalId = proposal.getProposalId();
        Proposal p = proposalRepository.findById(proposalId).orElse(null);
        if (p == null) {
            proposalRepository.save(proposal);
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

    public void removeProposalVotingChoice(ProposalVotingChoiceId proposalVotingChoiceId) {
        ProposalVotingChoice p = proposalVotingChoiceRepository.findById(proposalVotingChoiceId).orElse(null);
        if (p != null) {
            proposalVotingChoiceRepository.delete(p);
        }
    }

    public void updateVotingTurnoutThresholdByCurrentStateRoot(String daoId, String proposalNumber, String strategyId) {
        Pair<Long, String> p = jsonRpcClient.getCurrentChainHeightStateRoot();
        updateVotingTurnoutThreshold(daoId, proposalNumber, p.getItem1(), p.getItem2(), strategyId);
    }

    private void updateVotingTurnoutThreshold(String daoId, String proposalNumber, Long height, String stateRoot, String strategyId) {
        Proposal proposal = proposalRepository.findById(new ProposalId(daoId, proposalNumber)).orElse(null);
        if (proposal == null) {
            LOG.info("proposal not found, daoId: {}, proposalNumber: {}", daoId, proposalNumber);
            return;
        }
        if (!Proposal.VOTING_METHOD_OFF_CHAIN.equals(proposal.getVotingMethod())) {
            throw new IllegalArgumentException(String.format(
                    "The proposal voting method is not off-chain, daoId: %s, proposalNumber: %s", daoId, proposalNumber));
        }
        DaoStrategyId daoStrategyId = new DaoStrategyId(daoId, strategyId);
        DaoStrategy daoStrategy = daoStrategyRepository.findById(daoStrategyId).orElse(null);
        if (daoStrategy == null) {
            throw new IllegalArgumentException(String.format(
                    "The dao strategy not found, daoId: %s, strategyId: %s", daoId, strategyId));
        }
        if (!DaoStrategy.DAO_STRATEGY_TYPE_OFF_CHAIN.equals(daoStrategy.getDaoStrategyType())) {
            throw new IllegalArgumentException(String.format(
                    "The dao strategy type is not off-chain, daoId: %s, strategyId: %s", daoId, strategyId));
        }
        Pair<BigInteger, BigInteger> pair = daoStrategyService.getCirculatingVotingPowerAndVotingTurnoutThreshold(stateRoot, daoStrategy);
        proposal.setCirculatingVotingPower(pair.getItem1());
        proposal.setVotingTurnoutThreshold(pair.getItem2());
        proposal.setBlockHeight(height);
        proposal.setBlockStateRoot(stateRoot);
        proposalRepository.save(proposal);
    }
}
