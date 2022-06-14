package org.starcoin.dao.data.model;

import java.util.ArrayList;
import java.util.List;

public class VotingType {
    public static final String SINGLE_CHOICE = "SINGLE_CHOICE";
    public static final String YES_NO = "YES_NO";
    public static final String CHOICE_TITLE_YES = "YES";
    public static final String CHOICE_TITLE_NO = "NO";

    public static List<ProposalVotingChoice> getYesNoChoices(ProposalId proposalId) {
        List<ProposalVotingChoice> choices = new ArrayList<>();
        choices.add(new ProposalVotingChoice(proposalId, 0, CHOICE_TITLE_YES));
        choices.add(new ProposalVotingChoice(proposalId, 1, CHOICE_TITLE_NO));
        return choices;
    }
}
