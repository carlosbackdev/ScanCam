package com.scancam.controller;


import com.scancam.service.CaptureImages;
import com.scancam.service.ComputerVisionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("capture")
public class CaptureImagesController {

    @Autowired
    private CaptureImages captureImages;


    @PostMapping("start")
    public ResponseEntity<String> startCapture(@RequestBody String url) {
        try {
            ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);

            executorService.scheduleAtFixedRate(() -> {
                try {
                    captureImages.start(url);
                } catch (Exception e) {
                    System.err.println("Error during capture: " + e.getMessage());
                }
            }, 0, 60, TimeUnit.SECONDS);

            return ResponseEntity.ok("Capture started successfully");
        } catch (Exception e) {
            System.err.println("Error during capture: " + e.getMessage());
            return ResponseEntity.status(500).body("Error starting capture: " + e.getMessage());
        }
    }

}
