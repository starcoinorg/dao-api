package org.starcoin.dao.vo;

import org.starcoin.dao.data.model.Dao;

import java.util.List;

public class DaoVO extends Dao {
    private List<DaoStrategy> daoStrategies;

    public List<DaoStrategy> getDaoStrategies() {
        return daoStrategies;
    }

    public void setDaoStrategies(List<DaoStrategy> daoStrategies) {
        this.daoStrategies = daoStrategies;
    }

    public static class DaoStrategy {
        private String strategyId;

        private String description;

        private Integer sequenceId;

        public String getStrategyId() {
            return strategyId;
        }

        public void setStrategyId(String strategyId) {
            this.strategyId = strategyId;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public Integer getSequenceId() {
            return sequenceId;
        }

        public void setSequenceId(Integer sequenceId) {
            this.sequenceId = sequenceId;
        }

        private List<DaoVotingResourceVO> daoVotingResources;

        public List<DaoVotingResourceVO> getDaoVotingResources() {
            return daoVotingResources;
        }

        public void setDaoVotingResources(List<DaoVotingResourceVO> daoVotingResources) {
            this.daoVotingResources = daoVotingResources;
        }
    }
}
