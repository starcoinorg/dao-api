package org.starcoin.utils;

import com.novi.bcs.BcsSerializer;
import com.novi.serde.Bytes;
import com.novi.serde.Int128;
import com.novi.serde.SerializationError;
import com.novi.serde.Unsigned;
import org.starcoin.types.*;

import java.math.BigInteger;
import java.util.Collections;
import java.util.List;

public class TransactionUtils {

    private TransactionUtils() {
    }

    public static TransactionPayload encodeEmptyArgsScriptFunction(String moduleId, String functionName) {
        return buildScriptFunctionTransactionPayload(moduleId, functionName, Collections.emptyList());
    }

    public static TransactionPayload encodeU8ScriptFunction(String moduleId, String functionName, Byte b) {
        return buildScriptFunctionTransactionPayload(moduleId, functionName, Collections.singletonList(
                encode_u8_argument(b)
        ));
    }

    public static TransactionPayload encodeU64ScriptFunction(String moduleId, String functionName, Long l) {
        return buildScriptFunctionTransactionPayload(moduleId, functionName, Collections.singletonList(
                encode_u64_argument(l)
        ));
    }

    public static TransactionPayload encodeU128ScriptFunction(String moduleId, String functionName, BigInteger i) {
        return buildScriptFunctionTransactionPayload(moduleId, functionName, Collections.singletonList(
                encode_u128_argument(i)
        ));
    }

    public static TransactionPayload encodeBoolScriptFunction(String moduleId, String functionName, Boolean b) {
        return buildScriptFunctionTransactionPayload(moduleId, functionName, Collections.singletonList(
                encode_bool_argument(b)
        ));
    }

    public static TransactionPayload encodeU64AndU8ScriptFunction(String moduleId, String functionName, Long u64, Byte u8) {
        return buildScriptFunctionTransactionPayload(moduleId, functionName, java.util.Arrays.asList(
                encode_u64_argument(u64),
                encode_u8_argument(u8)
        ));
    }

    public static TransactionPayload encodeU8VectorScriptFunction(String moduleId, String functionName, Bytes bytes) {
        List<Bytes> args = java.util.Arrays.asList(
                encode_u8vector_argument(bytes)
        );
        return buildScriptFunctionTransactionPayload(moduleId, functionName, args);
    }

    public static TransactionPayload encodeU8VectorAndU64AndBoolScriptFunction(String moduleId, String functionName, Bytes bytes, Long u64, Boolean b) {
        List<Bytes> args = java.util.Arrays.asList(
                encode_u8vector_argument(bytes),
                encode_u64_argument(u64),
                encode_bool_argument(b)
        );
        return buildScriptFunctionTransactionPayload(moduleId, functionName, args);
    }


    public static TransactionPayload encodeU8AndU64ScriptFunction(String moduleId, String functionName, Byte u8, Long u64) {
        List<Bytes> args = java.util.Arrays.asList(
                encode_u8_argument(u8),
                encode_u64_argument(u64)
        );
        return buildScriptFunctionTransactionPayload(moduleId, functionName, args);
    }

    public static TransactionPayload encodeU8VectorAndU64ScriptFunction(String moduleId, String functionName, Bytes bytes, Long u64) {
        return buildScriptFunctionTransactionPayload(
                moduleId,
                functionName,
                java.util.Arrays.asList(
                        encode_u8vector_argument(bytes),
                        encode_u64_argument(u64)
                ));
    }

    private static TransactionPayload buildScriptFunctionTransactionPayload(String moduleId, String functionName, List<Bytes> args) {
        ScriptFunction.Builder script_function_builder = new ScriptFunction.Builder();
        script_function_builder.ty_args = Collections.emptyList();
        script_function_builder.args = args;
        script_function_builder.function = new Identifier(functionName);
        script_function_builder.module = parseModuleId(moduleId);
        TransactionPayload.ScriptFunction.Builder builder = new TransactionPayload.ScriptFunction.Builder();
        builder.value = script_function_builder.build();
        return builder.build();
    }


    private static ModuleId parseModuleId(String m) {
        String[] ss = m.split("::");
        if (ss.length < 2) {
            throw new IllegalArgumentException("Illegal module id.: " + m);
        }
        return new ModuleId(AccountAddressUtils.create(ss[0]), new Identifier(ss[1]));
    }


    private static Bytes encode_bool_argument(Boolean arg) {
        try {
            BcsSerializer s = new BcsSerializer();
            s.serialize_bool(arg);
            return Bytes.valueOf(s.get_bytes());

        } catch (SerializationError e) {
            throw new IllegalArgumentException("Unable to serialize argument of type bool");
        }
    }

    private static Bytes encode_u64_argument(@Unsigned Long arg) {
        try {
            BcsSerializer s = new BcsSerializer();
            s.serialize_u64(arg);
            return Bytes.valueOf(s.get_bytes());

        } catch (SerializationError e) {
            throw new IllegalArgumentException("Unable to serialize argument of type u64");
        }
    }

    private static Bytes encode_u8_argument(@Unsigned Byte arg) {
        try {
            BcsSerializer s = new BcsSerializer();
            s.serialize_u8(arg);
            return Bytes.valueOf(s.get_bytes());

        } catch (SerializationError e) {
            throw new IllegalArgumentException("Unable to serialize argument of type u8");
        }
    }

    private static Bytes encode_u128_argument(@Unsigned @Int128 BigInteger arg) {
        try {
            BcsSerializer s = new BcsSerializer();
            s.serialize_u128(arg);
            return Bytes.valueOf(s.get_bytes());
        } catch (SerializationError e) {
            throw new IllegalArgumentException("Unable to serialize argument of type u128");
        }
    }

    private static Bytes encode_address_argument(AccountAddress arg) {
        try {
            return Bytes.valueOf(arg.bcsSerialize());
        } catch (SerializationError e) {
            throw new IllegalArgumentException("Unable to serialize argument of type address");
        }
    }

    private static Bytes encode_u8vector_argument(Bytes arg) {
        try {
            BcsSerializer s = new BcsSerializer();
            s.serialize_bytes(arg);
            return Bytes.valueOf(s.get_bytes());
        } catch (SerializationError e) {
            throw new IllegalArgumentException("Unable to serialize argument of type u8vector");
        }
    }

}
