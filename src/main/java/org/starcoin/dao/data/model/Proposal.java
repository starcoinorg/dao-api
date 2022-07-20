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
public class Proposal implements Serializable {
    // Proposal states
    public static final byte PROPOSAL_STATE_PENDING = 1;
    public static final byte PROPOSAL_STATE_ACTIVE = 2;
    public static final byte PROPOSAL_STATE_DEFEATED = 3;
    public static final byte PROPOSAL_STATE_AGREED = 4;
    public static final byte PROPOSAL_STATE_QUEUED = 5;
    public static final byte PROPOSAL_STATE_EXECUTABLE = 6;
    public static final byte PROPOSAL_STATE_EXTRACTED = 7;

    //const ProposalStatusEnum
    //    Unknown: 'Unknown'
    //    InQueue: 'InQueue'
    //    VotingPeriod: 'VotingPeriod',
    //    GracePeriod: 'GracePeriod'
    //    Cancelled: 'Cancelled'
    //    Passed: 'Passed'
    //    Failed: 'Failed'
    //    ReadyForProcessing: 'ReadyForProcessing',
    //    Unsponsored: 'Unsponsored'
    //};

    // Voting methods
    public static final String VOTING_METHOD_ON_CHAIN = "ON_CHAIN";
    public static final String VOTING_METHOD_OFF_CHAIN = "OFF_CHAIN";

    @EmbeddedId
    @AttributeOverride(name = "daoId", column = @Column(name = "dao_id", length = 100, nullable = false))
    @AttributeOverride(name = "proposalNumber", column = @Column(name = "proposal_number", length = 20, nullable = false))
    private ProposalId proposalId;

    @Column(length = 200, nullable = false)
    private String title;

    @Column(length = 2000)
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

    @Column(length = 200)
    private String pluginTypeTag;

    @Column(length = 200)
    private String actionTypeTag;

    @Column(length = 1000)
    private String action;

    @Column(length = 20)
    private String status;

    @Column(length = 20)
    private String votingMethod;

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

    public String getPluginTypeTag() {
        return pluginTypeTag;
    }

    public void setPluginTypeTag(String pluginTypeTag) {
        this.pluginTypeTag = pluginTypeTag;
    }

    public String getActionTypeTag() {
        return actionTypeTag;
    }

    public void setActionTypeTag(String actionTypeTag) {
        this.actionTypeTag = actionTypeTag;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getVotingMethod() {
        return votingMethod;
    }

    public void setVotingMethod(String votingMethod) {
        this.votingMethod = votingMethod;
    }


    @Column(precision = 50, scale = 0)
    private BigInteger votingTurnoutThreshold;

    @Column(precision = 50, scale = 0)
    private BigInteger circulatingVotingPower;

    public BigInteger getVotingTurnoutThreshold() {
        return votingTurnoutThreshold;
    }

    public void setVotingTurnoutThreshold(BigInteger votingTurnoutThreshold) {
        this.votingTurnoutThreshold = votingTurnoutThreshold;
    }

    public BigInteger getCirculatingVotingPower() {
        return circulatingVotingPower;
    }

    public void setCirculatingVotingPower(BigInteger circulatingVotingPower) {
        this.circulatingVotingPower = circulatingVotingPower;
    }
}
