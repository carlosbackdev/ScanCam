package com.scancam.data;

import com.scancam.model.CaptureModel;
import com.scancam.repository.CaptureRepository;
import com.scancam.service.ComputerVisionService;
import com.scancam.service.DataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.apache.commons.text.WordUtils;

@Component
public class DataSave {
    private CaptureModel captureModel;

    @Autowired
    private DataService dataService;

    public void saveData(String filename, String url) {


        ComputerVisionService computerVisionService = new ComputerVisionService();
        long peopleCount = computerVisionService.countPeopleInImage(filename);

        captureModel = new CaptureModel();
        captureModel.setImageUrl(filename);
        captureModel.setPeople(peopleCount);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss dd-MM-yyyy");
        captureModel.setDate(LocalDateTime.parse(LocalDateTime.now().format(formatter), formatter));
        int lastSlashIndex = url.lastIndexOf("/");
        int secondLastSlashIndex = url.lastIndexOf("/", lastSlashIndex - 1);
        String city = url.substring(secondLastSlashIndex + 1, lastSlashIndex);
        city=WordUtils.capitalize(city);
        captureModel.setCity(city);
        int indexlast = url.lastIndexOf("/");
        String location = url.substring(indexlast + 1, url.lastIndexOf(".html"));
        location=location.replace("-", " ");
        location=WordUtils.capitalize(location);
        captureModel.setLocation(location);

        dataService.saveData(captureModel);
        System.out.println("Data saved successfully: " + captureModel);

    }
}
