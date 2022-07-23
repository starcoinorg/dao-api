package org.starcoin.dao.data.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

@Entity
@DynamicInsert
@DynamicUpdate
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler", "fieldHandler"})
public class DaoStrategyLockedVotingPower {

    @EmbeddedId
    @AttributeOverride(name = "daoId", column = @Column(name = "dao_id", length = 100, nullable = false))
    @AttributeOverride(name = "strategyId", column = @Column(name = "strategy_id", length = 100, nullable = false))
    @AttributeOverride(name = "sequenceId", column = @Column(name = "sequence_id", length = 100, nullable = false))
    private DaoStrategyLockedVotingPowerId daoStrategyLockedVotingPowerId;

    /**
     * Struct tag of the resource that the voting power is locked in.
     */
    @Column(length = 500, nullable = false)
    private String resourceStructTag;

    /**
     * BCS path to get voting power in the resource.
     */
    @Column(length = 200)
    private String votingPowerBcsPath;

    /**
     * Account address where the voting power is locked.
     */
    @Column(length = 100)
    private String accountAddress;

    public DaoStrategyLockedVotingPowerId getDaoStrategyLockedVotingPowerId() {
        return daoStrategyLockedVotingPowerId;
    }

    public void setDaoStrategyLockedVotingPowerId(DaoStrategyLockedVotingPowerId daoStrategyLockedVotingPowerId) {
        this.daoStrategyLockedVotingPowerId = daoStrategyLockedVotingPowerId;
    }

    public String getResourceStructTag() {
        return resourceStructTag;
    }

    public void setResourceStructTag(String resourceStructTag) {
        this.resourceStructTag = resourceStructTag;
    }

    public String getVotingPowerBcsPath() {
        return votingPowerBcsPath;
    }

    public void setVotingPowerBcsPath(String votingPowerBcsPath) {
        this.votingPowerBcsPath = votingPowerBcsPath;
    }

    public String getAccountAddress() {
        return accountAddress;
    }

    public void setAccountAddress(String accountAddress) {
        this.accountAddress = accountAddress;
    }
}
