package org.starcoin.dao.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.starcoin.dao.data.model.Dao;
import org.starcoin.dao.data.repo.DaoRepository;

@Service
public class DaoService {
    @Autowired
    private DaoRepository daoRepository;

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
}
