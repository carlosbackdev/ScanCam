package com.scancam.Task;

import com.scancam.model.UserModel;
import com.scancam.service.CityService;
import com.scancam.service.DataService;
import com.scancam.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataCaptureRunner implements ApplicationRunner {
    private static final Logger LOG= LoggerFactory.getLogger(
            DataCaptureRunner.class);

    @Autowired
    DataService dataService;

    @Autowired
    UserService userService;

    @Autowired
    CityService cityService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        LOG.info("Lista de caputuras:");
        dataService.getData().forEach(caputra ->{
            LOG.info(caputra.toString());
        });
        LOG.info("Lista de usuarios:");
        userService.getAllUsers().forEach(user ->{
           LOG.info(user.toString());
        });
        LOG.info("Lista de ciudades:");
        cityService.getAllCities().forEach(city ->{
            LOG.info(city.toString());
        });
    }
}
