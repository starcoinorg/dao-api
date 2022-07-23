package org.starcoin.bean;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

public class ChainInfo {

    @JsonProperty("block_info")
    private Map<String, Object> blockInfo;

    @JsonProperty("chain_id")
    private Integer chainId;

    @JsonProperty("genesis_hash")
    private String genesisHash;

    @JsonProperty("head")
    private BlockHeader header;

    public Map<String, Object> getBlockInfo() {
        return blockInfo;
    }

    public void setBlockInfo(Map<String, Object> blockInfo) {
        this.blockInfo = blockInfo;
    }

    public Integer getChainId() {
        return chainId;
    }

    public void setChainId(Integer chainId) {
        this.chainId = chainId;
    }

    public String getGenesisHash() {
        return genesisHash;
    }

    public void setGenesisHash(String genesisHash) {
        this.genesisHash = genesisHash;
    }

    public BlockHeader getHeader() {
        return header;
    }

    public void setHeader(BlockHeader header) {
        this.header = header;
    }
}
