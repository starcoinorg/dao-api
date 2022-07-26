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

    public void addOrUpdate(DaoMember daoMember) {
        DaoMemberId daoMemberId = daoMember.getDaoMemberId();
        DaoMember d = daoMemberRepository.findById(daoMemberId).orElse(null);
        if (d == null) {
            daoMember.setCreatedAt(System.currentTimeMillis());
            daoMember.setUpdatedAt(System.currentTimeMillis());
            daoMemberRepository.save(daoMember);
        } else if (daoMember.getJoinedAtBlockHeight()
                > (d.getQuitAtBlockHeight() != null ? d.getQuitAtBlockHeight() : 0)) {
            d.setUpdatedAt(System.currentTimeMillis());
            d.setDeactivated(false); // re-activate it
            d.setSbtAmount(daoMember.getSbtAmount());
            d.setJoinedAtBlockHeight(daoMember.getJoinedAtBlockHeight());
            d.setQuitAtBlockHeight(null);
            daoMemberRepository.save(d);
        }
    }

    public void revokeDaoMember(DaoMember daoMember) {
        daoMemberQuit(daoMember);
    }

    public void daoMemberQuit(DaoMember daoMember) {
        DaoMemberId daoMemberId = daoMember.getDaoMemberId();
        DaoMember d = daoMemberRepository.findById(daoMemberId).orElse(null);
        if (d == null) {
            daoMember.setCreatedAt(System.currentTimeMillis());
            daoMember.setUpdatedAt(System.currentTimeMillis());
            daoMember.setDeactivated(true);
            daoMemberRepository.save(daoMember);
        } else if (daoMember.getQuitAtBlockHeight()
                > (d.getJoinedAtBlockHeight() != null ? d.getJoinedAtBlockHeight() : 0)) {
            d.setUpdatedAt(System.currentTimeMillis());
            d.setDeactivated(true);
            d.setSbtAmount(daoMember.getSbtAmount());
            d.setQuitAtBlockHeight(daoMember.getQuitAtBlockHeight());
            d.setJoinedAtBlockHeight(null);
            daoMemberRepository.save(d);
        }
    }
}
