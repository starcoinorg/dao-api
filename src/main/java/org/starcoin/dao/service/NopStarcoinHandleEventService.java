package org.starcoin.dao.service;

import org.starcoin.bean.Event;

//@Component
public class NopStarcoinHandleEventService implements StarcoinHandleEventService {
    @Override
    public void handleEvent(Event e) {
        // do nothing
    }

}
