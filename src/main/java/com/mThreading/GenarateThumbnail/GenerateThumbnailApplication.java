package com.mThreading.GenarateThumbnail;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;


@SpringBootApplication
@EnableScheduling
public class GenerateThumbnailApplication
{
	public static void main(String[] args) 
	{
		SpringApplication.run(GenerateThumbnailApplication.class, args);
	}
}

