package com.scancam.controller;

import com.google.j2objc.annotations.Weak;
import com.scancam.model.CityModel;
import com.scancam.service.CityService;
import com.scancam.service.WheaterApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/weather")
public class WheaterController {

    @Autowired
    WheaterApi weatherService;
    @Autowired
    CityService cityService;

    @GetMapping("/refresh")
    public void refreshWeatherData() {
        for(CityModel city: cityService.getAllCities()){
            try {
                var cityWithWheater = weatherService.getWeather(city);
                cityService.updateCity(cityWithWheater);
            }catch (Exception e){
                System.err.println("Error fetching weather data for city: " + city.getName() + " - " + e.getMessage());
            }

        }
    }

}
