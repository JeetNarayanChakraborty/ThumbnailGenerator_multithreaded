package com.mThreading.GenarateThumbnail.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


@Configuration
public class ExecutorConfig 
{
	@Bean
    public ExecutorService executorService() 
	{
        int corePoolSize = 4;
        int maxPoolSize = 8;
        long keepAliveTime = 10;
        TimeUnit unit = TimeUnit.SECONDS;
        BlockingQueue<Runnable> workQueue = new LinkedBlockingQueue<>(100);

        return new ThreadPoolExecutor(
            corePoolSize,
            maxPoolSize,
            keepAliveTime,
            unit,
            workQueue
        );
    }
}
