package com.mThreading.GenarateThumbnail.service;

import com.mThreading.GenarateThumbnail.util.ImageUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.concurrent.ExecutorService;


@Service
public class ThumbnailService 
{
	private static final Logger log = LoggerFactory.getLogger(ThumbnailService.class);
    private static final String ORIGINAL_DIR = "src/main/resources/static/originals/";
    private static final String THUMBNAIL_DIR = "src/main/resources/static/thumbnails/";

    @Autowired
    private ExecutorService executorService;
    
    public void processImageAsync(String fileName, byte[] fileBytes)
    {
        executorService.submit(() -> 
        {
            try 
            {
                String originalPath = ORIGINAL_DIR + fileName;
                File output = new File(THUMBNAIL_DIR + "thumb_" + fileName);

                ByteArrayInputStream inputStream = new ByteArrayInputStream(fileBytes);
                Files.copy(inputStream, Paths.get(originalPath), StandardCopyOption.REPLACE_EXISTING);
                log.info("BACKGROUND SUCCESS: Original file saved for {}", fileName);

                File input = new File(originalPath);
                ImageUtils.createThumbnail(input, output, 150, 150);
                log.info("BACKGROUND SUCCESS: Thumbnail created for {}", fileName);
            } 
            
            catch(Exception e) 
            {
                log.error("ERROR: Failed to create thumbnail for {}: {}", fileName, e.getMessage());
            }
        });
    }
}






