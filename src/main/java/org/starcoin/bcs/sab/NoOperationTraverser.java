package org.starcoin.bcs.sab;

import com.novi.bcs.BcsDeserializer;
import com.novi.serde.DeserializationError;

public class NoOperationTraverser extends AbstractTraverser {
    public static NoOperationTraverser INSTANCE = new NoOperationTraverser();

    @Override
    protected Object doTraverse(BcsDeserializer deserializer) throws DeserializationError {
        return null;
    }

    @Override
    public String type() {
        return TYPE_NO_OPERATION;
    }

    @Override
    public String toString() {
        return "NoOperationTraverser{}";
    }
}
