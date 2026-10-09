package com.mThreading.GenarateThumbnail.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.util.concurrent.ThreadPoolExecutor;



@Component
public class BackgroundService
{
    private static final Logger log = LoggerFactory.getLogger(BackgroundService.class);

    @Autowired
    private APICallCounterService apiCallCounterService;

    @Autowired
    private ThreadPoolExecutor executor;

    @Scheduled(fixedRate = 10000)
    public void monitorAndAdjustPool()
    {
        int recentLoad = apiCallCounterService.getApiCallCount(); // Read the load first
        log.info("API load in current 10s window: {}. Active Threads: {}", recentLoad, executor.getActiveCount());

        if(recentLoad > 100) // Adjust the pool sizes
        {
            executor.setMaximumPoolSize(8);
            executor.setCorePoolSize(6);
            log.info("Scaled UP pool size. Core: 6, Max: 8");
        }

        else if(recentLoad < 20)
        {
            executor.setCorePoolSize(2);
            executor.setMaximumPoolSize(4);
            log.info("Scaled DOWN pool size. Core: 2, Max: 4");
        }

        // Reset the counter at the end of the 10-second check
        apiCallCounterService.resetCallCount();
    }
}
