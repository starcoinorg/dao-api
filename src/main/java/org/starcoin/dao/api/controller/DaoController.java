package org.starcoin.dao.api.controller;

import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.*;
import org.starcoin.dao.data.model.*;
import org.starcoin.dao.data.repo.*;
import org.starcoin.dao.service.CastVoteService;
import org.starcoin.dao.service.VotingPowerService;
import org.starcoin.dao.vo.CastVoteRequest;
import org.starcoin.dao.vo.DaoVO;
import org.starcoin.dao.vo.GetVotingPowerResponse;
import org.starcoin.dao.vo.ProposalVO;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

import static org.starcoin.dao.api.utils.BeanUtils.convertToDaoVO;
import static org.starcoin.dao.api.utils.BeanUtils.convertToProposalVO;

@Api(tags = {"Starcoin DAO RESTful API"})
@RestController
@RequestMapping("v1")
public class DaoController {
    @Resource
    private DaoRepository daoRepository;

    @Resource
    private DaoVotingResourceRepository daoVotingResourceRepository;

    @Resource
    private ProposalRepository proposalRepository;

    @Resource
    private ProposalVotingChoiceRepository proposalVotingChoiceRepository;

    @Resource
    private AccountVoteRepository accountVoteRepository;

    @Resource
    private VotingPowerService votingPowerService;

    @Resource
    private CastVoteService castVoteService;

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
    public DaoVO getDao(@PathVariable("daoId") String daoId) {
        Optional<Dao> dao = daoRepository.findById(daoId);
        return dao.map(d -> convertToDaoVO(d, daoVotingResourceRepository)).orElse(null);
    }

    @GetMapping("proposals")
    public List<Proposal> getProposals(@RequestParam(name = "daoId",required = false) String daoId) {
        if (daoId == null) {
            return proposalRepository.findAll();
        }
        return proposalRepository.findByProposalId_DaoId(daoId);
    }

    @GetMapping("proposals/{proposalId}")
    public ProposalVO getProposal(@PathVariable(name = "proposalId") String proposalId) {
        String[] a = splitByComma(proposalId, 2);
        ProposalId id = new ProposalId(a[0], a[1]);
        Optional<Proposal> proposal = proposalRepository.findById(id);
        return proposal.map(p -> convertToProposalVO(p, proposalVotingChoiceRepository)).orElse(null);
    }

    @GetMapping("accountVotes")
    public List<AccountVote> getAccountVotes(@RequestParam("daoId") String daoId,
                                             @RequestParam("proposalNumber") String proposalNumber) {
        return accountVoteRepository.findByDaoIdAndProposalNumber(daoId, proposalNumber);
    }

    @GetMapping("sumAccountVotesGroupByChoice")
    public List<AccountVoteSummary> sumAccountVotesGroupByChoice(@RequestParam("daoId") String daoId,
                                                                 @RequestParam("proposalNumber") String proposalNumber) {
        return accountVoteRepository.sumAccountVotesGroupByChoice(daoId, proposalNumber);
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
        return votingPowerService.getAccountVotingPower(accountAddress, daoId, proposalNumber);
    }

    @PostMapping("castVote")
    public void castVote(@RequestBody CastVoteRequest request) {
        castVoteService.castVote(request);
    }


}
