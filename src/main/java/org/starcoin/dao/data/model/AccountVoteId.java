package org.starcoin.dao.data.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class AccountVoteId implements Serializable {
    private String accountAddress;
    private ProposalId proposalId;

    @Column(length = 100)
    public String getAccountAddress() {
        return accountAddress;
    }

    public void setAccountAddress(String accountAddress) {
        this.accountAddress = accountAddress;
    }

    @Column(length = 100)
    public String getDaoId() {
        return proposalId.getDaoId();
    }

    public void setDaoId(String daoId) {
        proposalId.setDaoId(daoId);
    }

    @Column(length = 20)
    public String getProposalNumber() {
        return proposalId.getProposalNumber();
    }

    public void setProposalNumber(String proposalNumber) {
        proposalId.setProposalNumber(proposalNumber);
    }

    public ProposalId getProposalId() {
        return proposalId;
    }

    public void setProposalId(ProposalId proposalId) {
        this.proposalId = proposalId;
    }

    public AccountVoteId() {
    }

    public AccountVoteId(String accountAddress, ProposalId proposalId) {
        this.accountAddress = accountAddress;
        this.proposalId = proposalId;
    }

    @Override
    public String toString() {
        return "AccountVoteId{" +
                "accountAddress='" + accountAddress + '\'' +
                ", proposalId=" + proposalId +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AccountVoteId that = (AccountVoteId) o;
        return Objects.equals(accountAddress, that.accountAddress) && Objects.equals(proposalId, that.proposalId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountAddress, proposalId);
    }
}
