package org.starcoin.dao.vo;

public class CastVoteRequest {
    private String signedMessageHex;

    public String getSignedMessageHex() {
        return signedMessageHex;
    }

    public void setSignedMessageHex(String signedMessageHex) {
        this.signedMessageHex = signedMessageHex;
    }

    @Override
    public String toString() {
        return "CastVoteRequest{" +
                "signedMessageHex='" + signedMessageHex + '\'' +
                '}';
    }
}
