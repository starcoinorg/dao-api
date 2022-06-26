package org.starcoin.bcs;

import com.novi.bcs.BcsDeserializer;
import com.novi.serde.DeserializationError;

import java.util.Iterator;

public class VectorTraverser extends AbstractContainerTraverser {

    private final BCSTraverser elementTraverser;

    public VectorTraverser(BCSTraverser elementTraverser) {
        super(TYPE_VECTOR, elementTraverser.type());
        this.elementTraverser = elementTraverser;
    }

    public static Iterable<BCSTraverser> nElementTraversersIterable(final int n, final BCSTraverser elementTraverser) {
        return () -> new Iterator<BCSTraverser>() {
            private int counter = n;

            @Override
            public boolean hasNext() {
                return counter > 0;
            }

            @Override
            public BCSTraverser next() {
                counter--;
                return elementTraverser;
            }
        };
    }

    public BCSTraverser getElementTraverser() {
        return elementTraverser;
    }

    @Override
    public Iterable<BCSTraverser> elementTraversers(BcsDeserializer deserializer, ContentHandler contentHandler) throws DeserializationError {
        long length = deserializer.deserialize_len();
        //        BCSTraverser[] elementTraversers = new BCSTraverser[(int) length];
        //        Arrays.fill(elementTraversers, 0, elementTraversers.length, elementTraverser);
        //        return Arrays.asList(elementTraversers);
        return nElementTraversersIterable((int) length, elementTraverser);
    }

    @Override
    public String toString() {
        return "VectorTraverser{" +
                "elementTraverser=" + elementTraverser +
                '}';
    }
}
