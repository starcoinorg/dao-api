package org.starcoin.dao.utils;

import com.novi.serde.DeserializationError;
import org.starcoin.bean.*;
import org.starcoin.dao.data.model.Pair;
import org.starcoin.jsonrpc.client.JSONRPC2Session;
import org.starcoin.types.AccountResource;
import org.starcoin.utils.JsonRpcClientUtils;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

/**
 * Starcoin JSON RPC client.
 */
public class JsonRpcClient {
    public static final String STATE_ACCESS_PATH_TYPE_RESOURCE = "1";

    private final JSONRPC2Session jsonRpcSession;

    private final String jsonRpcUrl;

    public JsonRpcClient(String jsonRpcUrl) throws MalformedURLException {
        this.jsonRpcUrl = jsonRpcUrl;
        this.jsonRpcSession = new JSONRPC2Session(new URL(this.jsonRpcUrl));

    }

    public static String getResourceStateAccessPath(String accountAddress, String resourceStructTag) {
        return accountAddress + "/" + STATE_ACCESS_PATH_TYPE_RESOURCE + "/" + resourceStructTag;
    }

    public String getJsonRpcUrl() {
        return jsonRpcUrl;
    }

    public Event[] getEvents(Map<String, Object> eventFilter) {
        return JsonRpcClientUtils.getEvents(this.jsonRpcSession, eventFilter);
    }

    public <T> Resource<T> getResource(String accountAddress, String key, Class<T> resourceType) {
        return JsonRpcClientUtils.getResource(this.jsonRpcSession, accountAddress, key, resourceType);
    }

    public BlockHeader getBlockHeader(Long number) {
        return JsonRpcClientUtils.getBlockHeader(this.jsonRpcSession, number);
    }

    public RpcStateWithProof getStateWithProofByRoot(String accessPath, String stateRoot) {
        return JsonRpcClientUtils.getStateWithProofByRoot(this.jsonRpcSession, accessPath, stateRoot);
    }

    public AccountResource getAccountResource(String accountAddress) throws DeserializationError {
        return JsonRpcClientUtils.getAccountResource(this.jsonRpcSession, accountAddress);
    }

    public Pair<Long, String> getCurrentChainHeightStateRoot() {
        ChainInfo chainInfo = JsonRpcClientUtils.getChainInfo(this.jsonRpcSession);
        Long h = chainInfo.getHeader().getHeight();
        String sr = chainInfo.getHeader().getStateRoot();
        return new Pair<>(h, sr);
    }
}