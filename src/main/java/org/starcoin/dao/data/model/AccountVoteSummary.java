package org.starcoin.dao.data.model;

import java.math.BigInteger;
import java.util.List;


public interface AccountVoteSummary {
    static BigInteger getChoiceSubtotalVotingPower(int choiceSequenceId, List<AccountVoteSummary> accountVoteSummaries) {
        BigInteger vp = BigInteger.ZERO;
        for (AccountVoteSummary accountVoteSummary : accountVoteSummaries) {
            if (accountVoteSummary.getChoiceSequenceId().equals(choiceSequenceId)) {
                vp = vp.add(accountVoteSummary.getSubtotalVotingPower());
                break;
            }
        }
        return vp;
    }

    Integer getChoiceSequenceId();

    BigInteger getSubtotalVotingPower();
}
