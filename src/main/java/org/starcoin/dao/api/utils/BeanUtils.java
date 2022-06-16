package org.starcoin.dao.api.utils;

import org.starcoin.dao.data.model.*;
import org.starcoin.dao.data.repo.DaoVotingResourceRepository;
import org.starcoin.dao.data.repo.ProposalVotingChoiceRepository;
import org.starcoin.dao.vo.DaoVO;
import org.starcoin.dao.vo.ProposalVO;

import java.util.ArrayList;
import java.util.List;

public class BeanUtils {

    public static DaoVO convertToDaoVO(Dao dao, DaoVotingResourceRepository daoVotingResourceRepository) {
        DaoVO vo = new DaoVO();
        org.springframework.beans.BeanUtils.copyProperties(dao, vo);
        List<DaoVO.DaoVotingResource> resourceList = new ArrayList<>();
        for (DaoVotingResource r : daoVotingResourceRepository.findByDaoVotingResourceId_DaoId(dao.getDaoId())) {
            DaoVO.DaoVotingResource resourceVO = new DaoVO.DaoVotingResource();
            resourceVO.setSequenceId(r.getDaoVotingResourceId().getSequenceId());
            resourceVO.setResourceStructTag(r.getResourceStructTag());
            resourceVO.setVotingPowerBcsPath(r.getVotingPowerBcsPath());
            resourceList.add(resourceVO);
        }
        vo.setDaoVotingResources(resourceList);
        return vo;
    }

    public static ProposalVO convertToProposalVO(Proposal proposal, ProposalVotingChoiceRepository proposalVotingChoiceRepository) {
        ProposalVO proposalVO = new ProposalVO();
        org.springframework.beans.BeanUtils.copyProperties(proposal, proposalVO);
        if (VotingType.YES_NO.equals(proposalVO.getVotingType())) {
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
        return proposalVO;
    }

}
