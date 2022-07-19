package org.starcoin.dao.types.event;

import org.starcoin.dao.data.model.*;
import org.starcoin.utils.AccountAddressUtils;

import java.nio.charset.StandardCharsets;

public class EventUtils {
    private EventUtils() {
    }

    public static Dao toDao(DaoCreatedEvent e) {
        Dao dao = new Dao();
        dao.setDaoId(e.id.toString());
        dao.setName(new String(e.name.content(), StandardCharsets.UTF_8));
        dao.setOnChainAddress(AccountAddressUtils.hex(e.dao_address));
        return dao;
    }

    public static DaoMember toDaoMember(MemberJoinEvent e) {
        DaoMember daoMember = new DaoMember();
        daoMember.setDaoMemberId(new DaoMemberId(e.dao_id.toString(), AccountAddressUtils.hex(e.address)));
        daoMember.setOnChainMemberId(e.member_id);
        daoMember.setSbtAmount(e.sbt);
        return daoMember;
    }

    public static Proposal toProposal(ProposalCreatedEventV2 e) {
        Proposal proposal = new Proposal();
        proposal.setProposalId(new ProposalId(e.dao_id.toString(), e.proposal_id.toString()));
        proposal.setSubmittedBy(AccountAddressUtils.hex(e.proposer));
        return proposal;
    }

    public static AccountVote toAccountVote(VotedEvent e) {
        AccountVote accountVote = new AccountVote();
        accountVote.setAccountVoteId(new AccountVoteId(AccountAddressUtils.hex(e.voter),
                new ProposalId(e.dao_id.toString(), e.proposal_id.toString())));
        accountVote.setChoiceSequenceId(e.choice.intValue());
        accountVote.setVotingPower(e.vote_weight);
        return accountVote;
    }
}
