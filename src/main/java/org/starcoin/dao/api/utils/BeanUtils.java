package org.starcoin.dao.api.utils;

import org.starcoin.dao.data.model.*;
import org.starcoin.dao.data.repo.AccountVoteRepository;
import org.starcoin.dao.data.repo.ProposalVotingChoiceRepository;
import org.starcoin.dao.service.DaoStrategyService;
import org.starcoin.dao.service.VotingPowerQueryService;
import org.starcoin.dao.vo.DaoVO;
import org.starcoin.dao.vo.DaoVotingResourceVO;
import org.starcoin.dao.vo.ProposalVO;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BeanUtils {

    public static DaoVO convertToDaoVO(Dao dao,
                                       DaoStrategyService daoStrategyService,
                                       VotingPowerQueryService votingPowerQueryService) {
        DaoVO vo = new DaoVO();
        org.springframework.beans.BeanUtils.copyProperties(dao, vo);
        DaoStrategy daoStrategy = daoStrategyService.getPrimaryDaoStrategy(dao.getDaoId());
        DaoVO.DaoStrategy daoStrategy2 = convertToDaoStrategyVO(daoStrategy, dao, votingPowerQueryService);
        vo.setDaoStrategies(Collections.singletonList(daoStrategy2));
        return vo;
    }

    private static DaoVO.DaoStrategy convertToDaoStrategyVO(DaoStrategy daoStrategy, Dao dao, VotingPowerQueryService votingPowerQueryService) {
        DaoVO.DaoStrategy daoStrategy2 = new DaoVO.DaoStrategy();
        String strategyId = daoStrategy != null ? daoStrategy.getDaoStrategyId().getStrategyId() : null;
        daoStrategy2.setStrategyId(strategyId);
        daoStrategy2.setSequenceId(daoStrategy != null ? daoStrategy.getSequenceId() : null);
        if (Strategy.STRATEGY_ID_SBT.equals(strategyId)) {
            daoStrategy2.setDescription(Strategy.SBT.getDescription());
        } else if (Strategy.STRATEGY_ID_VOTING_RESOURCES.equals(strategyId)) {
            daoStrategy2.setDescription(Strategy.VOTING_RESOURCES.getDescription());
        }
        List<DaoVotingResourceVO> resourceList = new ArrayList<>();
        for (DaoVotingResource r : votingPowerQueryService.getDaoVotingResources(dao.getDaoId(), dao.getDaoTypeTag(),
                strategyId)) {
            DaoVotingResourceVO resourceVO = new DaoVotingResourceVO();
            resourceVO.setSequenceId(r.getDaoVotingResourceId().getSequenceId());
            resourceVO.setResourceStructTag(r.getResourceStructTag());
            resourceVO.setVotingPowerBcsPath(r.getVotingPowerBcsPath());
            resourceList.add(resourceVO);
        }
        daoStrategy2.setDaoVotingResources(resourceList);
        return daoStrategy2;
    }

    public static ProposalVO convertToProposalVO(Proposal proposal, ProposalVotingChoiceRepository proposalVotingChoiceRepository, AccountVoteRepository accountVoteRepository) {
        ProposalVO proposalVO = new ProposalVO();
        org.springframework.beans.BeanUtils.copyProperties(proposal, proposalVO);
        if (VotingType.STANDARD.equals(proposalVO.getVotingType())) {
            proposalVO.setProposalVotingChoices(ProposalVO.getStandardVotingChoices());
        } else if (VotingType.YES_NO.equals(proposalVO.getVotingType())) {
            proposalVO.setProposalVotingChoices(ProposalVO.getYesNoChoices());
        } else if (VotingType.SINGLE_CHOICE.equals(proposalVO.getVotingType())) {
            List<ProposalVO.ProposalVotingChoice> choices = new ArrayList<>();
            for (ProposalVotingChoice c : proposalVotingChoiceRepository.findByProposalVotingChoiceId_ProposalId(proposalVO.getProposalId())) {
                ProposalVO.ProposalVotingChoice c2 = new ProposalVO.ProposalVotingChoice(
                        c.getProposalVotingChoiceId().getSequenceId(), c.getTitle());
                choices.add(c2);
            }
            proposalVO.setProposalVotingChoices(choices);
        }
        List<AccountVoteSummary> accountVoteSummaries = accountVoteRepository.sumAccountVotesGroupByChoice(
                proposal.getProposalId().getDaoId(), proposal.getProposalId().getProposalNumber());
        proposalVO.setAccountVoteSummaries(accountVoteSummaries);
        return proposalVO;
    }

}
