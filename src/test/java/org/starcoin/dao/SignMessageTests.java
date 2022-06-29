package org.starcoin.dao;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.novi.serde.DeserializationError;
import com.novi.serde.SerializationError;
import org.junit.jupiter.api.Test;
import org.starcoin.dao.api.utils.JsonRpcClient;
import org.starcoin.dao.vo.CastVoteVO;
import org.starcoin.types.AccountResource;
import org.starcoin.types.ChainId;
import org.starcoin.types.SignedMessage;
import org.starcoin.utils.HexUtils;
import org.starcoin.utils.SignatureUtils;

import java.math.BigInteger;
import java.net.MalformedURLException;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class SignMessageTests {

    @Test
    public void testVerifySignedMessage() throws SerializationError, DeserializationError {
        String messageBytes = "0xd7f20befd34b9f1ab8aeae98b82a5a516f7b2264616f4964223a22746573745f64616f5f6964222c2270726f706f73616c4e756d626572223a2231222c226163636f756e7441646472657373223a2230783031222c22766f74696e67506f776572223a313131313131312c2263686f69636553657175656e63654964223a307d002032ed52d319694aebc5b52e00836e2f7c7d2c7c7791270ede450d21dbc90cbfa12032ed52d319694aebc5b52e00836e2f7c7d2c7c7791270ede450d21dbc90cbfa101";
        SignedMessage message = SignedMessage.bcsDeserialize(HexUtils.decode(messageBytes));
        boolean checked = SignatureUtils.signedMessageCheckSignature(message);
        assertTrue(checked);
        checked = SignatureUtils.signedMessageCheckAccount(message, new ChainId((byte) 255), null);
        assertTrue(checked);
    }

    @Test
    void testGetAccountResource() throws MalformedURLException, DeserializationError {
        JsonRpcClient jsonRpcClient = new JsonRpcClient("https://barnard-seed.starcoin.org");
        AccountResource accountResource = jsonRpcClient.getAccountResource("0x0000000000000000000000000a550c18");
        System.out.println(accountResource);
        System.out.println(HexUtils.byteListToHexWithPrefix(accountResource.authentication_key));
    }

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
