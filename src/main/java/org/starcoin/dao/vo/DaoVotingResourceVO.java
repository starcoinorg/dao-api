package org.starcoin.dao.vo;


public class DaoVotingResourceVO {
    /**
     * Resource sequence id.
     */
    private String sequenceId;
    /**
     * For example: "0x8c109349c6bd91411d6bc962e080c4a3::TokenSwapFarmBoost::UserInfo<0x00000000000000000000000000000001::STC::STC, 0x8c109349c6bd91411d6bc962e080c4a3::STAR::STAR>"
     */
    private String resourceStructTag;

    /**
     * BCS path to get voting power.
     */
    private String votingPowerBcsPath;

    public DaoVotingResourceVO() {
    }

    public DaoVotingResourceVO(String sequenceId, String resourceStructTag, String votingPowerBcsPath) {
        this.sequenceId = sequenceId;
        this.resourceStructTag = resourceStructTag;
        this.votingPowerBcsPath = votingPowerBcsPath;
    }

    public String getSequenceId() {
        return sequenceId;
    }

    public void setSequenceId(String sequenceId) {
        this.sequenceId = sequenceId;
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