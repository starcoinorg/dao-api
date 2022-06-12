package org.starcoin.dao.data.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class ProposalVotingChoiceId implements Serializable {
    private ProposalId proposalId;

    private Integer choiceSequenceId;

    public ProposalVotingChoiceId() {
    }

    public ProposalVotingChoiceId(ProposalId proposalId, Integer choiceSequenceId) {
        this.proposalId = proposalId;
        this.choiceSequenceId = choiceSequenceId;
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

    @Column(length = 100)
    public Integer getChoiceSequenceId() {
        return choiceSequenceId;
    }

    public void setChoiceSequenceId(Integer choiceSequenceId) {
        this.choiceSequenceId = choiceSequenceId;
    }

    @Override
    public String toString() {
        return "ProposalVotingChoiceId{" +
                "proposalId=" + proposalId +
                ", choiceSequenceId=" + choiceSequenceId +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProposalVotingChoiceId that = (ProposalVotingChoiceId) o;
        return Objects.equals(proposalId, that.proposalId) && Objects.equals(choiceSequenceId, that.choiceSequenceId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(proposalId, choiceSequenceId);
    }

}
