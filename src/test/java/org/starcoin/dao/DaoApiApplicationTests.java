package org.starcoin.dao;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.novi.serde.DeserializationError;
import com.novi.serde.SerializationError;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.starcoin.dao.data.model.*;
import org.starcoin.dao.data.repo.*;
import org.starcoin.dao.service.CastVoteService;
import org.starcoin.dao.vo.CastVoteRequest;
import org.starcoin.dao.vo.CastVoteVO;
import org.starcoin.types.*;
import org.starcoin.utils.HexUtils;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
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

    @Autowired
    private CastVoteService castVoteService;

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
        daoVotingResource.setResourceStructTag("0x8c109349c6bd91411d6bc962e080c4a3::TokenSwapVestarMinter::Treasury");
        // {"json":{"vtoken":{"token":{"value":0}}},"raw":"0x00000000000000000000000000000000"}
        daoVotingResource.setVotingPowerBcsPath("{{{u128<-}}}");
        daoVotingResourceRepository.saveAndFlush(daoVotingResource);

        DaoVotingResource daoVotingResource2 = new DaoVotingResource();
        daoVotingResource2.setDaoVotingResourceId(new DaoVotingResourceId(
                "test_dao_id",
                "2"
        ));
        daoVotingResource2.setResourceStructTag("0x8c109349c6bd91411d6bc962e080c4a3::TokenSwapFarmBoost::UserInfo<" +
                "0x00000000000000000000000000000001::STC::STC, " +
                "0x8c109349c6bd91411d6bc962e080c4a3::STAR::STAR" +
                ">");
        // {"json":{"boost_factor":250,
        // "locked_vetoken":{"token":{"value":2902046449728}},"user_amount":0},
        // "raw":"0xfa0000000000000040a072afa3020000000000000000000000000000000000000000000000000000"}
        daoVotingResource2.setVotingPowerBcsPath("{u128,{{u128<-}},u128}");
        daoVotingResourceRepository.saveAndFlush(daoVotingResource2);

        for (DaoVotingResource r : daoVotingResourceRepository.findAll()) {
            System.out.println(r);
        }
    }

//    @Test
//    void testAddAccountVotes() {
//        AccountVote vote1 = new AccountVote();
//        vote1.setAccountVoteId(new AccountVoteId("0x8c109349c6bd91411d6bc962e080c4a3",
//                new ProposalId("test_dao_id", "1")));
//        vote1.setChoiceSequenceId(0);
//        vote1.setVotingPower(BigInteger.valueOf(11111L));
//        accountVoteRepository.saveAndFlush(vote1);
//
//        AccountVote vote2 = new AccountVote();
//        vote2.setAccountVoteId(new AccountVoteId("0xa7cdbbd23a489acac81b07fdecbacc25",
//                new ProposalId("test_dao_id", "1")));
//        vote2.setChoiceSequenceId(0);
//        vote2.setVotingPower(BigInteger.valueOf(21111L));
//        accountVoteRepository.saveAndFlush(vote2);
//    }

    @Test
    void testSumProposalVotesGroupByChoice() {
        List<AccountVoteSummary> summaries = accountVoteRepository.sumAccountVotesGroupByChoice("test_dao_id", "1");
        System.out.println(summaries);
    }

    @Test
    void testCastVote() throws JsonProcessingException, DeserializationError, SerializationError {
        String signedMessageHex = "0x4e241dcb1955fdc4811ac3c4abb90f146f7b2264616f4964223a22746573745f64616f5f6964222c2270726f706f73616c4e756d626572223a2231222c226163636f756e7441646472657373223a2230783031222c22766f74696e67506f776572223a313131313131312c2263686f69636553657175656e63654964223a317d00206f1a656de7c85d5d1e936dc80c8f3a8ba16e014003090bb20c207196b4fbab2440e8053432ead6bc1b1063fa986f4626e627556fd6c918e9e46dcc3414ee2a504f7baff920796c69f460399c3887eecf97da0b944baa21ebd68271351551e78e0f01";
        //SignedMessage message = SignedMessage.bcsDeserialize(HexUtils.decode(signedMessageHex));
        CastVoteRequest castVoteRequest = new CastVoteRequest();//getTestCastVoteRequest();
        castVoteRequest.setSignedMessageHex(signedMessageHex);
        castVoteService.castVote(castVoteRequest);
    }

    @NotNull
    private CastVoteRequest getTestCastVoteRequest() throws JsonProcessingException, DeserializationError, SerializationError {
        CastVoteVO castVoteVO = new CastVoteVO();
        castVoteVO.setChoiceSequenceId(0);
        castVoteVO.setAccountAddress("0x8c109349c6bd91411d6bc962e080c4a3");
        castVoteVO.setDaoId("test_dao_id");
        castVoteVO.setProposalNumber("1");
        castVoteVO.setVotingPower(BigInteger.valueOf(1111111));
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(castVoteVO);
        System.out.println(json);

        String hex = HexUtils.byteArrayToHex(json.getBytes(StandardCharsets.UTF_8));
        System.out.println(hex);

        SigningMessage signingMessage = new SigningMessage(
                com.google.common.primitives.Bytes.asList(json.getBytes(StandardCharsets.UTF_8)));
        //todo: sign the message
        String just_a_test_authenticator_hex = "0x00204a4f7becc8b33af1ad34ed6195ab1109c4793e915759aa0eb26792fed4674f3d40097e0a748706c30de6457261bfc40ca0b83704072fb7614aac5b2643fe30860ed2e256b832e5160cd9da14d0fa183599d5e89b3169c8aa764ff86fc16f115600";
        SignedMessage signedMessage = new SignedMessage(
                AccountAddress.valueOf(HexUtils.hexToByteArray("0x8c109349c6bd91411d6bc962e080c4a3")),
                signingMessage,
                TransactionAuthenticator.Ed25519.bcsDeserialize(HexUtils.hexToByteArray(just_a_test_authenticator_hex)),
                new ChainId((byte) 1)
        );

        CastVoteRequest castVoteRequest = new CastVoteRequest();
        castVoteRequest.setSignedMessageHex(HexUtils.byteArrayToHex(signedMessage.bcsSerialize()));
        return castVoteRequest;
    }
}
