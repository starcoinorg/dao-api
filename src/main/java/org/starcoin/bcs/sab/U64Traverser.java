package org.starcoin.bcs.sab;

import com.novi.bcs.BcsDeserializer;
import com.novi.serde.DeserializationError;

public class U64Traverser extends AbstractTraverser {
    public static U64Traverser INSTANCE = new U64Traverser();

    public U64Traverser() {
    }

    @Override
    protected Object doTraverse(BcsDeserializer deserializer) throws DeserializationError {
        return deserializer.deserialize_u64();
    }

    @Override
    public String type() {
        return TYPE_U64;
    }

    @Override
    public String toString() {
        return "U64Traverser{" +
                '}';
    }
}
