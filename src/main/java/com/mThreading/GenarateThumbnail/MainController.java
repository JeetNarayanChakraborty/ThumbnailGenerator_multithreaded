package com.mThreading.GenarateThumbnail;

import com.mThreading.GenarateThumbnail.service.ThumbnailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import com.mThreading.GenarateThumbnail.service.APICallCounterService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;


@Controller
public class MainController 
{
    private static final String ORIGINAL_DIR = "src/main/resources/static/originals/";

    @Autowired
    private ThumbnailService thumbnailService;
    
    @Autowired
    private APICallCounterService APICallCounterService; 
    
    
    @GetMapping("/home")
    public String getHomePage()
    {
    	return "homePage";
    }

    @PostMapping("/upload")
    public String uploadImages(@RequestParam("files") MultipartFile[] files, Model model) 
    {
        List<String> thumbnailFiles = new ArrayList<>();

        for(MultipartFile file : files) 
        {
            try 
            {
                String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
                String path = ORIGINAL_DIR + fileName;

                Files.copy(file.getInputStream(), Paths.get(path)); // Save original file
                thumbnailService.generateThumbnailAsync(fileName); // Trigger async thumbnail creation
                thumbnailFiles.add("thumb_" + fileName);
                model.addAttribute("message", "Upload successful!");
            }
            
            catch(Exception e) 
            {
            	model.addAttribute("message", "Failed to upload: " + e.getMessage());
            }
        }
        
        return "uploadStatus";
    }
    
    @GetMapping("/getAPICallCount")
    @ResponseBody
    public String getCurrTenSecondAPICallCount() 
	{
    	int apiCallCount = APICallCounterService.getApiCallCount(); // Returns the current 10 second API call count
		return "API calls per 10 seconds: " + apiCallCount;
	}
}









