package org.starcoin.dao.data.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class DaoStrategyId implements Serializable {
    @Column(length = 100)
    private String daoId;

    @Column(length = 100)
    private String strategyId;

    public DaoStrategyId() {
    }

    public DaoStrategyId(String daoId, String strategyId) {
        this.daoId = daoId;
        this.strategyId = strategyId;
    }

    public String getDaoId() {
        return daoId;
    }

    public void setDaoId(String daoId) {
        this.daoId = daoId;
    }

    public String getStrategyId() {
        return strategyId;
    }

    public void setStrategyId(String strategyId) {
        this.strategyId = strategyId;
    }

    @Override
    public String toString() {
        return "DaoStrategyId{" +
                "daoId='" + daoId + '\'' +
                ", strategyId='" + strategyId + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DaoStrategyId that = (DaoStrategyId) o;
        return Objects.equals(daoId, that.daoId) && Objects.equals(strategyId, that.strategyId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(daoId, strategyId);
    }
}
