package org.starcoin.dao.service;

import java.util.List;

public interface StarcoinEventFilter {
    List<String> getAddresses();

    List<String> getEventTypeTags();
}
