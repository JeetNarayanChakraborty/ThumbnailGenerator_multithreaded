package com.mThreading.GenarateThumbnail.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import com.mThreading.GenarateThumbnail.config.APICallInterceptor;


@Configuration
public class WebConfig implements WebMvcConfigurer    //register the API Call interceptor
{
    @Autowired
    private APICallInterceptor apiInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) 
    {
        registry.addInterceptor(apiInterceptor);
    }
}
