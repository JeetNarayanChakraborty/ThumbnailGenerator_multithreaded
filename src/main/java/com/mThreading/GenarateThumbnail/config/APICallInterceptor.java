package com.mThreading.GenarateThumbnail.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import com.mThreading.GenarateThumbnail.service.APICallCounterService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@Component
public class APICallInterceptor implements HandlerInterceptor
{
	@Autowired
    private APICallCounterService apiCallCounterService;

	
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) 
    {
    	apiCallCounterService.incrementCallCount();  // Intercepts API calls and increments the call count
        return true;
    }
}
