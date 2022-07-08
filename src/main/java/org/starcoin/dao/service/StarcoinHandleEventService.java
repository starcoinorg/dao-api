package org.starcoin.dao.service;

import org.starcoin.bean.Event;

public interface StarcoinHandleEventService {
    void handleEvent(Event e);
}
