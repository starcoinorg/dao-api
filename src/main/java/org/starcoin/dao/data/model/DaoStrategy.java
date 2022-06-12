package org.starcoin.dao.data.model;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

@Entity
public class DaoStrategy {

    @EmbeddedId
    @AttributeOverride(name = "daoId", column = @Column(name = "dao_id", length = 100, nullable = false))
    @AttributeOverride(name = "strategyId", column = @Column(name = "strategy_id", length = 100, nullable = false))
    private DaoStrategyId daoStrategyId;

    public DaoStrategyId getDaoStrategyId() {
        return daoStrategyId;
    }

    public void setDaoStrategyId(DaoStrategyId daoStrategyId) {
        this.daoStrategyId = daoStrategyId;
    }

}