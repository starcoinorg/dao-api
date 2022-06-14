package org.starcoin.dao.api.controller;

import io.swagger.annotations.Api;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;
import org.starcoin.dao.data.model.*;
import org.starcoin.dao.data.repo.AccountVoteRepository;
import org.starcoin.dao.data.repo.DaoRepository;
import org.starcoin.dao.data.repo.ProposalRepository;
import org.starcoin.dao.data.repo.ProposalVotingChoiceRepository;
import org.starcoin.dao.vo.CastVoteRequest;
import org.starcoin.dao.vo.GetVotingPowerResponse;
import org.starcoin.dao.vo.ProposalVO;

import javax.annotation.Resource;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Api(tags = {"Starcoin DAO RESTful API"})
@RestController
@RequestMapping("v1")
public class DaoController {
    @Resource
    private DaoRepository daoRepository;

    @Resource
    private ProposalRepository proposalRepository;

    @Resource
    private ProposalVotingChoiceRepository proposalVotingChoiceRepository;

    @Resource
    private AccountVoteRepository accountVoteRepository;

    private static String[] splitByComma(String str, int expectedCount) {
        String[] a = str.split(",");
        if (a.length != expectedCount) {
            throw new IllegalArgumentException("Expected " + expectedCount + " elements, but got " + a.length);
        }
        return a;
    }

    @GetMapping("daos")
    public List<Dao> getDaos() {
        return daoRepository.findAll();
    }

    @GetMapping("daos/{daoId}")
    public Dao getDao(@PathVariable("daoId") String daoId) {
        return daoRepository.findById(daoId).orElse(null);
    }

    @GetMapping("proposals")
    public List<Proposal> getProposals() {
        return proposalRepository.findAll();
    }

    @GetMapping("proposals/{proposalId}")
    public ProposalVO getProposal(@PathVariable(name = "proposalId") String proposalId) {
        String[] a = splitByComma(proposalId, 2);
        ProposalId id = new ProposalId(a[0], a[1]);
        Optional<Proposal> proposal = proposalRepository.findById(id);
        if (!proposal.isPresent()) {
            return null;
        }
        return convertToProposalVO(proposal.get());
    }

    @GetMapping("accountVotes")
    public List<AccountVote> getAccountVotes(@RequestParam("daoId") String daoId,
                                             @RequestParam("proposalNumber") String proposalNumber) {
        return accountVoteRepository.findByDaoIdAndProposalNumber(daoId, proposalNumber);
    }

    @GetMapping("accountVotes/{accountVoteId}")
    public AccountVote getAccountVote(@PathVariable(name = "accountVoteId") String accountVoteId) {
        String[] a = splitByComma(accountVoteId, 3);
        AccountVoteId id = new AccountVoteId(a[0], new ProposalId(a[1], a[2]));
        Optional<AccountVote> accountVote = accountVoteRepository.findById(id);
        return accountVote.orElse(null);
    }

    @GetMapping("getVotingPower")
    public GetVotingPowerResponse getVotingPower(
            @RequestParam(required = true) String accountAddress,
            @RequestParam(required = true) String daoId,
            @RequestParam(required = true) String proposalNumber) {
        GetVotingPowerResponse r = new GetVotingPowerResponse();
        //todo
        r.setTotalVotingPower(BigInteger.valueOf(11111L));
        List<GetVotingPowerResponse.VotingPowerDetail> details = new ArrayList<>();
        GetVotingPowerResponse.VotingPowerDetail detail = new GetVotingPowerResponse.VotingPowerDetail();
        detail.setSequenceId("0");
        detail.setResourceStructTag("0x8c109349c6bd91411d6bc962e080c4a3::TokenSwapFarmBoost::UserInfo<" +
                "0x00000000000000000000000000000001::STC::STC, " +
                "0x8c109349c6bd91411d6bc962e080c4a3::STAR::STAR" +
                ">");
        detail.setPower(BigInteger.valueOf(11111L));
        details.add(detail);
        r.setDetails(details);
        return r;
    }

    @PostMapping("castVote")
    public void castVote(@RequestBody CastVoteRequest request) {
        //todo
    }

    @NotNull
    private ProposalVO convertToProposalVO(Proposal proposal) {
        ProposalVO proposalVO = new ProposalVO();
        BeanUtils.copyProperties(proposal, proposalVO);
        if (VotingType.YES_NO.equals(proposalVO.getVotingType())) {
            proposalVO.setProposalVotingChoices(ProposalVO.getYesNoChoices());
        } else if (VotingType.SINGLE_CHOICE.equals(proposalVO.getVotingType())) {
            List<ProposalVO.ProposalVotingChoice> choices = new ArrayList<>();
            for (ProposalVotingChoice c : proposalVotingChoiceRepository.findByProposalVotingChoiceId_ProposalId(proposalVO.getProposalId())) {
                ProposalVO.ProposalVotingChoice c2 = new ProposalVO.ProposalVotingChoice(
                        c.getProposalVotingChoiceId().getSequenceId(), c.getTitle());
                choices.add(c2);
            }
            proposalVO.setProposalVotingChoices(choices);
        }
        return proposalVO;
    }

}
