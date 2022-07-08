package org.starcoin.dao.subscribe;

import io.reactivex.Flowable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.starcoin.bean.EventNotification;
import org.starcoin.bean.Kind;
import org.starcoin.dao.service.StarcoinEventFilter;
import org.web3j.protocol.Web3jService;
import org.web3j.protocol.core.Request;
import org.web3j.protocol.core.methods.response.EthSubscribe;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class StarcoinEventSubscriber {

    private static final Logger LOG = LoggerFactory.getLogger(StarcoinEventSubscriber.class);

    private final StarcoinEventFilter starcoinEventFilter;
    private final Web3jService web3jService;

    public StarcoinEventSubscriber(Web3jService web3jService, StarcoinEventFilter starcoinEventFilter) {
        this.web3jService = web3jService;
        this.starcoinEventFilter = starcoinEventFilter;
    }

    private List<Map<String, Object>> createEventFilterList() {
        return starcoinEventFilter.getAddresses().stream().map(addr -> {
            Map<String, Object> eventFilter = new HashMap<>();
            eventFilter.put("addr", addr);
            eventFilter.put("type_tags", starcoinEventFilter.getEventTypeTags());
            //eventFilter.put("decode", true);
            return eventFilter;
        }).collect(Collectors.toList());
    }

    public List<Flowable<EventNotification>> eventNotificationFlowableList() {
        return createEventFilterList().stream().map(eventFilter ->
                web3jService.subscribe(
                        new Request<>(
                                "starcoin_subscribe",
                                Arrays.asList(Kind.Events, eventFilter),
                                web3jService,
                                EthSubscribe.class),
                        "starcoin_unsubscribe",
                        EventNotification.class)
        ).collect(Collectors.toList());
    }


}
