package org.starcoin.bcs.sab;

import com.novi.bcs.BcsDeserializer;
import com.novi.serde.DeserializationError;

public class U128Traverser extends AbstractTraverser {
    public static U128Traverser INSTANCE = new U128Traverser();

    public U128Traverser() {
    }

    @Override
    protected Object doTraverse(BcsDeserializer deserializer) throws DeserializationError {
        return deserializer.deserialize_u128();
    }

    @Override
    public String type() {
        return TYPE_U128;
    }

    @Override
    public String toString() {
        return "U128Traverser{" +
                '}';
    }
}
