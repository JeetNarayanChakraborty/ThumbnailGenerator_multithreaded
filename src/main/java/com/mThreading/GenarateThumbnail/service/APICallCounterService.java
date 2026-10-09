package com.mThreading.GenarateThumbnail.service;

import org.springframework.stereotype.Component;
import java.util.concurrent.atomic.AtomicInteger;


@Component
public class APICallCounterService 
{
	private final AtomicInteger apiCallCount = new AtomicInteger(0);

	public void incrementCallCount()
	{
		apiCallCount.incrementAndGet();
	}
	public int getApiCallCount()
	{
		return apiCallCount.get();
	}
	public void resetCallCount()
	{
		apiCallCount.set(0);
	}
}
