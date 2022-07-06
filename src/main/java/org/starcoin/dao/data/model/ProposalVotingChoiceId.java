package org.starcoin.dao.data.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class ProposalVotingChoiceId implements Serializable {
    private ProposalId proposalId;

    private Integer sequenceId;

    public ProposalVotingChoiceId() {
    }

    public ProposalVotingChoiceId(ProposalId proposalId, Integer choiceSequenceId) {
        this.proposalId = proposalId;
        this.sequenceId = choiceSequenceId;
    }

    @Column(length = 100)
    protected String getDaoId() {
        return proposalId.getDaoId();
    }

    protected void setDaoId(String daoId) {
        proposalId.setDaoId(daoId);
    }

    @Column(length = 20)
    protected String getProposalNumber() {
        return proposalId.getProposalNumber();
    }

    protected void setProposalNumber(String proposalNumber) {
        proposalId.setProposalNumber(proposalNumber);
    }

    @Column//(length = 100)
    public Integer getSequenceId() {
        return sequenceId;
    }

    public void setSequenceId(Integer sequenceId) {
        this.sequenceId = sequenceId;
    }

    @Override
    public String toString() {
        return "ProposalVotingChoiceId{" +
                "proposalId=" + proposalId +
                ", sequenceId=" + sequenceId +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProposalVotingChoiceId that = (ProposalVotingChoiceId) o;
        return Objects.equals(proposalId, that.proposalId) && Objects.equals(sequenceId, that.sequenceId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(proposalId, sequenceId);
    }

}
