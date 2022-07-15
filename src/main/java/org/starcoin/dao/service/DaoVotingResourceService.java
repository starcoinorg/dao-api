package org.starcoin.dao.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.starcoin.dao.data.model.DaoVotingResource;
import org.starcoin.dao.data.model.DaoVotingResourceId;
import org.starcoin.dao.data.repo.DaoVotingResourceRepository;

@Service
public class DaoVotingResourceService {

    @Autowired
    private DaoVotingResourceRepository daoVotingResourceRepository;

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
}
