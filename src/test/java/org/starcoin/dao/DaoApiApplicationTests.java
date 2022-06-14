package org.starcoin.dao;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.starcoin.dao.data.model.*;
import org.starcoin.dao.data.repo.*;

import java.math.BigInteger;
import java.util.List;

@SpringBootTest
class DaoApiApplicationTests {

    @Autowired
    private DaoRepository daoRepository;

    @Autowired
    private ProposalRepository proposalRepository;

    @Autowired
    private ProposalVotingChoiceRepository proposalVotingChoiceRepository;

    @Autowired
    private DaoVotingResourceRepository daoVotingResourceRepository;

    @Autowired
    private AccountVoteRepository accountVoteRepository;

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
    void testAddDaos() {
        Dao dao = new Dao();
        dao.setDaoId("test_dao_id");
        dao.setDescription("test_dao_description");
        dao.setName("test_dao_name");
        dao.setCommunityLinksDiscord("http://test_dao_community_links_discord");
        dao.setCommunityLinksTelegram("http://test_dao_community_links_telegram");
        dao.setCommunityLinksTwitter("http://test_dao_community_links_twitter");
        dao.setPurposeId("test_dao_purpose_id");
        dao.setTags("test_dao_tags,blockchain,dao");
        daoRepository.saveAndFlush(dao);
    }

    @Test
    void testAddProposals() {
        Proposal p1 = new Proposal();
        p1.setProposalId(new ProposalId("test_dao_id", "1"));
        p1.setCategoryId("category1");
        p1.setTitle("title1");
        p1.setDescription("description1");
        p1.setDiscussion("https://github.com/starcoinorg/discussion1");
        p1.setBlockHeight(19999L);
        p1.setBlockStateRoot("0x99163c0fc319b62c3897ada8f97881e396e33b30383f47e23d93aaed07d6806d");
        p1.setVotingPeriodEnd(System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 7);
        p1.setVotingPeriodStart(System.currentTimeMillis());
        p1.setVotingType(VotingType.SINGLE_CHOICE);
        proposalRepository.saveAndFlush(p1);
        List<ProposalVotingChoice> p1_choices = VotingType.getYesNoChoices(p1.getProposalId());
        proposalVotingChoiceRepository.saveAllAndFlush(p1_choices);

        // --------------- another proposal -----------------

        Proposal p_2 = new Proposal();
        p_2.setProposalId(new ProposalId("test_dao_id", "2"));
        p_2.setCategoryId("category2");
        p_2.setTitle("title2");
        p_2.setDescription("description2");
        p_2.setDiscussion("https://github.com/starcoinorg/discussion2");
        p_2.setBlockHeight(19999L);
        p_2.setBlockStateRoot("0x99163c0fc319b62c3897ada8f97881e396e33b30383f47e23d93aaed07d6806d");
        p_2.setVotingPeriodEnd(System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 7);
        p_2.setVotingPeriodStart(System.currentTimeMillis());
        p_2.setVotingType(VotingType.YES_NO);
        proposalRepository.saveAndFlush(p_2);

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

    @Test
    void testAddAccountVotes() {
        AccountVote vote1 = new AccountVote();
        vote1.setAccountVoteId(new AccountVoteId("0x8c109349c6bd91411d6bc962e080c4a3",
                new ProposalId("test_dao_id", "1")));
        vote1.setChoiceSequenceId(0);
        vote1.setVotingPower(BigInteger.valueOf(11111L));
        accountVoteRepository.saveAndFlush(vote1);

        AccountVote vote2 = new AccountVote();
        vote2.setAccountVoteId(new AccountVoteId("0xa7cdbbd23a489acac81b07fdecbacc25",
                new ProposalId("test_dao_id", "1")));
        vote2.setChoiceSequenceId(0);
        vote2.setVotingPower(BigInteger.valueOf(21111L));
        accountVoteRepository.saveAndFlush(vote2);
    }
}
