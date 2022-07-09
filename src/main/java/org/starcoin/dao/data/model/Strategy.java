package org.starcoin.dao.data.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@DynamicInsert
@DynamicUpdate
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler", "fieldHandler"})
public class Strategy {
    /**
     * Using SBT value as voting power.
     */
    public static final String STRATEGY_ID_SBT = "SBT";

    /**
     * Use a set of numeric fields in voting-resources as voting power.
     */
    public static final String STRATEGY_ID_VOTING_RESOURCES = "RESOURCES";

    public static final Strategy SBT = new Strategy(STRATEGY_ID_SBT,
            "Using SBT value as voting power");

    public static final Strategy VOTING_RESOURCES = new Strategy(STRATEGY_ID_VOTING_RESOURCES,
            "Use total amount of a specific set of resources owned by account as voting power");

    @Id
    @Column(length = 100)
    private String strategyId;


    @Column(length = 500, nullable = false)
    private String description;


    @Column
    private Integer sequenceId;

    public String getStrategyId() {
        return strategyId;
    }

    public void setStrategyId(String strategyId) {
        this.strategyId = strategyId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getSequenceId() {
        return sequenceId;
    }

    public void setSequenceId(Integer sequenceId) {
        this.sequenceId = sequenceId;
    }

    public Strategy(String strategyId, String description) {
        this.strategyId = strategyId;
        this.description = description;
    }

    public Strategy() {
    }
}
