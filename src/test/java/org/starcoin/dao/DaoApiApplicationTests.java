package org.starcoin.dao;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.starcoin.dao.data.model.*;
import org.starcoin.dao.data.repo.DaoVotingResourceRepository;
import org.starcoin.dao.data.repo.ProposalRepository;
import org.starcoin.dao.data.repo.ProposalVotingChoiceRepository;

import java.util.List;

@SpringBootTest
class DaoApiApplicationTests {

    @Autowired
    private ProposalRepository proposalRepository;

    @Autowired
    private DaoVotingResourceRepository daoVotingResourceRepository;

    @Autowired
    private ProposalVotingChoiceRepository proposalVotingChoiceRepository;

    @Test
    void contextLoads() {
    }

    @Test
    void testProposalRepositoryFindAll() {
        for (Proposal p : proposalRepository.findAll()) {
            System.out.println(p);
        }
    }

    @Test
    void testAddProposal() {
        Proposal p = new Proposal();
        p.setProposalId(new ProposalId("test_dao_id", "1"));
        p.setCategoryId("category1");
        p.setTitle("title1");
        p.setDescription("description1");
        p.setDiscussion("https://github.com/starcoinorg/discussion1");
        p.setBlockHeight(19999L);
        p.setBlockStateRoot("0x99163c0fc319b62c3897ada8f97881e396e33b30383f47e23d93aaed07d6806d");
        p.setVotingPeriodEnd(System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 7);
        p.setVotingPeriodStart(System.currentTimeMillis());
        p.setVotingType(VotingType.SINGLE_CHOICE);
        proposalRepository.saveAndFlush(p);

        List<ProposalVotingChoice> choices = VotingType.getYesNoChoices(p.getProposalId());
        proposalVotingChoiceRepository.saveAllAndFlush(choices);
    }

    @Test
    void testDaoVotingResourceRepository() {
        // test daoVotingResourceRepository save method.
        DaoVotingResource daoVotingResource = new DaoVotingResource();
        daoVotingResource.setDaoVotingResourceId(new DaoVotingResourceId(
                "test_dao_id",
                "1"
        ));
        daoVotingResource.setResourceStructTag("0x8c109349c6bd91411d6bc962e080c4a3::TokenSwapFarmBoost::UserInfo<" +
                "0x00000000000000000000000000000001::STC::STC, " +
                "0x8c109349c6bd91411d6bc962e080c4a3::STAR::STAR" +
                ">");
        daoVotingResource.setVotingPowerBcsPath("");
        daoVotingResourceRepository.saveAndFlush(daoVotingResource);

        for (DaoVotingResource r : daoVotingResourceRepository.findAll()) {
            System.out.println(r);
        }
    }
}
