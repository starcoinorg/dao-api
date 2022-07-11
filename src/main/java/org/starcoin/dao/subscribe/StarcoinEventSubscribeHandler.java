package org.starcoin.dao.subscribe;

import io.reactivex.Flowable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.starcoin.bean.Event;
import org.starcoin.bean.EventNotification;
import org.starcoin.dao.service.StarcoinEventFilter;
import org.starcoin.dao.service.StarcoinHandleEventService;
import org.web3j.protocol.websocket.WebSocketService;

import java.net.ConnectException;

public class StarcoinEventSubscribeHandler implements Runnable {

    private static final Logger LOG = LoggerFactory.getLogger(StarcoinEventSubscribeHandler.class);

    private final String webSocketSeed;

    private final StarcoinHandleEventService starcoinHandleEventService;

    private final StarcoinEventFilter starcoinEventFilter;

    public StarcoinEventSubscribeHandler(String seed,
                                         StarcoinHandleEventService starcoinHandleEventService,
                                         StarcoinEventFilter starcoinEventFilter) {
        this.webSocketSeed = seed;
        this.starcoinHandleEventService = starcoinHandleEventService;
        this.starcoinEventFilter = starcoinEventFilter;
    }

    private String getWebSocketServerUrl() {
        String wsUrl = webSocketSeed;
        String wsPrefix1 = "ws://";
        String wsPrefix2 = "wss://";
        if (!wsUrl.startsWith(wsPrefix1) && !wsUrl.startsWith(wsPrefix2)) {
            wsUrl = wsPrefix1 + wsUrl;
        }
        if (wsUrl.lastIndexOf(":") == wsUrl.indexOf(":") && wsUrl.startsWith(wsPrefix1)) {
            wsUrl = wsUrl + ":9870";
        }
        LOG.debug("Get WebSocket server URL: " + wsUrl);
        return wsUrl;
    }

    @Override
    public void run() {
        try {
            WebSocketService service = new WebSocketService(getWebSocketServerUrl(), true);
            service.connect();
            LOG.info("WebSocket connected. " + this.getWebSocketServerUrl());
            StarcoinEventSubscriber subscriber = new StarcoinEventSubscriber(service, starcoinEventFilter);
            Flowable<EventNotification> evtNotificationFlowable = subscriber.eventNotificationFlowable();
            for (EventNotification notification : evtNotificationFlowable.blockingIterable()) {
                if (notification.getParams() == null || notification.getParams().getResult() == null) {
                    continue;
                }
                Event event = notification.getParams().getResult();
                if (LOG.isDebugEnabled()) LOG.debug("Received event: " + event);
                starcoinHandleEventService.handleEvent(event);
            }
        } catch (ConnectException e) {
            LOG.info("WebSocketService connection exception", e);
        }
    }
}
