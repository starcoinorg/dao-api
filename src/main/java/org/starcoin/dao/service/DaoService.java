package org.starcoin.dao.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.starcoin.dao.data.model.Dao;
import org.starcoin.dao.data.model.DaoVotingResource;
import org.starcoin.dao.data.model.DaoVotingResourceId;
import org.starcoin.dao.data.repo.DaoRepository;
import org.starcoin.dao.data.repo.DaoVotingResourceRepository;

@Service
public class DaoService {
    @Autowired
    private DaoRepository daoRepository;

    @Autowired
    private DaoVotingResourceRepository daoVotingResourceRepository;

    public void addOrUpdateDao(Dao dao) {
        String daoId = dao.getDaoId();
        Dao d = daoRepository.findById(daoId).orElse(null);
        if (d == null) {
            //dao.setCreatedAt(System.currentTimeMillis());
            daoRepository.save(dao);
        } else {
            BeanUtils.copyProperties(dao, d);
            daoRepository.save(d);
        }
    }

    public void addOrUpdateDaoVotingResource(DaoVotingResource daoVotingResource) {
        DaoVotingResourceId daoVotingResourceId = daoVotingResource.getDaoVotingResourceId();
        DaoVotingResource d = daoVotingResourceRepository.findById(daoVotingResourceId).orElse(null);
        if (d == null) {
            daoVotingResourceRepository.save(daoVotingResource);
        } else {
            BeanUtils.copyProperties(daoVotingResource, d);
            daoVotingResourceRepository.save(d);
        }
    }

    public void removeDaoVotingResource(DaoVotingResourceId daoVotingResourceId) {
        DaoVotingResource d = daoVotingResourceRepository.findById(daoVotingResourceId).orElse(null);
        if (d != null) {
            daoVotingResourceRepository.delete(d);
        }
    }
}
