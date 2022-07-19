package org.starcoin.dao.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.starcoin.dao.data.model.AccountVote;
import org.starcoin.dao.data.model.AccountVoteId;
import org.starcoin.dao.data.model.ProposalId;
import org.starcoin.dao.data.repo.AccountVoteRepository;

@Service
public class AccountVoteService {
    private static final Logger LOG = LoggerFactory.getLogger(AccountVoteService.class);

    @Autowired
    private AccountVoteRepository accountVoteRepository;

    public Page<AccountVote> findPaginatedByDaoIdAndProposalNumber(String daoId, String proposalNumber, Pageable pageable) {
        //        Specification<AccountVote> specification = new Specification<AccountVote>() {
        //            @Override
        //            public Predicate toPredicate(Root<AccountVote> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        //                List<Predicate> predicates = new ArrayList<>();
        //                predicates.add(criteriaBuilder.equal(root.get("accountVoteId.daoId"),daoId));
        //                predicates.add(criteriaBuilder.equal(root.get("accountVoteId.proposalNumber"),proposalNumber));
        //                //predicates.add(criteriaBuilder.notEqual(root.get("accountVoteId.accountAddress"), "0x01"));
        //                return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        //            }
        //        };
        //        return accountVoteRepository.findAll(specification, pageable);

        AccountVote accountVote = new AccountVote();
        AccountVoteId accountVoteId = new AccountVoteId(null, new ProposalId(daoId, proposalNumber));
        accountVote.setAccountVoteId(accountVoteId);
        Example<AccountVote> accountVoteExample = Example.of(accountVote);
        return accountVoteRepository.findAll(accountVoteExample, pageable);
    }

    public void addIfNotExists(AccountVote accountVote) {
        AccountVoteId accountVoteId = accountVote.getAccountVoteId();
        AccountVote p = accountVoteRepository.findById(accountVoteId).orElse(null);
        if (p == null) {
            accountVoteRepository.save(accountVote);
        }
    }

}
