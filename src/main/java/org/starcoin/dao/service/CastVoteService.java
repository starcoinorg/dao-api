package org.starcoin.dao.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.novi.serde.DeserializationError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.starcoin.dao.data.model.AccountVote;
import org.starcoin.dao.data.model.AccountVoteId;
import org.starcoin.dao.data.model.ProposalId;
import org.starcoin.dao.data.repo.AccountVoteRepository;
import org.starcoin.dao.vo.CastVoteRequest;
import org.starcoin.dao.vo.CastVoteVO;
import org.starcoin.types.SignedMessage;
import org.starcoin.utils.HexUtils;

import java.io.IOException;

@Service
public class CastVoteService {

    @Autowired
    private AccountVoteRepository accountVoteRepository;

    public void castVote(CastVoteRequest castVoteRequest) {
        byte[] signedMessageBytes = HexUtils.hexToByteArray(castVoteRequest.getSignedMessageHex());
        SignedMessage signedMessage;
        try {
            signedMessage = SignedMessage.bcsDeserialize(signedMessageBytes);
        } catch (DeserializationError e) {
            throw new IllegalArgumentException("Invalid signed message hex", e);
        }
        //todo: check signature
        byte[] value = com.google.common.primitives.Bytes.toArray(signedMessage.message.value);
        ObjectMapper objectMapper = new ObjectMapper();
        CastVoteVO castVoteVO;
        try {
            castVoteVO = objectMapper.readValue(value, CastVoteVO.class);
        } catch (IOException e) {
            throw new IllegalArgumentException("Invalid signedMessage.message.value", e);
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
