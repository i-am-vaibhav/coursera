package com.coursera.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class HomeController {
    @GetMapping("/login")
    public String loginPage(){
        return "login";
    }


    @GetMapping("/home")
    public String homePage(){
        return "home";
    }
}
