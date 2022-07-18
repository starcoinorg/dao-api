package org.starcoin.dao.service;

import com.novi.serde.DeserializationError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.starcoin.bean.Event;
import org.starcoin.dao.types.event.DaoCreatedEvent;
import org.starcoin.dao.types.event.MemberJoinEvent;
import org.starcoin.dao.types.event.ProposalCreatedEvent;
import org.starcoin.dao.types.event.VotedEvent;

import java.util.HashMap;
import java.util.Map;

@Service
public class StarcoinHandleEventServiceImpl extends AbstractStarcoinHandleEventService {

    private final Map<String, EventHandler<?>> eventHandlerMap;

    public StarcoinHandleEventServiceImpl(@Autowired StarcoinEventFilterImpl starcoinEventFilter) {

        Map<String, EventHandler<?>> eventHandlerMap = new HashMap<>();

        eventHandlerMap.put(starcoinEventFilter.getDaoCreatedEventTypeTag(), new EventHandler<DaoCreatedEvent>() {
            @Override
            public DaoCreatedEvent bcsDeserializeEventData(byte[] eventData) throws DeserializationError {
                return DaoCreatedEvent.bcsDeserialize(eventData);
            }

            @Override
            public void handle(Event event, DaoCreatedEvent eventData) {
                //todo
            }
        });

        eventHandlerMap.put(starcoinEventFilter.getMemberJoinEventTypeTag(), new EventHandler<MemberJoinEvent>() {
            @Override
            public MemberJoinEvent bcsDeserializeEventData(byte[] eventData) throws DeserializationError {
                return MemberJoinEvent.bcsDeserialize(eventData);
            }

            @Override
            public void handle(Event event, MemberJoinEvent eventData) {
                //todo
            }
        });

        eventHandlerMap.put(starcoinEventFilter.getProposalCreatedEventTypeTag(), new EventHandler<ProposalCreatedEvent>() {
            @Override
            public ProposalCreatedEvent bcsDeserializeEventData(byte[] eventData) throws DeserializationError {
                return ProposalCreatedEvent.bcsDeserialize(eventData);
            }

            @Override
            public void handle(Event event, ProposalCreatedEvent eventData) {
                //todo
            }
        });

        eventHandlerMap.put(starcoinEventFilter.getVotedEventTypeTag(), new EventHandler<VotedEvent>() {
            @Override
            public VotedEvent bcsDeserializeEventData(byte[] eventData) throws DeserializationError {
                return VotedEvent.bcsDeserialize(eventData);
            }

            @Override
            public void handle(Event event, VotedEvent eventData) {
                //todo
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
