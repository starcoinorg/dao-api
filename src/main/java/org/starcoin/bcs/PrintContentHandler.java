package org.starcoin.bcs;

import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Collections;

public class PrintContentHandler implements ContentHandler {
    private int indent = 0;

    public static Object toObjectForPrint(Object val) {
        return val instanceof byte[] ? Arrays.toString((byte[]) val) : val;
    }

    @Override
    public boolean value(Object val) {
        System.out.println(indentSpaces() + "Got value: " + toObjectForPrint(val));
        return false;
    }

    @Override
    public void startContainer(String containerType, String elementType) {
        System.out.println(indentSpaces() + "Start container: " + containerType + ", element type: " + elementType);
        this.indent++;
    }

    @NotNull
    private String indentSpaces() {
        return String.join("", Collections.nCopies(this.indent * 2, " "));
    }

    @Override
    public boolean endContainer(String containerType, String elementType) {
        this.indent--;
        System.out.println(indentSpaces() + "End container: " + containerType + ", element type: " + elementType);
        return false;
    }

//    @Override
//    public void startOptional(String valueType) {
//        System.out.println(indentSpaces() + "Start optional, value type: "+valueType);
//        this.indent++;
//    }
//
//    @Override
//    public void endOptional(String valueType) {
//        this.indent--;
//        System.out.println(indentSpaces() + "End optional, value type: " + valueType);
//    }


    @Override
    public void variantIndex(int value) {
        System.out.println(indentSpaces() + "Variant Index: " + value);
    }
}
