package org.starcoin.dao.api.utils;

import com.novi.serde.DeserializationError;
import org.starcoin.bean.Event;
import org.starcoin.bean.RpcStateWithProof;
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
    public static final String ACCESS_PATH_DATA_TYPE_RESOURCE = "1";

    private final JSONRPC2Session jsonRpcSession;

    private final String jsonRpcUrl;

    public JsonRpcClient(String jsonRpcUrl) throws MalformedURLException {
        this.jsonRpcUrl = jsonRpcUrl;
        this.jsonRpcSession = new JSONRPC2Session(new URL(this.jsonRpcUrl));

    }

    public static String getAccountResourceAccessPath(String accountAddress, String resourceStructTag) {
        return accountAddress + "/" + ACCESS_PATH_DATA_TYPE_RESOURCE + "/" + resourceStructTag;
    }

    public String getJsonRpcUrl() {
        return jsonRpcUrl;
    }

    public Event[] getEvents(Map<String, Object> eventFilter) {
        return JsonRpcClientUtils.getEvents(this.jsonRpcSession, eventFilter);
    }

    public RpcStateWithProof getStateWithProofByRoot(String accessPath, String stateRoot) {
        return JsonRpcClientUtils.getStateWithProofByRoot(this.jsonRpcSession, accessPath, stateRoot);
    }

    public AccountResource getAccountResource(String accountAddress) throws DeserializationError {
        return JsonRpcClientUtils.getAccountResource(this.jsonRpcSession, accountAddress);
    }
}