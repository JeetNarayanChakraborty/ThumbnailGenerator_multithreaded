package com.mThreading.GenarateThumbnail;

import com.mThreading.GenarateThumbnail.service.APICallCounterService;
import com.mThreading.GenarateThumbnail.service.ThumbnailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Controller
public class MainController
{
    private static final Logger log = LoggerFactory.getLogger(MainController.class);

    // OPTIMIZATION: Ensure directories exist safely at startup using an absolute target path if needed
    private static final String ORIGINAL_DIR = "src/main/resources/static/originals/";

    @Autowired
    private ThumbnailService thumbnailService;

    @Autowired
    private APICallCounterService apiCallCounterService;

    @GetMapping("/home")
    public String getHomePage()
    {
        return "homePage";
    }

    @PostMapping("/upload")
    public String uploadImages(@RequestParam("files") MultipartFile[] files, Model model)
    {
        // Increment counter on every incoming load metric spike!
        apiCallCounterService.incrementCallCount();

        List<String> successfulThumbnails = new ArrayList<>();
        List<String> failedFiles = new ArrayList<>();

        File directory = new File(ORIGINAL_DIR);

        if(!directory.exists())
        {
            directory.mkdirs();
        }

        for(MultipartFile file : files)
        {
            if(file.isEmpty()) continue; // Skip accidental empty inputs

            try
            {
                String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
                byte[] fileBytes = file.getBytes();

                // Fire async task into our dynamically scaling thread pool
                thumbnailService.processImageAsync(fileName, fileBytes);

                successfulThumbnails.add("thumb_" + fileName);
            }
            catch(Exception e)
            {
                log.error("Failed to process file: {}", file.getOriginalFilename(), e);
                failedFiles.add(file.getOriginalFilename());
            }
        }

        if(failedFiles.isEmpty()) model.addAttribute("message", "All uploads processed successfully!");
        else if(successfulThumbnails.isEmpty()) model.addAttribute("message", "All uploads failed : " + failedFiles);
        else model.addAttribute("message", "Partial Success! Failed items: " + failedFiles);

        model.addAttribute("thumbnails", successfulThumbnails);
        return "uploadStatus";
    }

    @GetMapping("/getAPICallCount")
    @ResponseBody
    public String getCurrTenSecondAPICallCount()
    {
        int apiCallCount = apiCallCounterService.getApiCallCount();
        return "API calls per 10 seconds: " + apiCallCount;
    }
}
