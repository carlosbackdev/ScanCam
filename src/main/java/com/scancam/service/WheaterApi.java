package com.scancam.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.scancam.model.CityModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class WheaterApi {
    private static final String url="http://api.weatherapi.com/v1/current.json?key=d995fbb6786d490bb0193554251806&q=";
    private static final ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private RestTemplate restTemplate;

    public CityModel getWeather(CityModel city) {
        String cityName = city.getName();
        if (cityName.equalsIgnoreCase("roma")){
            cityName = "Rome";
        }
        String response=restTemplate.getForObject(url+cityName.toLowerCase(), String.class);
        System.out.println("Response: " + response);
        double temperature = 0.0;
        String condition="";
        String icon="";
        try {
            JsonNode root = mapper.readTree(response);
            temperature = root.path("current").path("temp_c").asDouble();
            condition = root.path("current").path("condition").path("text").asText();
            icon = root.path("current").path("condition").path("icon").asText();
        }catch (JsonProcessingException e){
            System.err.println("Error parsing JSON response: " + e.getMessage());
            city.setTemperature(-1.0);
            city.setCondition("No data available");
            city.setIcon("no data available");
            return city;
        }

        city.setTemperature(temperature);
        city.setCondition(condition);
        city.setIcon(icon);

        return city;
    }

}
