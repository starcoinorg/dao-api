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
public class DaoVotingResource {
    @EmbeddedId
    @AttributeOverride(name = "daoId", column = @Column(name = "dao_id", length = 100, nullable = false))
    @AttributeOverride(name = "sequenceId", column = @Column(name = "sequence_id", length = 100, nullable = false))
    private DaoVotingResourceId daoVotingResourceId;

    /**
     * For example: "0x8c109349c6bd91411d6bc962e080c4a3::TokenSwapFarmBoost::UserInfo<0x00000000000000000000000000000001::STC::STC, 0x8c109349c6bd91411d6bc962e080c4a3::STAR::STAR>"
     */
    @Column(length = 500, nullable = false)
    private String resourceStructTag;

    /**
     * BCS path to get voting power.
     */
    @Column(length = 200)
    private String votingPowerBcsPath;

    public DaoVotingResourceId getDaoVotingResourceId() {
        return daoVotingResourceId;
    }

    public void setDaoVotingResourceId(DaoVotingResourceId daoVotingResourceId) {
        this.daoVotingResourceId = daoVotingResourceId;
    }

    public String getResourceStructTag() {
        return resourceStructTag;
    }

    public void setResourceStructTag(String resourceStructTag) {
        this.resourceStructTag = resourceStructTag;
    }

    public String getVotingPowerBcsPath() {
        return votingPowerBcsPath;
    }

    public void setVotingPowerBcsPath(String votingPowerBcsPath) {
        this.votingPowerBcsPath = votingPowerBcsPath;
    }
}
