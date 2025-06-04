package com.mThreading.GenarateThumbnail.service;

import org.springframework.stereotype.Component;


@Component
public class APICallCounterService 
{
	private int apiCallCount = 0;

	public synchronized void incrementCallCount() // Method to increment the API call count
	{
		apiCallCount++;
	}

	public synchronized int getApiCallCount()  // Method to retrieve the current API call count	
	{
		return apiCallCount;
	}

	public synchronized void resetCallCount() // Method to reset the API call count
	{
		apiCallCount = 0;
	}
}
