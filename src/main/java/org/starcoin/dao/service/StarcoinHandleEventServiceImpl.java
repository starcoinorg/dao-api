package org.starcoin.dao.service;

import com.novi.serde.DeserializationError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.starcoin.bean.Event;
import org.starcoin.dao.data.model.DaoMember;
import org.starcoin.dao.types.event.*;

import java.util.HashMap;
import java.util.Map;

@Service
public class StarcoinHandleEventServiceImpl extends AbstractStarcoinHandleEventService {

    private final Map<String, EventHandler<?>> eventHandlerMap;


    public StarcoinHandleEventServiceImpl(@Autowired StarcoinEventFilterImpl starcoinEventFilter,
                                          @Autowired DaoService daoService,
                                          @Autowired DaoMemberService daoMemberService,
                                          @Autowired ProposalService proposalService,
                                          @Autowired AccountVoteService accountVoteService) {

        Map<String, EventHandler<?>> eventHandlerMap = new HashMap<>();

        eventHandlerMap.put(starcoinEventFilter.getDaoCreatedEventTypeTag(), new EventHandler<DaoCreatedEvent>() {
            @Override
            public DaoCreatedEvent bcsDeserializeEventData(byte[] eventData) throws DeserializationError {
                return DaoCreatedEvent.bcsDeserialize(eventData);
            }

            @Override
            public void handle(Event event, DaoCreatedEvent eventData) {
                daoService.addIfNotExists(EventUtils.toDao(eventData));
            }
        });

        eventHandlerMap.put(starcoinEventFilter.getMemberJoinEventTypeTag(), new EventHandler<MemberJoinEvent>() {
            @Override
            public MemberJoinEvent bcsDeserializeEventData(byte[] eventData) throws DeserializationError {
                return MemberJoinEvent.bcsDeserialize(eventData);
            }

            @Override
            public void handle(Event event, MemberJoinEvent eventData) {
                DaoMember daoMember = EventUtils.toDaoMember(eventData);
                daoMember.setJoinedAtBlockHeight(Long.parseLong(event.getBlockNumber()));
                //todo add or update Dao Member
                daoMemberService.addIfNotExists(daoMember);
            }
        });

        eventHandlerMap.put(starcoinEventFilter.getProposalCreatedEventTypeTag(), new EventHandler<ProposalCreatedEventV2>() {
            @Override
            public ProposalCreatedEventV2 bcsDeserializeEventData(byte[] eventData) throws DeserializationError {
                return ProposalCreatedEventV2.bcsDeserialize(eventData);
            }

            @Override
            public void handle(Event event, ProposalCreatedEventV2 eventData) {
                proposalService.addIfNotExists(EventUtils.toProposal(eventData));
            }
        });

        eventHandlerMap.put(starcoinEventFilter.getVotedEventTypeTag(), new EventHandler<VotedEvent>() {
            @Override
            public VotedEvent bcsDeserializeEventData(byte[] eventData) throws DeserializationError {
                return VotedEvent.bcsDeserialize(eventData);
            }

            @Override
            public void handle(Event event, VotedEvent eventData) {
                accountVoteService.addIfNotExists(EventUtils.toAccountVote(eventData));
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
