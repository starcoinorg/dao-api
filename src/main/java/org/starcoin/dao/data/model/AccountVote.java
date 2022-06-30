package org.starcoin.dao.data.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import java.io.Serializable;
import java.math.BigInteger;

@Entity
@DynamicInsert
@DynamicUpdate
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler", "fieldHandler"})
public class AccountVote implements Serializable {

    @EmbeddedId
    @AttributeOverride(name = "accountAddress", column = @Column(name = "account_address", length = 100, nullable = false))
    @AttributeOverride(name = "daoId", column = @Column(name = "dao_id", length = 100, nullable = false))
    @AttributeOverride(name = "proposalNumber", column = @Column(name = "proposal_number", length = 20, nullable = false))
    private AccountVoteId accountVoteId;

    @Column(nullable = false)
    private Integer choiceSequenceId;

    @Column(precision = 50, scale = 0, nullable = false)
    private BigInteger votingPower;

    @Column
    private Long createdAt;

    public AccountVoteId getAccountVoteId() {
        return accountVoteId;
    }

    public void setAccountVoteId(AccountVoteId accountVoteId) {
        this.accountVoteId = accountVoteId;
    }

    public Integer getChoiceSequenceId() {
        return choiceSequenceId;
    }

    public void setChoiceSequenceId(Integer choiceSequenceId) {
        this.choiceSequenceId = choiceSequenceId;
    }

    public BigInteger getVotingPower() {
        return votingPower;
    }

    public void setVotingPower(BigInteger votingPower) {
        this.votingPower = votingPower;
    }

    public Long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Long createdAt) {
        this.createdAt = createdAt;
    }
}
