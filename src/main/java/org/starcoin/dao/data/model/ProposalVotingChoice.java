package org.starcoin.dao.data.model;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

@Entity
public class ProposalVotingChoice {
    @EmbeddedId
    @AttributeOverride(name = "daoId", column = @Column(name = "dao_id", length = 100, nullable = false))
    @AttributeOverride(name = "proposalNumber", column = @Column(name = "proposal_number", length = 20, nullable = false))
    @AttributeOverride(name = "sequenceId", column = @Column(name = "sequence_id", length = 100, nullable = false))
    private ProposalVotingChoiceId proposalVotingChoiceId;

    @Column(length = 200, nullable = false)
    private String title;

    public ProposalVotingChoiceId getProposalVotingChoiceId() {
        return proposalVotingChoiceId;
    }

    public void setProposalVotingChoiceId(ProposalVotingChoiceId proposalVotingChoiceId) {
        this.proposalVotingChoiceId = proposalVotingChoiceId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


}
