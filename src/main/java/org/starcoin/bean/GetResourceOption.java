package org.starcoin.bean;

public class GetResourceOption {
    private boolean decode;

    public boolean isDecode() {
        return decode;
    }

    public void setDecode(boolean decode) {
        this.decode = decode;
    }

    @Override
    public String toString() {
        return "GetResourceOption{" +
                "decode=" + decode +
                '}';
    }
}
