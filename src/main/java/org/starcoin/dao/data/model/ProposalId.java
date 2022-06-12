package org.starcoin.dao.data.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class ProposalId implements Serializable {
    @Column(length = 100)
    private String daoId;

    @Column(length = 20)
    private String proposalNumber;

    public ProposalId() {
    }

    public ProposalId(String daoId, String proposalNumber) {
        this.daoId = daoId;
        this.proposalNumber = proposalNumber;
    }

    @Override
    public String toString() {
        return "ProposalId{" +
                "daoId='" + daoId + '\'' +
                ", proposalNumber='" + proposalNumber + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProposalId that = (ProposalId) o;
        return Objects.equals(daoId, that.daoId) && Objects.equals(proposalNumber, that.proposalNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(daoId, proposalNumber);
    }

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
}
