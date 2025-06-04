package com.mThreading.GenarateThumbnail.service;

import java.io.File;
import java.util.concurrent.ExecutorService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.mThreading.GenarateThumbnail.util.ImageUtils;


@Service
public class ThumbnailService 
{
	private static final Logger log = LoggerFactory.getLogger(ThumbnailService.class);
    private static final String ORIGINAL_DIR = "src/main/resources/static/originals/";
    private static final String THUMBNAIL_DIR = "src/main/resources/static/thumbnails/";

    @Autowired
    private ExecutorService executorService;

    
    public void generateThumbnailAsync(String fileName) 
    {
        executorService.submit(() -> 
        {
            try 
            {
                File input = new File(ORIGINAL_DIR + fileName);
                File output = new File(THUMBNAIL_DIR + "thumb_" + fileName);
                ImageUtils.createThumbnail(input, output, 150, 150);
                log.info("SUCCESS: Thumbnail created for {}", fileName);
            } 
            
            catch(Exception e) 
            {
            	log.info("SUCCESS: Thumbnail created for {}", fileName);
            }
        });
    }
}






