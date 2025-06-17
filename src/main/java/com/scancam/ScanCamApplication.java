package com.scancam;

import com.scancam.model.CaptureModel;
import com.scancam.model.UserModel;
import com.scancam.repository.UserRepository;
import com.scancam.service.CaptureImages;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.ServerProperties;

@SpringBootApplication
public class ScanCamApplication {

    public static void main(String[] args) {
        SpringApplication.run(ScanCamApplication.class, args);
        System.out.println("ScanCam Application started successfully!");
        ServerProperties serverProperties = new ServerProperties();
        serverProperties.getCompression().setEnabled(true);


    }

}
