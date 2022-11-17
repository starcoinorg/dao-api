package org.starcoin.utils;

import com.novi.serde.Bytes;
import com.novi.serde.SerializationError;
import org.starcoin.types.AccountAddress;
import org.starcoin.types.AuthenticationKey;
import org.starcoin.types.ChainId;
import org.starcoin.types.HashValue;

import java.util.Arrays;
import java.util.Optional;

public class ConvertUtils {
    private ConvertUtils() {
    }

    public static org.starcoin.types.BlockHeader toTypesBlockHeader(org.starcoin.bean.BlockHeader s) {
        if (s == null) {
            return null;
        }
        org.starcoin.types.BlockHeader t = new org.starcoin.types.BlockHeader();
        t.setParentHash(new HashValue(new Bytes(HexUtils.hexToByteArray(s.getParentHash()))));
        t.setTimestamp(s.getTimestamp());
        t.setNumber(s.getHeight());
        t.setTxnAccumulatorRoot(new HashValue(new Bytes(HexUtils.hexToByteArray(s.getTxnAccumulatorRoot()))));
        t.setBlockAccumulatorRoot(new HashValue(new Bytes(HexUtils.hexToByteArray(s.getBlockAccumulatorRoot()))));
        t.setStateRoot(new HashValue(new Bytes(HexUtils.hexToByteArray(s.getStateRoot()))));
        t.setGasUsed(s.getGasUsed());
        t.setDifficulty(to32Bytes(HexUtils.hexToByteArray(s.getDifficultyHexStr())));
        t.setBodyHash(new HashValue(new Bytes(HexUtils.hexToByteArray(s.getBodyHash()))));
        t.setAuthor(AccountAddress.valueOf(HexUtils.hexToByteArray(s.getAuthor())));
        t.setAuthenticationKey(s.getAuthorAuthKey() == null
                ? Optional.empty()
                : Optional.of(new AuthenticationKey(new Bytes(HexUtils.hexToByteArray(s.getAuthorAuthKey())))));
        t.setChainId(new ChainId((byte) s.getChainId()));
        t.setNonce((int) s.getNonce());
        t.setExtra(HexUtils.hexToByteArray(s.getExtra()));
        return t;
    }

    public static byte[] getBlockHash(org.starcoin.types.BlockHeader blockHeader) throws SerializationError {
        return HashUtils.hash(HashUtils.hashWithStarcoinPrefix("BlockHeader"), blockHeader.bcsSerialize());
    }

    private static byte[] to32Bytes(byte[] bytes) {
        if (bytes.length == 32) {
            return bytes;
        }
        if (bytes.length > 32) {
            return Arrays.copyOfRange(bytes, bytes.length - 32, bytes.length);
        }
        byte[] result = new byte[32];
        System.arraycopy(bytes, 0, result, 32 - bytes.length, bytes.length);
        return result;
    }
}
