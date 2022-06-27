package org.starcoin.bcs.sab;

import com.novi.bcs.BcsDeserializer;
import com.novi.serde.DeserializationError;

import java.util.Collections;
import java.util.List;

public class EnumTraverser extends AbstractContainerTraverser {
    private final List<BCSTraverser> elementTraversers;

    public EnumTraverser(List<BCSTraverser> elementTraversers) {
        super(TYPE_ENUM, null);
        this.elementTraversers = elementTraversers;
    }

    @Override
    public Iterable<BCSTraverser> elementTraversers(BcsDeserializer deserializer, ContentHandler contentHandler) throws DeserializationError {
        int variantIndex = deserializer.deserialize_variant_index();
        contentHandler.variantIndex(variantIndex);
        return Collections.singletonList(this.elementTraversers.get(variantIndex));
    }

    @Override
    public String toString() {
        return "EnumTraverser{" +
                "elementTraversers=" + elementTraversers +
                '}';
    }
}
