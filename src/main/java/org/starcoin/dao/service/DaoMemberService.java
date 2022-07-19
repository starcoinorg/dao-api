package org.starcoin.dao.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.starcoin.dao.data.model.DaoMember;
import org.starcoin.dao.data.model.DaoMemberId;
import org.starcoin.dao.data.repo.DaoMemberRepository;

@Service
public class DaoMemberService {
    @Autowired
    private DaoMemberRepository daoMemberRepository;

    public void addIfNotExists(DaoMember daoMember) {
        DaoMemberId daoMemberId = daoMember.getDaoMemberId();
        DaoMember d = daoMemberRepository.findById(daoMemberId).orElse(null);
        if (d == null) {
            daoMemberRepository.save(daoMember);
        }
    }
}
