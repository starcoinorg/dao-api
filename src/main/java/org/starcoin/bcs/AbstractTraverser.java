package org.starcoin.bcs;

import com.novi.bcs.BcsDeserializer;
import com.novi.serde.DeserializationError;

import java.util.Collections;

public abstract class AbstractTraverser implements BCSTraverser {
    @Override
    public boolean traverse(BcsDeserializer deserializer, ContentHandler contentHandler) throws DeserializationError {
        Object val = doTraverse(deserializer);
        return contentHandler.value(val);
    }

    @Override
    public Iterable<BCSTraverser> elementTraversers(BcsDeserializer deserializer) {
        return Collections.emptyList();
    }

    protected abstract Object doTraverse(BcsDeserializer deserializer) throws DeserializationError;

    @Override
    public abstract String type();


}
