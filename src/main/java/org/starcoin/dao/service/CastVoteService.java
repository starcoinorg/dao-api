package org.starcoin.dao.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.novi.serde.DeserializationError;
import com.novi.serde.SerializationError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.starcoin.dao.api.utils.JsonRpcClient;
import org.starcoin.dao.data.model.AccountVote;
import org.starcoin.dao.data.model.AccountVoteId;
import org.starcoin.dao.data.model.ProposalId;
import org.starcoin.dao.data.repo.AccountVoteRepository;
import org.starcoin.dao.vo.CastVoteRequest;
import org.starcoin.dao.vo.CastVoteVO;
import org.starcoin.types.ChainId;
import org.starcoin.types.SignedMessage;
import org.starcoin.utils.HexUtils;
import org.starcoin.utils.SignatureUtils;

import java.io.IOException;
import java.util.List;

@Service
public class CastVoteService {

    @Autowired
    private AccountVoteRepository accountVoteRepository;

    @Autowired
    private JsonRpcClient jsonRpcClient;

    //    STARCOIN_CHAIN_ID_MAIN    int = 1
    //    STARCOIN_CHAIN_ID_BARNARD int = 251
    //    STARCOIN_CHAIN_ID_PROXIMA int = 252
    //    STARCOIN_CHAIN_ID_HALLEY  int = 253

    @Value("${starcoin.chain-id}")
    private Integer chainId;

    public void castVote(CastVoteRequest castVoteRequest) {
        byte[] signedMessageBytes = HexUtils.hexToByteArray(castVoteRequest.getSignedMessageHex());
        SignedMessage signedMessage;
        try {
            signedMessage = SignedMessage.bcsDeserialize(signedMessageBytes);
        } catch (DeserializationError e) {
            throw new IllegalArgumentException("Invalid signed message hex", e);
        }
        //
        // check signature
        //
        boolean checked;
        try {
            checked = SignatureUtils.signedMessageCheckSignature(signedMessage);
        } catch (SerializationError e) {
            throw new IllegalArgumentException("signedMessageCheckSignature SerializationError.");
        } catch (DeserializationError e) {
            throw new IllegalArgumentException("signedMessageCheckSignature DeserializationError.");
        }
        if (!checked) {
            throw new IllegalArgumentException("signedMessageCheckSignature error.");
        }
        String accountAddress = HexUtils.byteListToHexWithPrefix(signedMessage.account.value);
        //
        // check auth key in account resource
        //
        List<Byte> authKeyOnChain = null;
        try {
            authKeyOnChain = jsonRpcClient.getAccountResource(accountAddress).authentication_key;
        } catch (DeserializationError e) {
            throw new IllegalArgumentException("getAccountResource DeserializationError.");
        }
        try {
            checked = SignatureUtils.signedMessageCheckAccount(signedMessage, new ChainId((byte) this.chainId.intValue()), authKeyOnChain);
        } catch (DeserializationError e) {
            throw new IllegalArgumentException("signedMessageCheckAccount DeserializationError.");
        } catch (SerializationError e) {
            throw new IllegalArgumentException("signedMessageCheckAccount SerializationError.");
        }
        if (!checked) {
            throw new IllegalArgumentException("signedMessageCheckAccount error.");
        }
        //
        // deserialize to CastVoteVO
        //
        byte[] value = com.google.common.primitives.Bytes.toArray(signedMessage.message.value);
        ObjectMapper objectMapper = new ObjectMapper();
        CastVoteVO castVoteVO;
        try {
            castVoteVO = objectMapper.readValue(value, CastVoteVO.class);
        } catch (IOException e) {
            throw new IllegalArgumentException("Invalid signedMessage.message.value", e);
        }
        if (!accountAddress.equalsIgnoreCase(castVoteVO.getAccountAddress())) {
            throw new IllegalArgumentException("Account in signedMessage signed different account address in CastVote request.");
        }

        //todo: check proposal is not expired
        //todo: check vote power
        AccountVoteId accountVoteId = new AccountVoteId(castVoteVO.getAccountAddress(),
                new ProposalId(castVoteVO.getDaoId(), castVoteVO.getProposalNumber()));
        //todo find by id first.
        AccountVote accountVote = new AccountVote();
        accountVote.setAccountVoteId(accountVoteId);
        accountVote.setVotingPower(castVoteVO.getVotingPower());
        accountVote.setChoiceSequenceId(castVoteVO.getChoiceSequenceId());
        accountVoteRepository.save(accountVote);
    }
}
