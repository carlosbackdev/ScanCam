package com.scancam.service;

import com.scancam.model.CaptureModel;
import com.scancam.model.CityModel;
import com.scancam.repository.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CityService {

    @Autowired
    private CityRepository cityRepository;

    public List<CityModel> getAllCities() {
        List<CityModel> cities = cityRepository.findAll();
        return cities;
    }

    public void saveCity(CityModel cityModel) {
        if (cityModel != null) {
            cityRepository.save(cityModel);
            System.out.println("City saved successfully: " + cityModel);
        } else {
            System.out.println("CityModel is null, cannot save city.");
        }
    }

    public void updateCity(CityModel cityModel) {
        if (cityModel != null && cityRepository.existsById(cityModel.getId())) {
            cityRepository.save(cityModel);
            System.out.println("City updated successfully: " + cityModel);
        } else {
            System.out.println("CityModel is null or does not exist, cannot update city.");
        }
    }

    public void cityExistByName(CaptureModel capture) {
        boolean exist = getAllCities().stream()
                .anyMatch(city -> city.getName().equalsIgnoreCase(capture.getCity()));
        if (!exist) {
            CityModel cityModel = new CityModel();
            cityModel.setName(capture.getCity());
            saveCity(cityModel);
        }
    }

}
