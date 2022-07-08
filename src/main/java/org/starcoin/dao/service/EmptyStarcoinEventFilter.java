package org.starcoin.dao.service;

import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
public class EmptyStarcoinEventFilter implements StarcoinEventFilter {
    @Override
    public List<String> getAddresses() {
        return Collections.emptyList();
    }

    @Override
    public List<String> getEventTypeTags() {
        return Collections.emptyList();
    }
}
