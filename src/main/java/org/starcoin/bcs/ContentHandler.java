package org.starcoin.bcs;

public interface ContentHandler {
    /**
     * Receive notification of value.
     * Only sent by non-container traversers.
     *
     * @param val Value
     * @return Returns true if it is necessary to break the traversal to the next element.
     */
    boolean value(Object val);

    /**
     * Receive notification of the beginning of a container.
     *
     * @param containerType
     * @param elementType   Element type, if the element type can be provided.
     */
    void startContainer(String containerType, String elementType);

    /**
     * Receive notification of the end of a container.
     *
     * @param containerType
     * @param elementType   Element type.
     * @return Returns true if it is necessary to break the traversal to the next element.
     */
    boolean endContainer(String containerType, String elementType);

//    void startOptional(String valueType);
//
//    void endOptional(String valueType);

}
