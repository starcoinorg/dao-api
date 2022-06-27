package org.starcoin.bcs.sab;

import com.novi.bcs.BcsDeserializer;
import com.novi.serde.DeserializationError;

public interface BCSTraverser {
    String TYPE_VECTOR = "vector";
    String TYPE_STRUCT = "struct";
    String TYPE_OPTIONAL = "option";
    String TYPE_ENUM = "enum";
    String TYPE_BYTES = "bytes";
    String TYPE_VECTOR_U8 = "vector<u8>";
    String TYPE_STRING = "string";
    String TYPE_U128 = "u128";
    String TYPE_U64 = "u64";
    String TYPE_U8 = "u8";
    String TYPE_BOOL = "bool";
    String TYPE_ACCOUNT_ADDRESS = "address";
    String TYPE_NO_OPERATION = "_nop_";
    String TYPE_UNSUPPORTED_OPERATION = "_unsupported_";

    /**
     * Travers BCS serialized data.
     *
     * @param deserializer BCS deserializer.
     * @return Returns true if it is necessary to break the traversal to the next element.
     */
    boolean traverse(BcsDeserializer deserializer, ContentHandler contentHandler) throws DeserializationError;

    /**
     * Iterable of element traversers, if the current traverser is a container traverser.
     *
     * @return
     */
    Iterable<BCSTraverser> elementTraversers(BcsDeserializer deserializer, ContentHandler contentHandler) throws DeserializationError;

    String type();

    class Enum {
        public static final Enum EMPTY = new Enum();

        private Integer variantIndex;
        private Object value;

        public Enum() {
        }

        public Enum(int variantIndex) {
            this.variantIndex = variantIndex;
        }

        public Enum(int variantIndex, Object value) {
            this.variantIndex = variantIndex;
            this.value = value;
        }

        public Integer getVariantIndex() {
            return variantIndex;
        }

        public Object getValue() {
            return value;
        }

        @Override
        public String toString() {
            return "Enum{" +
                    "variantIndex=" + variantIndex +
                    ", value=" + value +
                    '}';
        }
    }
}
