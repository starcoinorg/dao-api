package org.starcoin.dao.data.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import java.math.BigInteger;

@Entity
@DynamicInsert
@DynamicUpdate
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler", "fieldHandler"})
public class DaoMember {

    @EmbeddedId
    @AttributeOverride(name = "daoId", column = @Column(name = "dao_id", length = 100, nullable = false))
    @AttributeOverride(name = "accountAddress", column = @Column(name = "account_address", length = 100, nullable = false))
    private DaoMemberId daoMemberId;

    @Column
    private Long onChainMemberId;


    /**
     * The on-chain SBT value of this member.
     */
    @Column(precision = 50, scale = 0)
    private BigInteger sbtAmount;

    @Column
    private Long joinedAtBlockHeight;

    @Column
    private Long quitAtBlockHeight;

    @Column
    private Boolean deactivated;

    @Column(length = 70)
    private String createdBy;

    @Column(length = 70)
    private String updatedBy;

    @Column
    private Long createdAt;

    @Column
    private Long updatedAt;

    public DaoMemberId getDaoMemberId() {
        return daoMemberId;
    }

    public void setDaoMemberId(DaoMemberId daoMemberId) {
        this.daoMemberId = daoMemberId;
    }

    public Long getOnChainMemberId() {
        return onChainMemberId;
    }

    public void setOnChainMemberId(Long onChainMemberId) {
        this.onChainMemberId = onChainMemberId;
    }

    public BigInteger getSbtAmount() {
        return sbtAmount;
    }

    public void setSbtAmount(BigInteger sbtAmount) {
        this.sbtAmount = sbtAmount;
    }

    public Long getJoinedAtBlockHeight() {
        return joinedAtBlockHeight;
    }

    public void setJoinedAtBlockHeight(Long joinedAtBlockHeight) {
        this.joinedAtBlockHeight = joinedAtBlockHeight;
    }

    public Long getQuitAtBlockHeight() {
        return quitAtBlockHeight;
    }

    public void setQuitAtBlockHeight(Long quitAtBlockHeight) {
        this.quitAtBlockHeight = quitAtBlockHeight;
    }

    public Boolean getDeactivated() {
        return deactivated;
    }

    public void setDeactivated(Boolean deactivated) {
        this.deactivated = deactivated;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Long createdAt) {
        this.createdAt = createdAt;
    }

    public Long getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Long updatedAt) {
        this.updatedAt = updatedAt;
    }
}
