package org.starcoin.dao;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.starcoin.dao.vo.CastVoteRequest;
import org.starcoin.dao.vo.CastVoteVO;
import org.starcoin.utils.HexUtils;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;

public class SignMessageTests {

    @Test
    void CastVoteVOSerializeTest() throws JsonProcessingException {
        CastVoteVO castVoteVO = new CastVoteVO();
        castVoteVO.setChoiceSequenceId(0);
        castVoteVO.setAccountAddress("0x01");
        castVoteVO.setDaoId("test_dao_id");
        castVoteVO.setProposalNumber("1");
        castVoteVO.setVotingPower(BigInteger.valueOf(1111111));
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(castVoteVO);
        System.out.println(json);

        String hex = HexUtils.byteArrayToHex(json.getBytes(StandardCharsets.UTF_8));
        System.out.println(hex);
    }
}
