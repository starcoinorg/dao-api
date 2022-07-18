package org.starcoin.dao.service;

import com.novi.serde.DeserializationError;
import org.starcoin.bean.Event;
import org.starcoin.utils.HexUtils;

public abstract class AbstractStarcoinHandleEventService implements StarcoinHandleEventService {
    @Override
    public void handleEvent(Event e) {
        EventHandler<?> eventHandler = getEventHandlerByEventTypeTag(e.getTypeTag());
        Object eventDataObj = null;
        try {
            eventDataObj = eventHandler.bcsDeserializeEventData(HexUtils.hexToByteArray(e.getData()));
        } catch (DeserializationError ex) {
            throw new RuntimeException(ex);
        }
        eventHandler.handleEvent(e, eventDataObj);
    }

    protected abstract <T> EventHandler<T> getEventHandlerByEventTypeTag(String eventTypeTag);

    public interface EventHandler<T> {
        T bcsDeserializeEventData(byte[] eventData) throws DeserializationError;

        void handle(Event event, T eventData);

        default void handleEvent(Event e, Object eventDataObj) {
            handle(e, (T) eventDataObj);
        }
    }
}
