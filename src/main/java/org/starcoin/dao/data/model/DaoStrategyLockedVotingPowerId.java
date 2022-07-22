package org.starcoin.dao.data.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class DaoStrategyLockedVotingPowerId implements Serializable {
    private DaoStrategyId daoStrategyId = new DaoStrategyId();

    @Column
    private Integer sequenceId;

    public DaoStrategyLockedVotingPowerId() {
    }

    public DaoStrategyLockedVotingPowerId(DaoStrategyId daoStrategyId, Integer sequenceId) {
        this.daoStrategyId = daoStrategyId;
        this.sequenceId = sequenceId;
    }

    @Column(length = 100)
    protected String getDaoId() {
        return daoStrategyId.getDaoId();
    }

    protected void setDaoId(String daoId) {
        daoStrategyId.setDaoId(daoId);
    }

    @Column(length = 100)
    protected String getStrategyId() {
        return daoStrategyId.getStrategyId();
    }

    protected void setStrategyId(String strategyId) {
        daoStrategyId.setStrategyId(strategyId);
    }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DaoStrategyLockedVotingPowerId that = (DaoStrategyLockedVotingPowerId) o;
        return Objects.equals(daoStrategyId, that.daoStrategyId) && Objects.equals(sequenceId, that.sequenceId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(daoStrategyId, sequenceId);
    }

    @Override
    public String toString() {
        return "DaoStrategyLockedVotingPowerId{" +
                "daoStrategyId=" + daoStrategyId +
                ", sequenceId=" + sequenceId +
                '}';
    }
}
