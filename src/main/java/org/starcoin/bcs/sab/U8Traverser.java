package org.starcoin.bcs.sab;

import com.novi.bcs.BcsDeserializer;
import com.novi.serde.DeserializationError;

public class U8Traverser extends AbstractTraverser {
    public static U8Traverser INSTANCE = new U8Traverser();

    public U8Traverser() {
    }

    @Override
    protected Object doTraverse(BcsDeserializer deserializer) throws DeserializationError {
        return deserializer.deserialize_u64();
    }

    @Override
    public String type() {
        return TYPE_U8;
    }

    @Override
    public String toString() {
        return "U8Traverser{" +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof U8Traverser;
    }
}
