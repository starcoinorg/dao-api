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
public class DaoStrategy {

    @EmbeddedId
    @AttributeOverride(name = "daoId", column = @Column(name = "dao_id", length = 100, nullable = false))
    @AttributeOverride(name = "strategyId", column = @Column(name = "strategy_id", length = 100, nullable = false))
    private DaoStrategyId daoStrategyId;


    @Column
    private Integer sequenceId;

    @Column
    private Integer votingPowerDecimals;

    public DaoStrategyId getDaoStrategyId() {
        return daoStrategyId;
    }

    public void setDaoStrategyId(DaoStrategyId daoStrategyId) {
        this.daoStrategyId = daoStrategyId;
    }

    public Integer getSequenceId() {
        return sequenceId;
    }

    public void setSequenceId(Integer sequenceId) {
        this.sequenceId = sequenceId;
    }

    public Integer getVotingPowerDecimals() {
        return votingPowerDecimals;
    }

    public void setVotingPowerDecimals(Integer votingPowerDecimals) {
        this.votingPowerDecimals = votingPowerDecimals;
    }
}