package org.starcoin.dao.data.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import java.io.Serializable;

@Entity
@DynamicInsert
@DynamicUpdate
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler", "fieldHandler"})
public class Proposal implements Serializable {

    @EmbeddedId
    @AttributeOverride(name = "daoId", column = @Column(name = "dao_id", length = 100, nullable = false))
    @AttributeOverride(name = "proposalNumber", column = @Column(name = "proposal_number", length = 20, nullable = false))
    private ProposalId proposalId;

    @Column(length = 200, nullable = false)
    private String title;

    @Column(length = 500)
    private String description;

    /**
     * Link to proposal's discussion.
     */
    @Column(length = 200)
    private String discussion;

    @Column(length = 100, nullable = false)
    private String votingType;

    @Column
    private Long votingPeriodStart;

    @Column
    private Long votingPeriodEnd;

    @Column
    private Long blockHeight;

    @Column(length = 100)
    private String blockStateRoot;

    @Column(length = 100)
    private String categoryId;

    @Column(length = 100)
    private String submittedBy;

    @Column
    private Long submittedAt;


    public ProposalId getProposalId() {
        return proposalId;
    }

    public void setProposalId(ProposalId proposalId) {
        this.proposalId = proposalId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDiscussion() {
        return discussion;
    }

    public void setDiscussion(String discussion) {
        this.discussion = discussion;
    }

    public String getVotingType() {
        return votingType;
    }

    public void setVotingType(String votingType) {
        this.votingType = votingType;
    }

    public Long getVotingPeriodStart() {
        return votingPeriodStart;
    }

    public void setVotingPeriodStart(Long votingPeriodStart) {
        this.votingPeriodStart = votingPeriodStart;
    }

    public Long getVotingPeriodEnd() {
        return votingPeriodEnd;
    }

    public void setVotingPeriodEnd(Long votingPeriodEnd) {
        this.votingPeriodEnd = votingPeriodEnd;
    }

    public Long getBlockHeight() {
        return blockHeight;
    }

    public void setBlockHeight(Long blockHeight) {
        this.blockHeight = blockHeight;
    }

    public String getBlockStateRoot() {
        return blockStateRoot;
    }

    public void setBlockStateRoot(String blockStateRoot) {
        this.blockStateRoot = blockStateRoot;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getSubmittedBy() {
        return submittedBy;
    }

    public void setSubmittedBy(String submittedBy) {
        this.submittedBy = submittedBy;
    }

    public Long getSubmittedAt() {
        return submittedAt;
    }

    public void setSubmittedAt(Long submittedAt) {
        this.submittedAt = submittedAt;
    }
}
