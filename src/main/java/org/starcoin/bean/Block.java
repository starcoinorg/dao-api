package org.starcoin.bean;

import java.util.List;
import java.util.Map;

public class Block {
    private BlockHeader header;//`json:"header"`
    private Map<String, Object> body;//`json:"body"`
    private List<BlockHeader> uncles;//`json:"uncles"`

    private String raw;

    public BlockHeader getHeader() {
        return header;
    }

    public void setHeader(BlockHeader header) {
        this.header = header;
    }

    public Map<String, Object> getBody() {
        return body;
    }

    public void setBody(Map<String, Object> body) {
        this.body = body;
    }

    public List<BlockHeader> getUncles() {
        return uncles;
    }

    public void setUncles(List<BlockHeader> uncles) {
        this.uncles = uncles;
    }

    public String getRaw() {
        return raw;
    }

    public void setRaw(String raw) {
        this.raw = raw;
    }

    @Override
    public String toString() {
        return "Block{" +
                "header=" + header +
                ", body=" + body +
                ", uncles=" + uncles +
                ", raw='" + raw + '\'' +
                '}';
    }
}
