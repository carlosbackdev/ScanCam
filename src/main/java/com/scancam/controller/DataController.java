package com.scancam.controller;

import com.scancam.model.CaptureModel;
import com.scancam.service.DataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class DataController {

    @Autowired
    private DataService dataService;


    @GetMapping("/")
    public String getData(Model model) {
        model.addAttribute("captures",dataService.getData());
        return "index";
    }


}
