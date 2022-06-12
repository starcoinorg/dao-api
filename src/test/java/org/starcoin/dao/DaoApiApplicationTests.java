package org.starcoin.dao;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.starcoin.dao.data.model.DaoVotingResource;
import org.starcoin.dao.data.model.DaoVotingResourceId;
import org.starcoin.dao.data.model.Proposal;
import org.starcoin.dao.data.repo.DaoVotingResourceRepository;
import org.starcoin.dao.data.repo.ProposalRepository;

@SpringBootTest
class DaoApiApplicationTests {

    @Autowired
    private ProposalRepository proposalRepository;

    @Autowired
    private DaoVotingResourceRepository daoVotingResourceRepository;

    @Test
    void contextLoads() {
    }

    @Test
    void testProposalRepository() {
        for (Proposal p : proposalRepository.findAll()) {
            System.out.println(p);
        }
    }

    @Test
    void testDaoVotingResourceRepository() {
        // test daoVotingResourceRepository save method.
        DaoVotingResource daoVotingResource = new DaoVotingResource();
        daoVotingResource.setDaoVotingResourceId(new DaoVotingResourceId(
                "test_dao_id",
                "1"
        ));
        daoVotingResource.setResourceStructTag("0x8c109349c6bd91411d6bc962e080c4a3::TokenSwapFarmBoost::UserInfo<" +
                "0x00000000000000000000000000000001::STC::STC, " +
                "0x8c109349c6bd91411d6bc962e080c4a3::STAR::STAR" +
                ">");
        daoVotingResource.setVotingPowerBcsPath("");
        daoVotingResourceRepository.saveAndFlush(daoVotingResource);

        for (DaoVotingResource r : daoVotingResourceRepository.findAll()) {
            System.out.println(r);
        }
    }
}
