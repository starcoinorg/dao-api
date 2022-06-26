package org.starcoin.bcs;

import com.novi.bcs.BcsDeserializer;
import com.novi.serde.DeserializationError;

import java.util.Collections;

public class OptionalTraverser extends AbstractContainerTraverser {
    private BCSTraverser innerTraverser;

    /**
     * @param innerTraverser Inner BCS traverser.
     */
    public OptionalTraverser(BCSTraverser innerTraverser, String elementType) {
        super(TYPE_OPTIONAL, elementType);
        this.innerTraverser = innerTraverser;
    }


    public BCSTraverser getInnerTraverser() {
        return innerTraverser;
    }

    @Override
    public Iterable<BCSTraverser> elementTraversers(BcsDeserializer deserializer) throws DeserializationError {
        boolean tag = deserializer.deserialize_option_tag();
        if (!tag) {
            return Collections.emptyList();
        } else {
            return Collections.singletonList(innerTraverser);
        }
    }

//    @Override
//    public List<BCSTraverser> elementTraversers(BcsDeserializer deserializer) {
//        return Collections.emptyList();
//    }

//    @Override
//    public boolean traverse(BcsDeserializer deserializer, ContentHandler contentHandler) throws DeserializationError {
//        boolean b = false;
//        contentHandler.startOptional(innerTraverser.type());
//        boolean tag = deserializer.deserialize_option_tag();
//        if (tag) {
//            b = innerTraverser.traverse(deserializer, contentHandler);
//        }
//        contentHandler.endOptional(innerTraverser.type());
//        return b;
//    }

//    @Override
//    public String type() {
//        return TYPE_OPTIONAL;
//    }


    @Override
    public String toString() {
        return "OptionalTraverser{" +
                "innerTraverser=" + innerTraverser +
                '}';
    }
}
