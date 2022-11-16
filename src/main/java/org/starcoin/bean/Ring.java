package org.starcoin.bean;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class Ring<E> {
    @JsonProperty("data")
    private List<Option<E>> data;//: vector<Option::Option<Element>>,
    @JsonProperty("insertion_index")
    private Long insertionIndex;// : u64,
    @JsonProperty("external_index")
    private Long externalIndex;//  : u64

    public List<Option<E>> getData() {
        return data;
    }

    public void setData(List<Option<E>> data) {
        this.data = data;
    }

    public Long getInsertionIndex() {
        return insertionIndex;
    }

    public void setInsertionIndex(Long insertionIndex) {
        this.insertionIndex = insertionIndex;
    }

    public Long getExternalIndex() {
        return externalIndex;
    }

    public void setExternalIndex(Long externalIndex) {
        this.externalIndex = externalIndex;
    }

    @Override
    public String toString() {
        return "Ring{" +
                "data=" + data +
                ", insertionIndex=" + insertionIndex +
                ", externalIndex=" + externalIndex +
                '}';
    }
}
