package org.starcoin.dao.vo;

import javax.persistence.Column;
import java.math.BigInteger;

public class CirculatingVotingPowerAndVotingTurnoutThreshold {
    private BigInteger votingTurnoutThreshold;

    private BigInteger circulatingVotingPower;

    private Long height;

    private String stateRoot;

    public BigInteger getVotingTurnoutThreshold() {
        return votingTurnoutThreshold;
    }

    public void setVotingTurnoutThreshold(BigInteger votingTurnoutThreshold) {
        this.votingTurnoutThreshold = votingTurnoutThreshold;
    }

    public BigInteger getCirculatingVotingPower() {
        return circulatingVotingPower;
    }

    public void setCirculatingVotingPower(BigInteger circulatingVotingPower) {
        this.circulatingVotingPower = circulatingVotingPower;
    }

    public Long getHeight() {
        return height;
    }

    public void setHeight(Long height) {
        this.height = height;
    }

    public String getStateRoot() {
        return stateRoot;
    }

    public void setStateRoot(String stateRoot) {
        this.stateRoot = stateRoot;
    }
}
