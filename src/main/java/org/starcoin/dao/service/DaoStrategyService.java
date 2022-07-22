package org.starcoin.dao.service;

import com.novi.serde.DeserializationError;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.starcoin.bcs.sab.BCSPath;
import org.starcoin.bcs.sab.ParseException;
import org.starcoin.bean.RpcStateWithProof;
import org.starcoin.dao.api.utils.JsonRpcClient;
import org.starcoin.dao.data.model.DaoStrategy;
import org.starcoin.dao.data.model.DaoStrategyId;
import org.starcoin.dao.data.model.Strategy;
import org.starcoin.dao.data.repo.DaoStrategyLockedVotingPowerRepository;
import org.starcoin.dao.data.repo.DaoStrategyRepository;
import org.starcoin.utils.HexUtils;

import java.math.BigDecimal;
import java.math.BigInteger;

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
    }

    public BigInteger getVotingPowerSupply(String stateRoot, String daoId, String strategyId) {
        DaoStrategyId daoStrategyId = new DaoStrategyId(daoId, strategyId);
        DaoStrategy daoStrategy = daoStrategyRepository.findById(daoStrategyId).orElse(null);
        if (daoStrategy == null) {
            return BigInteger.ZERO;
        }
        RpcStateWithProof stateWithProof = jsonRpcClient.getStateWithProofByRoot(
                JsonRpcClient.getResourceStateAccessPath(daoStrategy.getVotingPowerSupplyAccountAddress(),
                        daoStrategy.getVotingPowerSupplyResourceStructTag()),
                stateRoot);
        BigInteger detailVotingPower = BigInteger.ZERO;
        byte[] state = HexUtils.hexToByteArray(stateWithProof.getState());
        if (state.length > 0) {
            Object amount;
            try {
                amount = BCSPath.select(state, daoStrategy.getVotingPowerSupplyBcsPath());
            } catch (DeserializationError e) {
                throw new IllegalStateException("BCSPath.select DeserializationError.", e);
            } catch (ParseException e) {
                throw new IllegalStateException("BCSPath.select ParseException.", e);
            }
            detailVotingPower = (BigInteger) amount;
        }
        return detailVotingPower;
    }
}
