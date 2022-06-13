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
