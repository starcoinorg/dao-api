package org.starcoin.dao.data.model;

import java.util.ArrayList;
import java.util.List;

public class VotingType {
    public static final String SINGLE_CHOICE = "SINGLE_CHOICE";
    public static final String YES_NO = "YES_NO";

    public static final String STANDARD = "STANDARD";

    public static final String CHOICE_TITLE_YES = "YES";
    public static final String CHOICE_TITLE_NO = "NO";

    public static final byte STANDARD_VOTING_CHOICE_ID_YES = 1;
    public static final byte STANDARD_VOTING_CHOICE_ID_NO = 2;
    public static final byte STANDARD_VOTING_CHOICE_ID_NO_WITH_VETO = 3;
    public static final byte STANDARD_VOTING_CHOICE_ID_ABSTAIN = 4;

    public static final String STANDARD_VOTING_CHOICE_TITLE_YES = "Yes";
    public static final String STANDARD_VOTING_CHOICE_TITLE_NO = "No";
    public static final String STANDARD_VOTING_CHOICE_TITLE_NO_WITH_VETO = "No with veto";
    public static final String STANDARD_VOTING_CHOICE_TITLE_ABSTAIN = "Abstain";

    public static List<ProposalVotingChoice> getYesNoChoices(ProposalId proposalId) {
        List<ProposalVotingChoice> choices = new ArrayList<>();
        choices.add(new ProposalVotingChoice(proposalId, 0, CHOICE_TITLE_YES));
        choices.add(new ProposalVotingChoice(proposalId, 1, CHOICE_TITLE_NO));
        return choices;
    }

    public static List<ProposalVotingChoice> getStandardVotingChoices(ProposalId proposalId) {
        List<ProposalVotingChoice> choices = new ArrayList<>();
        choices.add(new ProposalVotingChoice(proposalId, (int) STANDARD_VOTING_CHOICE_ID_YES, STANDARD_VOTING_CHOICE_TITLE_YES));
        choices.add(new ProposalVotingChoice(proposalId, (int) STANDARD_VOTING_CHOICE_ID_NO, STANDARD_VOTING_CHOICE_TITLE_NO));
        choices.add(new ProposalVotingChoice(proposalId, (int) STANDARD_VOTING_CHOICE_ID_NO_WITH_VETO, STANDARD_VOTING_CHOICE_TITLE_NO_WITH_VETO));
        choices.add(new ProposalVotingChoice(proposalId, (int) STANDARD_VOTING_CHOICE_ID_ABSTAIN, STANDARD_VOTING_CHOICE_TITLE_ABSTAIN));
        return choices;
    }
}
