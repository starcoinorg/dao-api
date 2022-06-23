package org.starcoin.dao.vo;

import org.starcoin.dao.data.model.AccountVoteSummary;
import org.starcoin.dao.data.model.Proposal;

import java.util.ArrayList;
import java.util.List;

import static org.starcoin.dao.data.model.VotingType.CHOICE_TITLE_NO;
import static org.starcoin.dao.data.model.VotingType.CHOICE_TITLE_YES;

public class ProposalVO extends Proposal {
    private List<ProposalVotingChoice> proposalVotingChoices;

    private List<AccountVoteSummary> accountVoteSummaries;

    public static List<ProposalVotingChoice> getYesNoChoices() {
        List<ProposalVotingChoice> choices = new ArrayList<>();
        choices.add(new ProposalVotingChoice(0, CHOICE_TITLE_YES));
        choices.add(new ProposalVotingChoice(1, CHOICE_TITLE_NO));
        return choices;
    }

    public List<ProposalVotingChoice> getProposalVotingChoices() {
        return proposalVotingChoices;
    }

    public void setProposalVotingChoices(List<ProposalVotingChoice> proposalVotingChoices) {
        this.proposalVotingChoices = proposalVotingChoices;
    }

    public List<AccountVoteSummary> getAccountVoteSummaries() {
        return accountVoteSummaries;
    }

    public void setAccountVoteSummaries(List<AccountVoteSummary> accountVoteSummaries) {
        this.accountVoteSummaries = accountVoteSummaries;
    }

    public static class ProposalVotingChoice {
        private Integer sequenceId;
        private String title;

        public ProposalVotingChoice() {
        }

        public ProposalVotingChoice(Integer sequenceId, String title) {
            this.sequenceId = sequenceId;
            this.title = title;
        }

        public Integer getSequenceId() {
            return sequenceId;
        }

        public void setSequenceId(Integer sequenceId) {
            this.sequenceId = sequenceId;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        @Override
        public String toString() {
            return "ProposalVotingChoice{" +
                    "sequenceId=" + sequenceId +
                    ", title='" + title + '\'' +
                    '}';
        }
    }
}
