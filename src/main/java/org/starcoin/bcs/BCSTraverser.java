package org.starcoin.bcs;

import com.novi.bcs.BcsDeserializer;
import com.novi.serde.DeserializationError;

public interface BCSTraverser {
    String TYPE_VECTOR = "vector";
    String TYPE_STRUCT = "struct";
    String TYPE_OPTIONAL = "option";
    String TYPE_BYTES = "bytes";
    String TYPE_VECTOR_U8 = "vector<u8>";
    String TYPE_U128 = "u128";
    String TYPE_U64 = "u64";
    String TYPE_U8 = "u8";
    String TYPE_BOOL = "bool";
    String TYPE_ACCOUNT_ADDRESS = "address";

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
    Iterable<BCSTraverser> elementTraversers(BcsDeserializer deserializer) throws DeserializationError;

    String type();
}
