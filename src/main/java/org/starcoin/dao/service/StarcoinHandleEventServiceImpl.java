package org.starcoin.dao.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.starcoin.bean.Event;

import java.util.HashMap;
import java.util.Map;

@Service
public class StarcoinHandleEventServiceImpl extends AbstractStarcoinHandleEventService {

    private final Map<String, EventHandler<?>> eventHandlerMap;

    public StarcoinHandleEventServiceImpl(@Autowired StarcoinEventFilterImpl eventFilter) {
        Map<String, EventHandler<?>> eventHandlerMap = new HashMap<>();
        //todo add event handlers here
        eventHandlerMap.put("", new EventHandler<Object>() {
            @Override
            public Object bcsDeserializeEventData(byte[] eventData) {
                return null;
            }

            @Override
            public void handle(Event event, Object eventData) {
            }
        });
        this.eventHandlerMap = eventHandlerMap;
    }

    @SuppressWarnings("unchecked")
    @Override
    protected <T> EventHandler<T> getEventHandlerByEventTypeTag(String eventTypeTag) {
        if (eventHandlerMap.containsKey(eventTypeTag)) {
            return (EventHandler<T>) eventHandlerMap.get(eventTypeTag);
        } else {
            throw new IllegalArgumentException("eventTypeTag not found: " + eventTypeTag);
        }
    }
}
