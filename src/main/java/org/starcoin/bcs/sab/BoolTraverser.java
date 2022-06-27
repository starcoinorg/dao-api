package org.starcoin.bcs.sab;

import com.novi.bcs.BcsDeserializer;
import com.novi.serde.DeserializationError;

public class BoolTraverser extends AbstractTraverser {
    public static BoolTraverser INSTANCE = new BoolTraverser();

    public BoolTraverser() {
    }

    @Override
    protected Object doTraverse(BcsDeserializer deserializer) throws DeserializationError {
        return deserializer.deserialize_u128();
    }

    @Override
    public String type() {
        return TYPE_BOOL;
    }

    @Override
    public String toString() {
        return "BoolTraverser{" +
                '}';
    }
}
