package org.starcoin.dao.data.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class DaoMemberId implements Serializable {
    @Column(length = 100)
    private String daoId;

    @Column(length = 100)
    private String accountAddress;

    public DaoMemberId() {
    }

    public DaoMemberId(String daoId, String accountAddress) {
        this.daoId = daoId;
        this.accountAddress = accountAddress;
    }

    @Override
    public String toString() {
        return "DaoMemberId{" +
                "daoId='" + daoId + '\'' +
                ", accountAddress='" + accountAddress + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DaoMemberId that = (DaoMemberId) o;
        return Objects.equals(daoId, that.daoId) && Objects.equals(accountAddress, that.accountAddress);
    }

    @Override
    public int hashCode() {
        return Objects.hash(daoId, accountAddress);
    }
}
