package org.starcoin.utils;

import com.novi.serde.DeserializationError;
import com.novi.serde.SerializationError;
import org.starcoin.jsonrpc.client.JSONRPC2Session;
import org.starcoin.types.*;

import java.math.BigInteger;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.starcoin.utils.JsonRpcClientUtils.getAccountResource;
import static org.starcoin.utils.SignatureUtils.createRawUserTransaction;

public class StarcoinClient {
    private static final long DEFAULT_MAX_GAS_AMOUNT = 10000000L;
    private static final long DEFAULT_TRANSACTION_EXPIRATION_SECONDS = 2 * 60 * 60;

    private final String jsonRpcUrl;
    private final int chainId;

    private final JSONRPC2Session jsonRpcSession;

    public StarcoinClient(String jsonRpcUrl, int chainId) throws MalformedURLException {
        this.chainId = chainId;
        this.jsonRpcUrl = jsonRpcUrl;
        this.jsonRpcSession = new JSONRPC2Session(new URL(this.jsonRpcUrl));
    }

    public Object submitTransaction(AccountAddress sender, Ed25519PrivateKey privateKey,
                                    TransactionPayload payload) throws SerializationError, DeserializationError {
        RawUserTransaction rawUserTransaction = buildRawUserTransaction(sender, payload);
        return submitHexTransaction(privateKey, rawUserTransaction);
    }

    public RawUserTransaction buildRawUserTransaction(AccountAddress sender,
                                                      TransactionPayload payload) throws DeserializationError {
        long seqNumber = getAccountSequenceNumber(sender);
        return buildRawUserTransaction(sender, seqNumber, payload);
    }

    private long getAccountSequenceNumber(AccountAddress sender) throws DeserializationError {
        AccountResource accountResource = getAccountResource(jsonRpcSession, sender.toString());
        return accountResource.sequence_number;
    }

    public RawUserTransaction buildRawUserTransaction(AccountAddress sender, long seqNumber,
                                                      TransactionPayload payload) {
        return createRawUserTransaction(chainId, sender, BigInteger.valueOf(seqNumber), payload,
                BigInteger.valueOf(getGasUnitPrice()),
                BigInteger.valueOf(DEFAULT_MAX_GAS_AMOUNT),
                getExpirationTimestampSecs());
    }

    private long getGasUnitPrice() {
        return JsonRpcClientUtils.callForObject(jsonRpcSession,
                "txpool.gas_price", Collections.emptyList(), Long.class);
    }

    private long getExpirationTimestampSecs() {
        Map m = JsonRpcClientUtils.callForObject(jsonRpcSession, "node.info", Collections.emptyList(), Map.class);
        long nowSeconds = Long.parseLong(m.get("now_seconds").toString());
        return nowSeconds + DEFAULT_TRANSACTION_EXPIRATION_SECONDS;
    }

    public Object submitHexTransaction(Ed25519PrivateKey privateKey,
                                       RawUserTransaction rawUserTransaction) throws SerializationError {
        SignedUserTransaction signedUserTransaction = SignatureUtils.signTxn(privateKey,
                rawUserTransaction);
        return submitHexTransaction(signedUserTransaction);
    }

    public Object submitHexTransaction(SignedUserTransaction signedUserTransaction) throws SerializationError {
        List<Object> params = Collections.singletonList(HexUtils.encode(signedUserTransaction.bcsSerialize()));
        return JsonRpcClientUtils.callForObject(jsonRpcSession,
                "txpool.submit_hex_transaction", params, Object.class);
    }

    public String getJsonRpcUrl() {
        return jsonRpcUrl;
    }

    public int getChainId() {
        return chainId;
    }

    public JSONRPC2Session getJsonRpcSession() {
        return jsonRpcSession;
    }
}
