package org.starcoin.dao.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class StarcoinEventFilterImpl implements StarcoinEventFilter {

    private List<String> addresses;

    private String daoCreatedEventTypeTag;

    private String memberJoinEventTypeTag;

    private String memberQuitEventTypeTag;

    private String memberRevokeEventTypeTag;

    private String proposalCreatedEventTypeTag;

    private String votedEventTypeTag;

    public StarcoinEventFilterImpl(
            @Value("${starcoin.event-filter.addresses}") List<String> addresses,
            @Value("${starcoin.dao.dao-created-event-type-tag}") String daoCreatedEventTypeTag,
            @Value("${starcoin.dao.member-join-event-type-tag}") String memberJoinEventTypeTag,
            @Value("${starcoin.dao.member-quit-event-type-tag}") String memberQuitEventTypeTag,
            @Value("${starcoin.dao.member-revoke-event-type-tag}") String memberRevokeEventTypeTag,
            @Value("${starcoin.dao.proposal-created-event-type-tag}") String proposalCreatedEventTypeTag,
            @Value("${starcoin.dao.voted-event-type-tag}") String votedEventTypeTag) {
        this.addresses = addresses;
        //System.out.println("addresses: " + addresses);
        this.daoCreatedEventTypeTag = daoCreatedEventTypeTag;
        this.memberJoinEventTypeTag = memberJoinEventTypeTag;
        this.memberQuitEventTypeTag = memberQuitEventTypeTag;
        this.memberRevokeEventTypeTag = memberRevokeEventTypeTag;
        this.proposalCreatedEventTypeTag = proposalCreatedEventTypeTag;
        this.votedEventTypeTag = votedEventTypeTag;
    }

    @Override
    public List<String> getAddresses() {
        return this.addresses;
    }

    @Override
    public List<String> getEventTypeTags() {
        return Arrays.asList(
                daoCreatedEventTypeTag,
                memberJoinEventTypeTag,
                memberQuitEventTypeTag,
                memberRevokeEventTypeTag,
                proposalCreatedEventTypeTag,
                votedEventTypeTag);
    }

    public String getDaoCreatedEventTypeTag() {
        return daoCreatedEventTypeTag;
    }

    public String getMemberJoinEventTypeTag() {
        return memberJoinEventTypeTag;
    }

    public String getMemberQuitEventTypeTag() {
        return memberQuitEventTypeTag;
    }

    public String getMemberRevokeEventTypeTag() {
        return memberRevokeEventTypeTag;
    }

    public String getProposalCreatedEventTypeTag() {
        return proposalCreatedEventTypeTag;
    }

    public String getVotedEventTypeTag() {
        return votedEventTypeTag;
    }

}
