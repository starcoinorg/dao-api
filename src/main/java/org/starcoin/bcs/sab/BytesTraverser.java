package org.starcoin.bcs.sab;

import com.novi.bcs.BcsDeserializer;
import com.novi.serde.Bytes;
import com.novi.serde.DeserializationError;

public class BytesTraverser extends AbstractTraverser {
    public static BytesTraverser INSTANCE = new BytesTraverser();

    public BytesTraverser() {
    }

    @Override
    protected Object doTraverse(BcsDeserializer deserializer) throws DeserializationError {
        Bytes bytes = deserializer.deserialize_bytes();
        return bytes.content();
    }

    @Override
    public String type() {
        return TYPE_BYTES;
    }

    @Override
    public String toString() {
        return "BytesTraverser{" +
                '}';
    }

}
