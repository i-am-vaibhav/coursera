package com.coursera.controller;

import com.coursera.service.UserService;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class HomeController {
    private final UserService userService;

    public HomeController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String loginPage(){
        return "login";
    }


    @GetMapping("/home")
    @Secured({"ROLE_ADMIN","ROLE_STUDENT"})
    public String homePage(){
        return "home";
    }


    @GetMapping("/profile")
    @Secured({"ROLE_ADMIN","ROLE_STUDENT"})
    public String profilePage(Model model){
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        model.addAttribute("user",userService.getUser(userName));
        return "user/viewUser";
    }
}
