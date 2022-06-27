package org.starcoin.bcs.sab;

import com.novi.bcs.BcsDeserializer;
import com.novi.serde.DeserializationError;

public abstract class AbstractContainerTraverser implements BCSTraverser {

    private final String containerType;
    private final String elementType;

    public AbstractContainerTraverser(String containerType, String elementType) {
        this.containerType = containerType;
        this.elementType = elementType;
    }

    public String getContainerType() {
        return containerType;
    }

    public String getElementType() {
        return elementType;
    }

    @Override
    public boolean traverse(BcsDeserializer deserializer, ContentHandler contentHandler) throws DeserializationError {
        boolean b = false;
        contentHandler.startContainer(containerType, elementType);
        for (BCSTraverser t : elementTraversers(deserializer, contentHandler)) {
            b = t.traverse(deserializer, contentHandler);
            if (b) {
                break;
            }
        }
        return b || contentHandler.endContainer(containerType, elementType);
    }

    @Override
    public abstract Iterable<BCSTraverser> elementTraversers(BcsDeserializer deserializer, ContentHandler contentHandler) throws DeserializationError;

    @Override
    public String type() {
        return getContainerType();
    }

}
