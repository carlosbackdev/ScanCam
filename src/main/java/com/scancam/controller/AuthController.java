package com.scancam.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthController {

    @GetMapping("/login")
    public String login() {
        // This method will return the login page
        return "login"; // Assuming you have a login.html in your templates directory
    }

}
