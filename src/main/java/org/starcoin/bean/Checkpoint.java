package org.starcoin.bean;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Checkpoint {
    //number of the  block
    @JsonProperty("block_number")
    private Long blockNumber;//: u64,

    //Hash of the block
    @JsonProperty("block_hash")
    private String blockHash;//: vector<u8>,

    //State root of the block
    @JsonProperty("state_root")
    private Option<String> stateRoot;//: Option::Option<vector<u8>>,

    public Long getBlockNumber() {
        return blockNumber;
    }

    public void setBlockNumber(Long blockNumber) {
        this.blockNumber = blockNumber;
    }

    public String getBlockHash() {
        return blockHash;
    }

    public void setBlockHash(String blockHash) {
        this.blockHash = blockHash;
    }

    public Option<String> getStateRoot() {
        return stateRoot;
    }

    public void setStateRoot(Option<String> stateRoot) {
        this.stateRoot = stateRoot;
    }

    @Override
    public String toString() {
        return "Checkpoint{" +
                "blockNumber=" + blockNumber +
                ", blockHash='" + blockHash + '\'' +
                ", stateRoot=" + stateRoot +
                '}';
    }
}
