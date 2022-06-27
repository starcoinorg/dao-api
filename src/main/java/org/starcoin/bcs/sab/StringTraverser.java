package org.starcoin.bcs.sab;

import com.novi.bcs.BcsDeserializer;
import com.novi.serde.DeserializationError;

public class StringTraverser extends AbstractTraverser {
    public static StringTraverser INSTANCE = new StringTraverser();

    public StringTraverser() {
    }

    @Override
    protected Object doTraverse(BcsDeserializer deserializer) throws DeserializationError {
        return deserializer.deserialize_str();
    }

    @Override
    public String type() {
        return TYPE_STRING;
    }

    @Override
    public String toString() {
        return "BytesTraverser{" +
                '}';
    }

}
