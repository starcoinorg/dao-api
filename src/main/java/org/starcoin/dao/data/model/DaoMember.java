package org.starcoin.dao.data.model;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

@Entity
public class DaoMember {

    @EmbeddedId
    @AttributeOverride(name = "daoId", column = @Column(name = "dao_id", length = 100, nullable = false))
    @AttributeOverride(name = "accountAddress", column = @Column(name = "account_address", length = 100, nullable = false))
    private DaoMemberId daoMemberId;

    public DaoMemberId getDaoMemberId() {
        return daoMemberId;
    }

    public void setDaoMemberId(DaoMemberId daoMemberId) {
        this.daoMemberId = daoMemberId;
    }
}
