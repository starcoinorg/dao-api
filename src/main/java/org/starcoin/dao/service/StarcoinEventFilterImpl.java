package org.starcoin.dao.service;

import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
public class StarcoinEventFilterImpl implements StarcoinEventFilter {
    @Override
    public List<String> getAddresses() {
        //return Collections.singletonList("0x598b8cbfd4536ecbe88aa1cfaffa7a62");
        return Collections.emptyList();
    }

    @Override
    public List<String> getEventTypeTags() {
        //return Collections.singletonList("0x598b8cbfd4536ecbe88aa1cfaffa7a62::TokenSwap::AddLiquidityEvent");
        return Collections.emptyList();
    }
}
