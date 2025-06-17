package com.scancam.controller;

import com.scancam.service.CaptureImages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Controller
public class CaptureController {

    @Autowired
    private CaptureImages captureImages;

    @PostMapping("/captureth")
    public String startCapture( String url) {
        try {
            ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);

            executorService.scheduleAtFixedRate(() -> {
                try {
                    captureImages.start(url);
                } catch (Exception e) {
                    System.err.println("Error during capture: " + e.getMessage());
                }
            }, 0, 60, TimeUnit.SECONDS);

            return "index";
        } catch (Exception e) {
            System.err.println("Error during capture: " + e.getMessage());
            return "Error starting capture: ";
        }
    }

}
