package org.starcoin.dao.vo;



import java.math.BigInteger;
import java.util.List;

public class GetVotingPowerResponse {
    /**
     * The total voting power of the account.
     */
    private BigInteger totalVotingPower;

    /**
     * The voting power details of the account.
     */
    private List<VotingPowerDetail> details;

    public GetVotingPowerResponse() {
    }

    public BigInteger getTotalVotingPower() {
        return totalVotingPower;
    }

    public void setTotalVotingPower(BigInteger totalVotingPower) {
        this.totalVotingPower = totalVotingPower;
    }

    public List<VotingPowerDetail> getDetails() {
        return details;
    }

    public void setDetails(List<VotingPowerDetail> details) {
        this.details = details;
    }

    @Override
    public String toString() {
        return "GetVotingPowerResponse{" +
                "totalVotingPower=" + totalVotingPower +
                ", details=" + details +
                '}';
    }

    public static class VotingPowerDetail {
        /**
         * The voting resource sequence id.
         */
        private String sequenceId;

        private String resourceStructTag;

        /**
         * Voting power of this resource.
         */
        private BigInteger power;

        public VotingPowerDetail() {
        }


        public BigInteger getPower() {
            return power;
        }

        public void setPower(BigInteger power) {
            this.power = power;
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
    }
}
