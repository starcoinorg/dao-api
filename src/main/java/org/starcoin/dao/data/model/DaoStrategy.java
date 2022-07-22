package org.starcoin.dao.data.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@DynamicInsert
@DynamicUpdate
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler", "fieldHandler"})
@Table(uniqueConstraints = {@UniqueConstraint(name = "UniqueDaoStrategyType", columnNames = {"dao_id", "dao_strategy_type"})})
public class DaoStrategy {

    public static final String DAO_STRATEGY_TYPE_ON_CHAIN = "ON_CHAIN";
    public static final String DAO_STRATEGY_TYPE_OFF_CHAIN = "OFF_CHAIN";

    @EmbeddedId
    @AttributeOverride(name = "daoId", column = @Column(name = "dao_id", length = 100, nullable = false))
    @AttributeOverride(name = "strategyId", column = @Column(name = "strategy_id", length = 100, nullable = false))
    private DaoStrategyId daoStrategyId;

    @Column
    private Integer sequenceId;

    @Column
    private Integer votingPowerDecimals;

    @Column(length = 100)
    private String votingPowerName;

    @Column(length = 500)
    private String description;

    @Column(length = 500)
    private String votingPowerSupplyResourceStructTag;

    @Column(length = 200)
    private String votingPowerSupplyBcsPath;

    @Column(length = 100)
    private String votingPowerSupplyAccountAddress;

    @Column(precision = 51, scale = 10)
    private BigDecimal votingTurnoutThresholdRate;

    @Column(name = "dao_strategy_type", length = 50)
    private String daoStrategyType;

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

    public String getVotingPowerName() {
        return votingPowerName;
    }

    public void setVotingPowerName(String votingPowerName) {
        this.votingPowerName = votingPowerName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getVotingPowerSupplyResourceStructTag() {
        return votingPowerSupplyResourceStructTag;
    }

    public void setVotingPowerSupplyResourceStructTag(String votingPowerSupplyResourceStructTag) {
        this.votingPowerSupplyResourceStructTag = votingPowerSupplyResourceStructTag;
    }

    public String getVotingPowerSupplyBcsPath() {
        return votingPowerSupplyBcsPath;
    }

    public void setVotingPowerSupplyBcsPath(String votingPowerSupplyBcsPath) {
        this.votingPowerSupplyBcsPath = votingPowerSupplyBcsPath;
    }

    public String getVotingPowerSupplyAccountAddress() {
        return votingPowerSupplyAccountAddress;
    }

    public void setVotingPowerSupplyAccountAddress(String votingPowerSupplyAccountAddress) {
        this.votingPowerSupplyAccountAddress = votingPowerSupplyAccountAddress;
    }

    public BigDecimal getVotingTurnoutThresholdRate() {
        return votingTurnoutThresholdRate;
    }

    public void setVotingTurnoutThresholdRate(BigDecimal votingTurnoutThresholdRate) {
        this.votingTurnoutThresholdRate = votingTurnoutThresholdRate;
    }

    public String getDaoStrategyType() {
        return daoStrategyType;
    }

    public void setDaoStrategyType(String daoStrategyType) {
        this.daoStrategyType = daoStrategyType;
    }
}