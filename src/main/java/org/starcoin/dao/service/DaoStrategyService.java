package org.starcoin.dao.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.starcoin.dao.data.model.DaoStrategy;
import org.starcoin.dao.data.model.DaoStrategyId;
import org.starcoin.dao.data.repo.DaoStrategyRepository;

@Service
public class DaoStrategyService {
    @Autowired
    private DaoStrategyRepository daoStrategyRepository;

    public DaoStrategy getPrimaryDaoStrategy(String daoId) {
        return daoStrategyRepository.findFirstByDaoStrategyId_DaoIdOrderBySequenceId(daoId);
    }

    public void addOrUpdateDaoStrategy(DaoStrategy daoStrategy) {
        DaoStrategyId daoStrategyId = daoStrategy.getDaoStrategyId();
        DaoStrategy d = daoStrategyRepository.findById(daoStrategyId).orElse(null);
        if (d == null) {
            daoStrategyRepository.save(daoStrategy);
        } else {
            BeanUtils.copyProperties(daoStrategy, d);
            daoStrategyRepository.save(d);
        }
    }
}
