package org.starcoin.dao.data.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class DaoVotingResourceId implements Serializable {
    @Column(length = 100)
    private String daoId;

    @Column(length = 100)
    private String sequenceId;

    public DaoVotingResourceId() {
    }

    public DaoVotingResourceId(String daoId, String sequenceId) {
        this.daoId = daoId;
        this.sequenceId = sequenceId;
    }

    public String getDaoId() {
        return daoId;
    }

    public void setDaoId(String daoId) {
        this.daoId = daoId;
    }

    public String getSequenceId() {
        return sequenceId;
    }

    public void setSequenceId(String sequenceId) {
        this.sequenceId = sequenceId;
    }

    @Override
    public String toString() {
        return "DaoVotingResourceId{" +
                "daoId='" + daoId + '\'' +
                ", sequenceId='" + sequenceId + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DaoVotingResourceId that = (DaoVotingResourceId) o;
        return Objects.equals(daoId, that.daoId) && Objects.equals(sequenceId, that.sequenceId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(daoId, sequenceId);
    }
}
