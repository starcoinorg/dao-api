package org.starcoin.dao.service;

import org.starcoin.bean.Event;

public interface HandleEventService {
    void handleEvent(Event e);
}
