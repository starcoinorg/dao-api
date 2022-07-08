package org.starcoin.dao.subscribe;

import io.reactivex.Flowable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.starcoin.bean.Event;
import org.starcoin.bean.EventNotification;
import org.starcoin.dao.service.HandleEventService;
import org.starcoin.dao.service.StarcoinEventFilter;
import org.web3j.protocol.websocket.WebSocketService;

import java.net.ConnectException;
import java.util.List;

public class StarcoinEventSubscribeHandler implements Runnable {

    private static final Logger LOG = LoggerFactory.getLogger(StarcoinEventSubscribeHandler.class);

    private final String webSocketSeed;

    //private final String network;

    private final HandleEventService handleEventService;

    private final StarcoinEventFilter starcoinEventFilter;

    public StarcoinEventSubscribeHandler(String seed, //String network,
                                         HandleEventService handleEventService,
                                         StarcoinEventFilter starcoinEventFilter) {
        this.webSocketSeed = seed;
        //this.network = network;
        this.handleEventService = handleEventService;
        this.starcoinEventFilter = starcoinEventFilter;
    }

    private String getWebSocketSeed() {
        String wsUrl = webSocketSeed;
        String wsPrefix1 = "ws://";
        String wsPrefix2 = "wss://";
        if (!wsUrl.startsWith(wsPrefix1) && !wsUrl.startsWith(wsPrefix2)) {
            wsUrl = wsPrefix1 + wsUrl;
        }
        if (wsUrl.lastIndexOf(":") == wsUrl.indexOf(":") && wsUrl.startsWith(wsPrefix1)) {
            wsUrl = wsUrl + ":9870";
        }
        LOG.debug("Get WebSocket URL: " + wsUrl);
        return wsUrl;
    }

    @Override
    public void run() {
        try {
            WebSocketService service = new WebSocketService(getWebSocketSeed(), true);
            service.connect();
            LOG.info("WebSocket connected. " + this.getWebSocketSeed());
            StarcoinEventSubscriber subscriber = new StarcoinEventSubscriber(service, starcoinEventFilter);
            List<Flowable<EventNotification>> evtNotificationFlowableList = subscriber.eventNotificationFlowableList();
            evtNotificationFlowableList.forEach(evtNotifications -> {
                for (EventNotification notification : evtNotifications.blockingIterable()) {
                    if (notification.getParams() == null || notification.getParams().getResult() == null) {
                        continue;
                    }
                    Event event = notification.getParams().getResult();
                    if (LOG.isDebugEnabled()) LOG.debug("Received event: " + event);
                    handleEventService.handleEvent(event);
                }
            });
        } catch (ConnectException e) {
            LOG.info("WebSocketService connection exception", e);
        }
    }
}
