package org.starcoin.dao.service;

import org.springframework.stereotype.Component;
import org.starcoin.bean.Event;

@Component
public class NopHandleEventService implements HandleEventService {
    @Override
    public void handleEvent(Event e) {
        // do nothing
    }

}
