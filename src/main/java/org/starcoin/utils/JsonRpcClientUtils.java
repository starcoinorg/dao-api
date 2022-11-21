package org.starcoin.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.novi.serde.DeserializationError;
import org.starcoin.bean.*;
import org.starcoin.jsonrpc.JSONRPC2Request;
import org.starcoin.jsonrpc.JSONRPC2Response;
import org.starcoin.jsonrpc.client.JSONRPC2Session;
import org.starcoin.jsonrpc.client.JSONRPC2SessionException;
import org.starcoin.types.AccountResource;

import java.math.BigInteger;
import java.util.*;
import java.util.function.Function;

/**
 * Starcoin JSON RPC client utils.
 */
public class JsonRpcClientUtils {
    public static final String STC_ACCOUNT_BALANCE_RESOURCE_STRUCT_TAG = "0x00000000000000000000000000000001::Account::Balance<0x00000000000000000000000000000001::STC::STC>";

    private JsonRpcClientUtils() {
    }

    public static ChainInfo getChainInfo(JSONRPC2Session session) {
        String method = "chain.info";
        return callForObject(session, method, Collections.emptyList(), ChainInfo.class);
    }

    public static RpcStateWithProof getStateWithProofByRoot(JSONRPC2Session jsonRpcSession, String accessPath, String stateRoot) {
        String method = "state.get_with_proof_by_root";
        return callForObject(jsonRpcSession, method, Arrays.asList(accessPath, stateRoot), RpcStateWithProof.class);
    }

    public static BlockHeader getBlockHeader(JSONRPC2Session session, Long number) {
        String method = "chain.get_block_by_number";
        Block block = callForObject(session, method, Collections.singletonList(number), Block.class);
        return block.getHeader();
    }

    public static Event[] getEvents(JSONRPC2Session jsonRpcSession, Map<String, Object> eventFilter) {
        String method = "chain.get_events";
        Class<Event[]> objectClass = Event[].class;
        Event[] events = callForObject(jsonRpcSession, method, Collections.singletonList(eventFilter), objectClass);
        return events == null ? new Event[0] : events;
    }

    public static <T> Resource<T> getResource(JSONRPC2Session jsonRpcSession, String accountAddress, String key, Class<T> resourceType) {
        String method = "state.get_resource";
        GetResourceOption getResourceOption = new GetResourceOption();
        getResourceOption.setDecode(true);
        Resource<T> resource = callForObject(jsonRpcSession, method, Arrays.asList(accountAddress, key, getResourceOption),
                getObjectMapper().getTypeFactory().constructParametricType(Resource.class, resourceType));
        return resource;
    }

    public static BigInteger getTokenScalingFactor(JSONRPC2Session jsonRpcSession, String token) {
        // Move code:
        // public fun scaling_factor<TokenType: store>(): u128
        //
        List<BigInteger> resultFields = contractCallV2(jsonRpcSession, "0x1::Token::scaling_factor",
                Arrays.asList(token), Collections.emptyList(), new TypeReference<List<BigInteger>>() {
                });
        return resultFields.get(0);
    }

    /**
     * Get state of struct '0x01::Account::Account' in the accountAddress.
     *
     * @param jsonrpcSession JSON RPC session
     * @param accountAddress account address
     * @return state of struct '0x01::Account::Account'
     * @throws DeserializationError
     */
    public static AccountResource getAccountResource(JSONRPC2Session jsonrpcSession, String accountAddress) throws DeserializationError {
        String resourceType = "0x00000000000000000000000000000001::Account::Account";
        Map<String, Object> rst = callForObject(jsonrpcSession, "state.get_resource",
                Arrays.asList(accountAddress, resourceType), new TypeReference<Map<String, Object>>() {
                });
        String rawHex = (String) rst.get("raw");
        return AccountResource.bcsDeserialize(HexUtils.decode(rawHex));
    }

    /**
     * Call JSON RPC method 'contract.call_v2'.
     *
     * @param functionId function ID.
     * @param typeArgs   type arguments.
     * @param args       arguments.
     * @return JSON RPC response body.
     */
    public static String contractCallV2(JSONRPC2Session jsonRpcSession, String functionId, List<String> typeArgs, List<Object> args) {
        String method = "contract.call_v2";
        List<Object> params = getContractCallV2Params(functionId, typeArgs, args);
        return callForObject(jsonRpcSession, method, params, String.class);
    }

    /**
     * Call JSON RPC method 'contract.call_v2', and get result of type 'T'.
     *
     * @param functionId function ID.
     * @param typeArgs   type arguments.
     * @param args       arguments.
     * @return method result of type 'T'.
     */
    public static <T> T contractCallV2(JSONRPC2Session jsonRpcSession, String functionId, List<String> typeArgs, List<Object> args, TypeReference<T> typeReference) {
        String method = "contract.call_v2";
        List<Object> params = getContractCallV2Params(functionId, typeArgs, args);
        return callForObject(jsonRpcSession, method, params, typeReference);
    }

    private static List<Object> getContractCallV2Params(String functionId, List<String> typeArgs, List<Object> args) {
        Map<String, Object> singleParamMap = new HashMap<>();
        singleParamMap.put("function_id", functionId);
        singleParamMap.put("type_args", typeArgs);
        singleParamMap.put("args", args);
        List<Object> params = Collections.singletonList(singleParamMap);
        return params;
    }

    /**
     * Call RPC method and get result of type 'T'.
     *
     * @param jsonRpcSession JSON RPC session
     * @param method         method name.
     * @param params         method params.
     * @param typeRef        result type reference
     * @param <T>            result type
     * @return method result
     */
    public static <T> T callForObject(JSONRPC2Session jsonRpcSession, String method, List<Object> params, TypeReference<T> typeRef) {
        return callForObject(jsonRpcSession, method, params,
                (result) -> getObjectMapper().convertValue(result, typeRef));
    }

    private static <T> T callForObject(JSONRPC2Session jsonRpcSession, String method, List<Object> params, JavaType javaType) {
        return callForObject(jsonRpcSession, method, params,
                (result) -> getObjectMapper().convertValue(result, javaType));
    }

    /**
     * Call RPC method and get result of type 'T'.
     **/
    @SuppressWarnings("unchecked")
    public static <T> T callForObject(JSONRPC2Session jsonRpcSession, String method, List<Object> params, Class<T> objectClass) {
        return callForObject(jsonRpcSession, method, params, (result) -> {
            if (objectClass.isAssignableFrom(result.getClass())) {
                return (T) result;
            }
            if (objectClass.isAssignableFrom(String.class)) {
                return (T) result.toString();
            }
            return getObjectMapper().convertValue(result, objectClass);
        });
    }

    private static <T> T callForObject(JSONRPC2Session jsonRpcSession,
                                       String method, List<Object> params,
                                       Function<Object, T> resultConverter) {
        JSONRPC2Request request = new JSONRPC2Request(method, params, System.currentTimeMillis());
        JSONRPC2Response response;
        try {
            response = jsonRpcSession.send(request);
        } catch (JSONRPC2SessionException e) {
            //LOG.error("JSON rpc error.", e);
            throw new RuntimeException(e);
        }
        if (response.indicatesSuccess()) {
            Object result = response.getResult();
            if (result != null) {
                try {
                    return resultConverter.apply(result);
                } catch (RuntimeException e) {
                    String msg = "resultConverter.apply(result) error. Response: " + response;
                    //LOG.error(msg);
                    throw new RuntimeException(msg, e);
                }
            } else {
                return null;
            }
        } else {
            String msg = "JSON RPC error. response error: '" + response.getError() + "'. Request: " + request
                    + ". Response: " + response;
            //LOG.error(msg);
            throw new RuntimeException(msg);
        }
    }

    private static ObjectMapper getObjectMapper() {
        return new ObjectMapper();
    }

}
