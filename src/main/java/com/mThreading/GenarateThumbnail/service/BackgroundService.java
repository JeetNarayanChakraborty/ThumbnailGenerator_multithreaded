package com.mThreading.GenarateThumbnail.service;

import java.util.concurrent.ThreadPoolExecutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;


public class BackgroundService 
{
	@Autowired
	private APICallCounterService apiCallCounterService;
	
	@Autowired
	private ThreadPoolExecutor executor;
	
	
	@Scheduled(fixedRate = 10000)
	public void maintainTenSecondWindow()
	{
		apiCallCounterService.resetCallCount(); // Reset the API call count every 10 seconds to maintain a sliding window.
	}
	
	@Scheduled(fixedRate = 9999)
	public void adjustPoolSize() 
	{
        int recentLoad = apiCallCounterService.getApiCallCount(); // Get the recent API call count from the last 10 seconds

        if(recentLoad > 100) 
        {
            executor.setMaximumPoolSize(8);   // Dynamically adjust the maximum pool size based on the load
            executor.setCorePoolSize(6);
        }
        
        else if(recentLoad < 20) 
        {
            executor.setMaximumPoolSize(4);
            executor.setCorePoolSize(2);
        }
    }
}






