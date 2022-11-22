package org.starcoin.dao.taskservice;

import com.novi.serde.Bytes;
import com.novi.serde.DeserializationError;
import com.novi.serde.SerializationError;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.starcoin.bean.BlockHeader;
import org.starcoin.bean.Checkpoints;
import org.starcoin.bean.Resource;
import org.starcoin.dao.utils.JsonRpcClient;
import org.starcoin.types.AccountAddress;
import org.starcoin.types.Ed25519PrivateKey;
import org.starcoin.types.TransactionPayload;
import org.starcoin.utils.ConvertUtils;
import org.starcoin.utils.HexUtils;
import org.starcoin.utils.StarcoinClient;
import org.starcoin.utils.TransactionUtils;

@Service
public class CheckpointUpdateTaskService {
    private static final Logger LOG = LoggerFactory.getLogger(CheckpointUpdateTaskService.class);

    @Autowired
    private StarcoinClient starcoinClient;

    @Autowired
    private JsonRpcClient jsonRpcClient;

    @Value("${starcoin.checkpoint-update.account-address}")
    private String accountAddress;

    @Value("${starcoin.checkpoint-update.private-key}")
    private String privateKey;

    @Scheduled(fixedDelayString = "${starcoin.checkpoint-update.fixed-delay}")
    public void task() {
        try {
            callCheckpointEntry();
            callUpdateStateRootEntry();
        } catch (SerializationError | DeserializationError e) {
            LOG.error("callUpdateStateRootEntry error", e);
        }
    }

    private void callCheckpointEntry() throws SerializationError, DeserializationError {
        TransactionPayload transactionPayload = TransactionUtils.encodeEmptyArgsScriptFunction(
                "0x00000000000000000000000000000001::Block",
                "checkpoint_entry");
        submitTx(transactionPayload);
    }

    private void callUpdateStateRootEntry() throws SerializationError, DeserializationError {
        Resource<Checkpoints> checkpointsResource
                = jsonRpcClient.getResource(
                "0x00000000000000000000000000000001",
                "0x1::Block::Checkpoints",
                Checkpoints.class);
        Long blockNumber = checkpointsResource.getJson().getLastNumber();
        System.out.println("LastNumber: " + blockNumber);
        BlockHeader blockHeader = jsonRpcClient.getBlockHeader(blockNumber);

        TransactionPayload transactionPayload = TransactionUtils.encodeU8VectorScriptFunction(
                "0x00000000000000000000000000000001::Block",
                "update_state_root_entry",
                Bytes.valueOf(ConvertUtils.toTypesBlockHeader(blockHeader).bcsSerialize()));
        submitTx(transactionPayload);
    }

    private void submitTx(TransactionPayload transactionPayload) throws SerializationError, DeserializationError {
        Object result = starcoinClient.submitTransaction(
                AccountAddress.valueOf(HexUtils.hexToByteArray(accountAddress)),
                new Ed25519PrivateKey(Bytes.valueOf(HexUtils.decode(privateKey))),
                transactionPayload);
        //System.out.println(result);
        LOG.info("CheckpointUpdateTaskService.submitTx() result: {}", result);
    }
}
