package org.starcoin.dao.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.novi.serde.DeserializationError;
import com.novi.serde.SerializationError;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.starcoin.dao.api.utils.JsonRpcClient;
import org.starcoin.dao.data.model.AccountVote;
import org.starcoin.dao.data.model.AccountVoteId;
import org.starcoin.dao.data.model.Proposal;
import org.starcoin.dao.data.model.ProposalId;
import org.starcoin.dao.data.repo.AccountVoteRepository;
import org.starcoin.dao.data.repo.ProposalRepository;
import org.starcoin.dao.vo.CastVoteRequest;
import org.starcoin.dao.vo.CastVoteVO;
import org.starcoin.dao.vo.GetVotingPowerResponse;
import org.starcoin.types.ChainId;
import org.starcoin.types.SignedMessage;
import org.starcoin.utils.HexUtils;
import org.starcoin.utils.SignatureUtils;

import java.io.IOException;
import java.util.List;

@Service
public class CastVoteService {
    private static final Logger LOG = LoggerFactory.getLogger(CastVoteService.class);

    //    STARCOIN_CHAIN_ID_MAIN    int = 1
    //    STARCOIN_CHAIN_ID_BARNARD int = 251
    //    STARCOIN_CHAIN_ID_PROXIMA int = 252
    //    STARCOIN_CHAIN_ID_HALLEY  int = 253

    @Value("${starcoin.chain-id}")
    private Integer chainId;

    @Autowired
    private JsonRpcClient jsonRpcClient;

    @Autowired
    private AccountVoteRepository accountVoteRepository;

    @Autowired
    private ProposalRepository proposalRepository;


    @Autowired
    private VotingPowerQueryService votingPowerQueryService;

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
            throw new IllegalArgumentException("signedMessageCheckSignature SerializationError.", e);
        } catch (DeserializationError e) {
            throw new IllegalArgumentException("signedMessageCheckSignature DeserializationError.", e);
        }
        if (!checked) {
            throw new IllegalArgumentException("signedMessageCheckSignature failed.");
        }
        String accountAddress = HexUtils.byteListToHexWithPrefix(signedMessage.account.value);
        //
        // check auth key in account resource
        //
        List<Byte> authKeyOnChain;
        try {
            authKeyOnChain = jsonRpcClient.getAccountResource(accountAddress).authentication_key;
        } catch (DeserializationError e) {
            throw new IllegalArgumentException("getAccountResource DeserializationError.", e);
        }
        try {
            checked = SignatureUtils.signedMessageCheckAccount(signedMessage, new ChainId((byte) this.chainId.intValue()), authKeyOnChain);
        } catch (DeserializationError e) {
            throw new IllegalArgumentException("signedMessageCheckAccount DeserializationError.", e);
        } catch (SerializationError e) {
            throw new IllegalArgumentException("signedMessageCheckAccount SerializationError.", e);
        }
        if (!checked) {
            throw new IllegalArgumentException("signedMessageCheckAccount failed.");
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

        //
        // check voting power
        //
        GetVotingPowerResponse getVotingPowerResponse = votingPowerQueryService.getAccountVotingPower(accountAddress,
                castVoteVO.getDaoId(), castVoteVO.getProposalNumber());
        if (castVoteVO.getVotingPower().compareTo(getVotingPowerResponse.getTotalVotingPower()) != 0) {
            throw new IllegalArgumentException("castVoteVO.votingPower("
                    + castVoteVO.getVotingPower() + ") is incorrect. Got voting power on-chain: "
                    + getVotingPowerResponse.getTotalVotingPower());
        }

        //
        // check if proposal is started and not expired
        //
        ProposalId proposalId = new ProposalId(castVoteVO.getDaoId(), castVoteVO.getProposalNumber());
        Proposal proposal = proposalRepository.findById(proposalId).orElseThrow(
                () -> new IllegalArgumentException("Proposal is NOT present: " + proposalId)
        );
        long currentMills = System.currentTimeMillis();
        if (currentMills < proposal.getVotingPeriodStart()) {
            throw new IllegalArgumentException("Proposal is NOT started: " + proposalId);
        }
        if (currentMills > proposal.getVotingPeriodEnd()) {
            throw new IllegalArgumentException("Proposal is ended: " + proposalId);
        }

        AccountVoteId accountVoteId = new AccountVoteId(castVoteVO.getAccountAddress(), proposalId);
        if (!accountVoteRepository.findById(accountVoteId).isPresent()) {
            AccountVote accountVote = new AccountVote();
            accountVote.setAccountVoteId(accountVoteId);
            accountVote.setVotingPower(castVoteVO.getVotingPower());
            accountVote.setChoiceSequenceId(castVoteVO.getChoiceSequenceId());
            accountVoteRepository.save(accountVote);
        } else {
            LOG.info("Account has casted vote: " + accountVoteId);
        }
    }
}
