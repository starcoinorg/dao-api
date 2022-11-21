package org.starcoin.dao;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.novi.serde.Bytes;
import com.novi.serde.DeserializationError;
import com.novi.serde.SerializationError;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.starcoin.bean.BlockHeader;
import org.starcoin.bean.Checkpoints;
import org.starcoin.bean.Resource;
import org.starcoin.dao.utils.JsonRpcClient;
import org.starcoin.dao.vo.CastVoteVO;
import org.starcoin.utils.TransactionUtils;
import org.starcoin.types.*;
import org.starcoin.utils.ConvertUtils;
import org.starcoin.utils.HexUtils;
import org.starcoin.utils.SignatureUtils;
import org.starcoin.utils.StarcoinClient;

import java.math.BigInteger;
import java.net.MalformedURLException;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.starcoin.utils.ConvertUtils.getBlockHash;

public class SignMessageTests {


    @Test
    public void testVerifySignedMessage() throws SerializationError, DeserializationError {
        String messageBytes = "0x4e241dcb1955fdc4811ac3c4abb90f146f7b2264616f4964223a22746573745f64616f5f6964222c2270726f706f73616c4e756d626572223a2231222c226163636f756e7441646472657373223a2230783031222c22766f74696e67506f776572223a313131313131312c2263686f69636553657175656e63654964223a317d00206f1a656de7c85d5d1e936dc80c8f3a8ba16e014003090bb20c207196b4fbab2440e8053432ead6bc1b1063fa986f4626e627556fd6c918e9e46dcc3414ee2a504f7baff920796c69f460399c3887eecf97da0b944baa21ebd68271351551e78e0f01";
        SignedMessage message = SignedMessage.bcsDeserialize(HexUtils.decode(messageBytes));
        boolean checked = SignatureUtils.signedMessageCheckSignature(message);
        assertTrue(checked);
        System.out.println(message.account.toString());
        checked = SignatureUtils.signedMessageCheckAccount(message, new ChainId((byte) 255), (AccountResource) null);
        assertTrue(checked);
        byte[] msgBytes = HexUtils.toPrimitive(message.message.value.toArray(new Byte[0]));
        System.out.println(new String(msgBytes, StandardCharsets.UTF_8));
    }

    @Test
    void testGetAccountResource() throws MalformedURLException, DeserializationError {
        JsonRpcClient jsonRpcClient = new JsonRpcClient("https://barnard-seed.starcoin.org");
        AccountResource accountResource = jsonRpcClient.getAccountResource("0x0000000000000000000000000a550c18");
        System.out.println(accountResource);
        System.out.println(HexUtils.byteListToHexWithPrefix(accountResource.authentication_key));
    }

    @Test
    void testGetResource() throws MalformedURLException {
        JsonRpcClient jsonRpcClient = new JsonRpcClient("https://barnard-seed.starcoin.org");
        Resource<Checkpoints> resource
                = jsonRpcClient.getResource(
                "0x00000000000000000000000000000001",
                "0x1::Block::Checkpoints",
                Checkpoints.class);
        System.out.println(resource);
    }

    @Test
    void testGetBlockHeader() throws MalformedURLException, SerializationError {
        JsonRpcClient jsonRpcClient = new JsonRpcClient("https://barnard-seed.starcoin.org");
        BlockHeader blockHeader = jsonRpcClient.getBlockHeader(111111L);
        System.out.println(blockHeader);
        org.starcoin.types.BlockHeader typesBlockHeader = ConvertUtils.toTypesBlockHeader(blockHeader);
        byte[] hash = getBlockHash(typesBlockHeader);
        String calculatedHash = HexUtils.encode(hash);
        Assertions.assertEquals(calculatedHash.toLowerCase(), blockHeader.getBlockHash().toLowerCase());
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

    @Test
    void test_checkpoint_entry() throws MalformedURLException, SerializationError, DeserializationError {
        StarcoinClient starcoinClient = new StarcoinClient("https://barnard-seed.starcoin.org", 251);
        TransactionPayload transactionPayload = TransactionUtils.encodeEmptyArgsScriptFunction(
                "0x00000000000000000000000000000001::Block",
                "checkpoint_entry");
        submitTxUsingTestAccount(starcoinClient, transactionPayload);
    }

    @Test
    void test_update_state_root_entry() throws MalformedURLException, SerializationError, DeserializationError {
        JsonRpcClient jsonRpcClient = new JsonRpcClient("https://barnard-seed.starcoin.org");
        Resource<Checkpoints> checkpointsResource
                = jsonRpcClient.getResource(
                "0x00000000000000000000000000000001",
                "0x1::Block::Checkpoints",
                Checkpoints.class);
        Long blockNumber = checkpointsResource.getJson().getLastNumber();
        System.out.println("LastNumber: " + blockNumber);
        BlockHeader blockHeader = jsonRpcClient.getBlockHeader(blockNumber);

        StarcoinClient starcoinClient = new StarcoinClient("https://barnard-seed.starcoin.org", 251);

        TransactionPayload transactionPayload = TransactionUtils.encodeU8VectorScriptFunction(
                "0x00000000000000000000000000000001::Block",
                "update_state_root_entry",
                Bytes.valueOf(ConvertUtils.toTypesBlockHeader(blockHeader).bcsSerialize()));

        submitTxUsingTestAccount(starcoinClient, transactionPayload);
    }


    private void submitTxUsingTestAccount(StarcoinClient starcoinClient, TransactionPayload transactionPayload) throws SerializationError, DeserializationError {
        Object result = starcoinClient.submitTransaction(
                AccountAddress.valueOf(HexUtils.hexToByteArray("0x5e66ebe084f888160acd24e3a320cf2b")),
                new Ed25519PrivateKey(Bytes.valueOf(HexUtils.decode(""))),
                transactionPayload);
        System.out.println(result);
    }

}
