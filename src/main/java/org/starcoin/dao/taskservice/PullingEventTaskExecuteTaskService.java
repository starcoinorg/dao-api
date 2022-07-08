package org.starcoin.dao.taskservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.starcoin.bean.Event;
import org.starcoin.dao.api.utils.JsonRpcClient;
import org.starcoin.dao.data.model.PullingEventTask;
import org.starcoin.dao.data.repo.NodeHeartbeatRepository;
import org.starcoin.dao.service.StarcoinHandleEventService;
import org.starcoin.dao.service.NodeHeartbeatService;
import org.starcoin.dao.service.PullingEventTaskService;
import org.starcoin.dao.service.StarcoinEventFilter;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.starcoin.dao.data.model.PullingEventTask.PULLING_BLOCK_MAX_COUNT;

@Service
public class PullingEventTaskExecuteTaskService {
    private static final Logger LOG = LoggerFactory.getLogger(PullingEventTaskExecuteTaskService.class);

    private final PullingEventTaskService pullingEventTaskService;
    private final StarcoinHandleEventService starcoinHandleEventService;
    private final JsonRpcClient jsonRpcClient;
    private final NodeHeartbeatRepository nodeHeartbeatRepository;
    private final StarcoinEventFilter starcoinEventFilter;

    public PullingEventTaskExecuteTaskService(
            @Autowired JsonRpcClient jsonRpcClient,
            @Autowired PullingEventTaskService pullingEventTaskService,
            @Autowired StarcoinHandleEventService starcoinHandleEventService,
            @Autowired NodeHeartbeatRepository nodeHeartbeatRepository,
            @Autowired StarcoinEventFilter starcoinEventFilter) {
        this.jsonRpcClient = jsonRpcClient;
        this.pullingEventTaskService = pullingEventTaskService;
        this.starcoinHandleEventService = starcoinHandleEventService;
        this.nodeHeartbeatRepository = nodeHeartbeatRepository;
        this.starcoinEventFilter = starcoinEventFilter;
    }

    @Scheduled(fixedDelayString = "${starcoin.pulling-event-task-execute.fixed-delay}")
    public void task() {
        List<PullingEventTask> pullingEventTasks = pullingEventTaskService.getPullingEventTaskToProcess();
        if (pullingEventTasks == null || pullingEventTasks.isEmpty()) {
            return;
        }
        for (PullingEventTask t : pullingEventTasks) {
            executeTask(t);
        }
    }

    private void executeTask(PullingEventTask t) {
        BigInteger fromBlockNumber = t.getFromBlockNumber();
        // use a new individual nodeId to record heartbeats.
        NodeHeartbeatService nodeHeartbeatService = new NodeHeartbeatService(nodeHeartbeatRepository);
        nodeHeartbeatService.beat(t.getFromBlockNumber());
        while (fromBlockNumber.compareTo(t.getToBlockNumber()) <= 0) {
            BigInteger toBlockNumber = fromBlockNumber.add(BigInteger.valueOf(PULLING_BLOCK_MAX_COUNT));
            if (toBlockNumber.compareTo(t.getToBlockNumber()) > 0) {
                toBlockNumber = t.getToBlockNumber();
            }
            if (LOG.isDebugEnabled()) {
                LOG.debug("JSON RPC getting events... from block: " + fromBlockNumber + ", to block: " + toBlockNumber);
            }
            Map<String, Object> eventFilter = createEventFilterMap(fromBlockNumber, toBlockNumber);
            Event[] events = jsonRpcClient.getEvents(eventFilter);
            if (events == null) {
                break;
            }
            for (Event e : events) {
                if (LOG.isDebugEnabled()) {
                    LOG.debug("Processing a event: " + e);
                }
                starcoinHandleEventService.handleEvent(e);//note：Get address from eventKey?
            }
            fromBlockNumber = toBlockNumber.add(BigInteger.ONE);
        }
        pullingEventTaskService.updateStatusDone(t);
        nodeHeartbeatService.beat(t.getToBlockNumber());
    }


    private Map<String, Object> createEventFilterMap(BigInteger fromBlockNumber, BigInteger toBlockNumber) {
        Map<String, Object> eventFilter = new HashMap<>();
        // params: `from_block`, `to_block`, `event_keys`, `addrs`, `type_tags`, `limit`.
        eventFilter.put("addrs", starcoinEventFilter.getAddresses());
        eventFilter.put("type_tags", starcoinEventFilter.getEventTypeTags());
        //eventFilter.put("decode", true);
        eventFilter.put("from_block", fromBlockNumber);
        eventFilter.put("to_block", toBlockNumber);
        return eventFilter;
    }
}
