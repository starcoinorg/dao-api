package org.starcoin.bean;

import java.util.List;

public class Option<E> {
    private List<E> vec;

    public List<E> getVec() {
        return vec;
    }

    public void setVec(List<E> vec) {
        this.vec = vec;
    }

    @Override
    public String toString() {
        return "Option{" +
                "vec=" + vec +
                '}';
    }
}
