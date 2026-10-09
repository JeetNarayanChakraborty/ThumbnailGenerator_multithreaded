package com.mThreading.GenarateThumbnail.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


@Configuration
public class ExecutorConfig 
{
	@Bean
    public ThreadPoolExecutor executorService()
	{
        int cpuCores = Runtime.getRuntime().availableProcessors();

        int corePoolSize = cpuCores;
        int maxPoolSize = cpuCores * 2; // Allow a small buffer for minimal overhead
        long keepAliveTime = 30;
        TimeUnit unit = TimeUnit.SECONDS;

        BlockingQueue<Runnable> workQueue = new LinkedBlockingQueue<>(1000); // Holds upto 1000 tasks if all threads are busy

        return new ThreadPoolExecutor(
            corePoolSize,
            maxPoolSize,
            keepAliveTime,
            unit,
            workQueue,

            new ThreadPoolExecutor.CallerRunsPolicy() //Tells the pool what to do if the 1000-queue fills up
        );
    }
}
