package com.scancam.controller;

import com.scancam.model.CityModel;
import com.scancam.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/city")
public class CityController {

    @Autowired
    private CityService cityService;

    @GetMapping("all")
    public ResponseEntity<List<CityModel>> getAllCities() {
        List<CityModel> cities = cityService.getAllCities();
        if (cities != null && !cities.isEmpty()) {
            return ResponseEntity.ok(cities);
        } else {
            return ResponseEntity.status(404).body(null);
        }
    }

    @GetMapping("updateTemperatue")
    public ResponseEntity<String> updateCityTemperature() {
        try {
            for(CityModel city: cityService.getAllCities()){
                //metodo que busca la temperatura dela ciudad
                city.setTemperature("10");
                //
                cityService.updateCity(city);
            }
            return ResponseEntity.ok("City temperature updated successfully");
        } catch (Exception e) {
            System.err.println("Error updating city temperature: " + e.getMessage());
            return ResponseEntity.status(500).body("Error updating city temperature: " + e.getMessage());
        }
    }

}
