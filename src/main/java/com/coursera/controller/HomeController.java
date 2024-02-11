package com.coursera.controller;

import com.coursera.model.User;
import com.coursera.service.UserService;
import com.coursera.vo.ChangePassword;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.Optional;


@Controller
public class HomeController {
    private final UserService userService;

    public HomeController(
            @Qualifier("userServiceImpl") UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }


    @GetMapping("/home")
    public String homePage(Model model) {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.getUser(name);
        model.addAttribute("user", user);
        model.addAttribute("courses", userService.getEnrolledCourses(Optional.ofNullable(user.getId())));
        Object msg = model.asMap().get("message");
        if(msg!=null)
            model.addAttribute("message",(String)msg);
        return "home";
    }


    @GetMapping("/profile")
    public String profilePage(Model model) {
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        model.addAttribute("user", userService.getUser(userName));
        return "user/viewUser";
    }

    @ModelAttribute
    public ChangePassword changePassword(){
        return ChangePassword.builder().build();
    }
}
