package org.starcoin.dao.api.controller;

import io.swagger.annotations.Api;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import org.starcoin.dao.api.hateoas.event.PaginatedResultsRetrievedEvent;
import org.starcoin.dao.data.model.*;
import org.starcoin.dao.data.repo.*;
import org.starcoin.dao.service.*;
import org.starcoin.dao.vo.CastVoteRequest;
import org.starcoin.dao.vo.DaoVO;
import org.starcoin.dao.vo.GetVotingPowerResponse;
import org.starcoin.dao.vo.ProposalVO;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Optional;

import static org.starcoin.dao.api.utils.BeanUtils.convertToDaoVO;
import static org.starcoin.dao.api.utils.BeanUtils.convertToProposalVO;

@Api(tags = {"Starcoin DAO RESTful API"})
@RestController
@RequestMapping("v1")
public class DaoController {
    @Resource
    private ApplicationEventPublisher eventPublisher;

    @Resource
    private DaoRepository daoRepository;

    @Resource
    private DaoVotingResourceRepository daoVotingResourceRepository;

    @Resource
    private ProposalRepository proposalRepository;

    @Resource
    private ProposalService proposalService;

    @Resource
    private ProposalVotingChoiceRepository proposalVotingChoiceRepository;

    @Resource
    private AccountVoteRepository accountVoteRepository;

    @Resource
    private AccountVoteService accountVoteService;

    @Resource
    private VotingPowerQueryService votingPowerQueryService;

    @Resource
    private DaoStrategyService daoStrategyService;

    @Resource
    private CastVoteService castVoteService;

    @Resource
    private DaoService daoService;

    private static String[] splitByComma(String str, int expectedCount) {
        String[] a = str.split(",");
        if (a.length != expectedCount) {
            throw new IllegalArgumentException("Expected " + expectedCount + " elements, but got " + a.length);
        }
        return a;
    }

    @GetMapping("daos")
    public List<Dao> getDaos(@RequestParam("page") final int page, @RequestParam("size") final int size,
                             final UriComponentsBuilder uriBuilder, final HttpServletResponse response) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("sequenceId").ascending());
        Page<Dao> p = daoRepository.findAll(pageable);
        eventPublisher.publishEvent(new PaginatedResultsRetrievedEvent<>(Dao.class, uriBuilder, response, page,
                p.getTotalPages(), size));
        return p.getContent();
    }

    @GetMapping("daos/{daoId}")
    public DaoVO getDao(@PathVariable("daoId") String daoId) {
        Optional<Dao> dao = daoRepository.findById(daoId);
        return dao.map(d -> convertToDaoVO(d, daoStrategyService, votingPowerQueryService)).orElse(null);
    }

    @GetMapping("proposals")
    public List<Proposal> getProposals(@RequestParam(name = "daoId", required = false) String daoId,
                                       @RequestParam("page") final int page, @RequestParam("size") final int size,
                                       final UriComponentsBuilder uriBuilder, final HttpServletResponse response) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Proposal> p = proposalService.findPaginatedByDaoId(daoId, pageable);
        eventPublisher.publishEvent(new PaginatedResultsRetrievedEvent<>(Proposal.class, uriBuilder, response, page,
                p.getTotalPages(), size));
        return p.getContent();
    }

    @GetMapping("proposals/{proposalId}")
    public ProposalVO getProposal(@PathVariable(name = "proposalId") String proposalId) {
        String[] a = splitByComma(proposalId, 2);
        ProposalId id = new ProposalId(a[0], a[1]);
        Optional<Proposal> proposal = proposalRepository.findById(id);
        return proposal.map(p -> convertToProposalVO(p, proposalVotingChoiceRepository, accountVoteRepository))
                .orElse(null);
    }

    @GetMapping("accountVotes")
    public List<AccountVote> getAccountVotes(@RequestParam("daoId") String daoId,
                                             @RequestParam("proposalNumber") String proposalNumber,
                                             @RequestParam("page") final int page, @RequestParam("size") final int size,
                                             final UriComponentsBuilder uriBuilder, final HttpServletResponse response) {
        Pageable pageable = PageRequest.of(page, size);
        Page<AccountVote> p = accountVoteService.findPaginatedByDaoIdAndProposalNumber(daoId, proposalNumber, pageable);
        eventPublisher.publishEvent(new PaginatedResultsRetrievedEvent<>(AccountVote.class, uriBuilder, response, page,
                p.getTotalPages(), size));
        return p.getContent();
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
        return votingPowerQueryService.getAccountVotingPower(accountAddress, daoId, proposalNumber);
    }

    @PostMapping("castVote")
    public void castVote(@RequestBody CastVoteRequest request) {
        castVoteService.castVote(request);
    }

    @PutMapping("daos/{daoId}")
    public void putDao(@PathVariable("daoId") String daoId, @RequestBody Dao dao) {
        dao.setDaoId(daoId);
        daoService.addOrUpdateDao(dao);
    }

    @PutMapping("daos/{daoId}/daoStrategies/{strategyId}")
    public void putDaoStrategy(@PathVariable("daoId") String daoId,
                               @PathVariable("strategyId") String strategyId,
                               @RequestBody DaoStrategy daoStrategy) {
        DaoStrategyId daoStrategyId = new DaoStrategyId(daoId, strategyId);
        daoStrategy.setDaoStrategyId(daoStrategyId);
        daoStrategyService.addOrUpdateDaoStrategy(daoStrategy);
    }

    @PutMapping("daos/{daoId}/daoVotingResource/{sequenceId}")
    public void putDaoVotingResource(@PathVariable("daoId") String daoId,
                                     @PathVariable("sequenceId") String sequenceId,
                                     @RequestBody DaoVotingResource daoVotingResource) {
        DaoVotingResourceId daoVotingResourceId = new DaoVotingResourceId(daoId, sequenceId);
        daoVotingResource.setDaoVotingResourceId(daoVotingResourceId);
        daoService.addOrUpdateDaoVotingResource(daoVotingResource);
    }

    @PutMapping("daos/{daoId}/proposals/{proposalNumber}")
    public void putProposal(@PathVariable("daoId") String daoId,
                            @PathVariable("proposalNumber") String proposalNumber,
                            @RequestBody Proposal proposal) {
        ProposalId proposalId = new ProposalId(daoId, proposalNumber);
        proposal.setProposalId(proposalId);
        proposalService.addOrUpdateProposal(proposal);
    }

    @PutMapping("daos/{daoId}/proposals/{proposalNumber}/proposalVotingChoices/{choiceSequenceId}")
    public void putProposalVotingChoice(@PathVariable("daoId") String daoId,
                                        @PathVariable("proposalNumber") String proposalNumber,
                                        @PathVariable("choiceSequenceId") Integer choiceSequenceId,
                                        @RequestBody ProposalVotingChoice proposalVotingChoice) {
        ProposalVotingChoiceId proposalVotingChoiceId = new ProposalVotingChoiceId(
                new ProposalId(daoId, proposalNumber), choiceSequenceId);
        proposalVotingChoice.setProposalVotingChoiceId(proposalVotingChoiceId);
        proposalService.addOrUpdateProposalVotingChoice(proposalVotingChoice);
    }

}
