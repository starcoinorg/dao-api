package org.starcoin.bean;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Checkpoints {
    @JsonProperty("checkpoints")
    private Ring<Checkpoint> checkpoints;
    @JsonProperty("index")
    private Long index;//       : u64,
    @JsonProperty("last_number")
    private Long lastNumber;// : u64,

    public Ring<Checkpoint> getCheckpoints() {
        return checkpoints;
    }

    public void setCheckpoints(Ring<Checkpoint> checkpoints) {
        this.checkpoints = checkpoints;
    }

    public Long getIndex() {
        return index;
    }

    public void setIndex(Long index) {
        this.index = index;
    }

    public Long getLastNumber() {
        return lastNumber;
    }

    public void setLastNumber(Long lastNumber) {
        this.lastNumber = lastNumber;
    }

    @Override
    public String toString() {
        return "Checkpoints{" +
                "checkpoints=" + checkpoints +
                ", index=" + index +
                ", lastNumber=" + lastNumber +
                '}';
    }
}
