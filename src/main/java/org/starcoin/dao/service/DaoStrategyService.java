package org.starcoin.dao.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.starcoin.dao.data.model.*;
import org.starcoin.dao.data.repo.DaoStrategyLockedVotingPowerRepository;
import org.starcoin.dao.data.repo.DaoStrategyRepository;
import org.starcoin.dao.utils.JsonRpcClient;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

import static org.starcoin.dao.utils.MiscUtils.getResourceFieldAsBigInteger;

@Service
public class DaoStrategyService {
    @Autowired
    private DaoStrategyRepository daoStrategyRepository;

    @Autowired
    private DaoStrategyLockedVotingPowerRepository daoStrategyLockedVotingPowerRepository;

    @Autowired
    private JsonRpcClient jsonRpcClient;

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

    public void createStcDaoStrategy(String daoId, Integer sequenceId,
                                     BigDecimal votingTurnoutThresholdRate, String daoStrategyType) {
        DaoStrategyId daoStrategyId = new DaoStrategyId(daoId, Strategy.STRATEGY_ID_VOTING_RESOURCES);
        DaoStrategy daoStrategy = new DaoStrategy();
        daoStrategy.setDaoStrategyId(daoStrategyId);
        daoStrategy.setSequenceId(sequenceId);
        daoStrategy.setVotingPowerName("STC");
        daoStrategy.setVotingPowerDecimals(0);
        daoStrategy.setVotingTurnoutThresholdRate(votingTurnoutThresholdRate);
        daoStrategy.setDaoStrategyType(daoStrategyType);
        daoStrategy.setVotingPowerSupplyAccountAddress("0x00000000000000000000000000000001");
        daoStrategy.setVotingPowerSupplyResourceStructTag("0x00000000000000000000000000000001::Token::TokenInfo<0x00000000000000000000000000000001::STC::STC>");
        daoStrategy.setVotingPowerSupplyBcsPath("{u128,u128,_,_}[0]");
        if (daoStrategyRepository.findById(daoStrategyId).orElse(null) != null) {
            return;
        }
        daoStrategyRepository.save(daoStrategy);

        DaoStrategyLockedVotingPowerId lockedVotingPowerId = new DaoStrategyLockedVotingPowerId(daoStrategyId, 0);
        if (daoStrategyLockedVotingPowerRepository.findById(lockedVotingPowerId).orElse(null) != null) {
            return;
        }
        DaoStrategyLockedVotingPower lockedVotingPower = new DaoStrategyLockedVotingPower();
        lockedVotingPower.setDaoStrategyLockedVotingPowerId(lockedVotingPowerId);
        lockedVotingPower.setAccountAddress("0x00000000000000000000000000000001");
        lockedVotingPower.setResourceStructTag("0x00000000000000000000000000000001::Treasury::Treasury<0x00000000000000000000000000000001::STC::STC>");
        lockedVotingPower.setVotingPowerBcsPath("{{u128},_,_}[0][0]");
        daoStrategyLockedVotingPowerRepository.save(lockedVotingPower);
    }

    public BigInteger getVotingPowerSupply(String stateRoot, String daoId, String strategyId) {
        DaoStrategyId daoStrategyId = new DaoStrategyId(daoId, strategyId);
        DaoStrategy daoStrategy = daoStrategyRepository.findById(daoStrategyId).orElse(null);
        if (daoStrategy == null) {
            return BigInteger.ZERO;
        }
        return getVotingPowerSupply(stateRoot, daoStrategy);
    }

    public BigInteger getVotingTurnoutThreshold(String stateRoot, String daoId, String strategyId) {
        return getCirculatingVotingPowerAndVotingTurnoutThreshold(stateRoot, daoId, strategyId).getItem1();
    }

    public Pair<BigInteger, BigInteger> getCirculatingVotingPowerAndVotingTurnoutThreshold(String stateRoot, String daoId, String strategyId) {
        DaoStrategyId daoStrategyId = new DaoStrategyId(daoId, strategyId);
        DaoStrategy daoStrategy = daoStrategyRepository.findById(daoStrategyId).orElse(null);
        if (daoStrategy == null) {
            return new Pair<>(BigInteger.ZERO, BigInteger.ZERO);
        }
        BigInteger votingPowerSupply = getVotingPowerSupply(stateRoot, daoStrategy);
        BigInteger circulatingVotingPowerSupply = votingPowerSupply.subtract(
                getLockedVotingPowerSupply(stateRoot, daoId, strategyId));
        BigInteger votingTurnoutThreshold = new BigDecimal(circulatingVotingPowerSupply)
                .multiply(daoStrategy.getVotingTurnoutThresholdRate()).toBigInteger();
        return new Pair<>(circulatingVotingPowerSupply, votingTurnoutThreshold);
    }

    private BigInteger getVotingPowerSupply(String stateRoot, DaoStrategy daoStrategy) {
        return getResourceFieldAsBigInteger(jsonRpcClient, stateRoot,
                daoStrategy.getVotingPowerSupplyAccountAddress(), daoStrategy.getVotingPowerSupplyResourceStructTag(),
                daoStrategy.getVotingPowerSupplyBcsPath());
    }

    public BigInteger getCirculatingVotingPowerSupply(String stateRoot, String daoId, String strategyId) {
        BigInteger votingPowerSupply = getVotingPowerSupply(stateRoot, daoId, strategyId);
        return votingPowerSupply.subtract(getLockedVotingPowerSupply(stateRoot, daoId, strategyId));
    }

    public BigInteger getLockedVotingPowerSupply(String stateRoot, String daoId, String strategyId) {
        BigInteger lockedAmount = BigInteger.ZERO;
        for (DaoStrategyLockedVotingPower locked : findLockedVotingPowersByDaoStrategyId(daoId, strategyId)) {
            lockedAmount = lockedAmount.add(getResourceFieldAsBigInteger(jsonRpcClient, stateRoot,
                    locked.getAccountAddress(), locked.getResourceStructTag(), locked.getVotingPowerBcsPath()));
        }
        return lockedAmount;
    }

    public List<DaoStrategyLockedVotingPower> findLockedVotingPowersByDaoStrategyId(String daoId, String strategyId) {
        DaoStrategyId daoStrategyId = new DaoStrategyId(daoId, strategyId);
        DaoStrategyLockedVotingPower lockedVotingPower = new DaoStrategyLockedVotingPower();
        lockedVotingPower.setDaoStrategyLockedVotingPowerId(new DaoStrategyLockedVotingPowerId(daoStrategyId, null));
        return daoStrategyLockedVotingPowerRepository.findAll(Example.of(lockedVotingPower));
    }

}
