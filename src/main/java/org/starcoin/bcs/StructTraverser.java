package org.starcoin.bcs;

import com.novi.bcs.BcsDeserializer;

import java.util.List;

public class StructTraverser extends AbstractContainerTraverser {
    private final List<BCSTraverser> elementTraversers;

    public StructTraverser(List<BCSTraverser> elementTraversers) {
        super(TYPE_STRUCT, null);
        this.elementTraversers = elementTraversers;
    }

    @Override
    public Iterable<BCSTraverser> elementTraversers(BcsDeserializer deserializer, ContentHandler contentHandler) {
        return this.elementTraversers;
    }

    @Override
    public String toString() {
        return "StructTraverser{" +
                "elementTraversers=" + elementTraversers +
                '}';
    }
}
