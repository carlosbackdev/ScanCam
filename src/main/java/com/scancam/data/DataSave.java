package com.scancam.data;

import com.scancam.model.CaptureModel;
import com.scancam.model.CityModel;
import com.scancam.service.ComputerVisionService;
import com.scancam.service.DataService;
import com.scancam.service.CityService;
import com.scancam.utils.FormateText;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class DataSave {
    private CaptureModel captureModel;

    @Autowired
    private DataService dataService;

    @Autowired
    private CityService cityService;

    @Autowired
    private ComputerVisionService computerVisionService;

    public void saveData(String filename, String url) {

        captureModel = new CaptureModel();
        captureModel.setImageUrl(filename);
        captureModel.setPeople(getPeopleCount(filename));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss dd-MM-yyyy");
        captureModel.setDate(LocalDateTime.parse(LocalDateTime.now().format(formatter), formatter));
        String city= FormateText.extractCity(url);
        captureModel.setCity(city);
        String location = FormateText.extractLocation(url);
        captureModel.setLocation(location);

        dataService.saveData(captureModel);
        System.out.println("Data saved successfully: " + captureModel);

        cityService.cityExistByName(captureModel);

    }
    public long getPeopleCount(String filename) {
        long peopleCount = 0;
        try{
            peopleCount = computerVisionService.countPeopleInImage(filename);

        }catch (Exception e) {
            System.err.println("Error initializing ComputerVisionService: " + e.getMessage());
            return 0;
        }
        return peopleCount;
    }
}
