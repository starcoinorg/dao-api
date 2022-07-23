package org.starcoin.dao.utils;

import com.novi.serde.DeserializationError;
import org.starcoin.bcs.sab.BCSPath;
import org.starcoin.bcs.sab.ParseException;
import org.starcoin.bean.RpcStateWithProof;
import org.starcoin.utils.HexUtils;

import java.math.BigInteger;

public class MiscUtils {
    private MiscUtils() {
    }

    public static BigInteger getResourceFieldAsBigInteger(JsonRpcClient jsonRpcClient, String stateRoot,
                                                          String accountAddress, String resourceStructTag, String bcsPath) {
        RpcStateWithProof stateWithProof = jsonRpcClient.getStateWithProofByRoot(
                JsonRpcClient.getResourceStateAccessPath(accountAddress,
                        resourceStructTag),
                stateRoot);
        BigInteger bi = BigInteger.ZERO;
        byte[] state = HexUtils.hexToByteArray(stateWithProof.getState());
        if (state.length > 0) {
            Object amount;
            try {
                amount = BCSPath.select(state, bcsPath);
            } catch (DeserializationError e) {
                throw new IllegalArgumentException("BCSPath.select DeserializationError.", e);
            } catch (ParseException e) {
                throw new IllegalArgumentException("BCSPath.select ParseException.", e);
            }
            bi = (BigInteger) amount;
        }
        return bi;
    }
}
