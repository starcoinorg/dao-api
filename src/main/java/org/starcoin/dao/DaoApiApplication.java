package org.starcoin.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.starcoin.dao.service.StarcoinHandleEventService;
import org.starcoin.dao.service.StarcoinEventFilter;
import org.starcoin.dao.subscribe.StarcoinEventSubscribeHandler;

@EnableWebMvc
@SpringBootApplication
@EnableScheduling
public class DaoApiApplication {

    private static final Logger LOG = LoggerFactory.getLogger(DaoApiApplication.class);

    @Value("${starcoin.seeds}")
    private String[] starcoinSeeds;

    @Value("${starcoin.network}")
    private String starcoinNetwork;

    @Autowired
    private StarcoinHandleEventService starcoinHandleEventService;

    @Autowired
    private StarcoinEventFilter starcoinEventFilter;

    public static void main(String[] args) {
        SpringApplication.run(DaoApiApplication.class, args);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void runStarcoinEventSubscribeHandler() {
        LOG.info("EXECUTING : StarcoinEventSubscribeHandler");
        //LOG.info("es url is " + esUrl);
        for (String seed : starcoinSeeds) {
            Thread handlerThread = new Thread(new StarcoinEventSubscribeHandler(seed,
                    starcoinHandleEventService, starcoinEventFilter));
            handlerThread.start();
        }
    }
}
