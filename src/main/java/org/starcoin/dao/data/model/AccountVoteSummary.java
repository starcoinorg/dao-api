package org.starcoin.dao.data.model;

import java.math.BigInteger;


public interface AccountVoteSummary {
    Integer getChoiceSequenceId();

    BigInteger getSubtotalVotingPower();
}
