package org.starcoin.bcs;

import com.novi.bcs.BcsDeserializer;
import com.novi.serde.DeserializationError;

public class UnsupportedOperationTraverser extends AbstractTraverser {
    public static UnsupportedOperationTraverser INSTANCE = new UnsupportedOperationTraverser();

    @Override
    protected Object doTraverse(BcsDeserializer deserializer) throws DeserializationError {
        throw new UnsupportedOperationException();
    }

    @Override
    public String type() {
        return TYPE_UNSUPPORTED_OPERATION;
    }

    @Override
    public String toString() {
        return "UnsupportedOperationTraverser{}";
    }
}
