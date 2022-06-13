package org.starcoin.dao.vo;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigInteger;

public class CastVoteVO {
    @JsonProperty("daoId")
    private String daoId;

    @JsonProperty("proposalNumber")
    private String proposalNumber;

    @JsonProperty("accountAddress")
    private String accountAddress;

    @JsonProperty("votingPower")
    private BigInteger votingPower;

    @JsonProperty("choiceSequenceId")
    private Integer choiceSequenceId;

    public String getDaoId() {
        return daoId;
    }

    public void setDaoId(String daoId) {
        this.daoId = daoId;
    }

    public String getProposalNumber() {
        return proposalNumber;
    }

    public void setProposalNumber(String proposalNumber) {
        this.proposalNumber = proposalNumber;
    }

    public String getAccountAddress() {
        return accountAddress;
    }

    public void setAccountAddress(String accountAddress) {
        this.accountAddress = accountAddress;
    }

    public BigInteger getVotingPower() {
        return votingPower;
    }

    public void setVotingPower(BigInteger votingPower) {
        this.votingPower = votingPower;
    }

    public Integer getChoiceSequenceId() {
        return choiceSequenceId;
    }

    public void setChoiceSequenceId(Integer choiceSequenceId) {
        this.choiceSequenceId = choiceSequenceId;
    }

    @Override
    public String toString() {
        return "CastVoteVO{" +
                "daoId='" + daoId + '\'' +
                ", proposalNumber='" + proposalNumber + '\'' +
                ", accountAddress='" + accountAddress + '\'' +
                ", votingPower=" + votingPower +
                ", choiceSequenceId=" + choiceSequenceId +
                '}';
    }
}
