package com.scancam.utils;

import com.scancam.model.CaptureModel;
import com.scancam.model.CityModel;
import com.scancam.service.CityService;
import com.scancam.service.DataService;
import com.scancam.service.WheaterApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Guardat implements CommandLineRunner {

    @Autowired
    private DataService dataService;
    @Autowired
    private CityService cityService;
    @Autowired
    private WheaterApi wheaterApi;

    @Override
    public void run(String... args) throws Exception {

        List<CaptureModel> captures = dataService.getData();
        for(CaptureModel capture : captures){
            cityService.cityExistByName(capture);
        }
        for(CityModel city: cityService.getAllCities()){
            wheaterApi.getWeather(city);        }
    }
}
