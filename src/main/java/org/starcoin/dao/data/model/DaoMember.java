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
}
