package org.starcoin.bcs;

public class VectorU8Traverser extends BytesTraverser {
    public static VectorU8Traverser INSTANCE = new VectorU8Traverser();

    public VectorU8Traverser() {
    }

    @Override
    public String type() {
        return TYPE_VECTOR_U8;
    }

    @Override
    public String toString() {
        return "VectorU8Traverser{" +
                '}';
    }
}
