package com.coursera.controller;

import com.coursera.model.User;
import com.coursera.service.UserService;
import com.coursera.vo.ChangePassword;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Optional;

@Controller
@Secured("ROLE_ADMIN")
@RequestMapping("/users")
public class UserController {

    private static final String USER_FOLDER = "user/";

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String getUsersPage(Model model) {
        model.addAttribute("users", userService.getUsers());
        return USER_FOLDER + "users";
    }

    @GetMapping("/{id}")
    public String getUserDetailsPage(Model model, @PathVariable("id") Optional<BigDecimal> id) {
        model.addAttribute("user", userService.getUser(id));
        return USER_FOLDER + "viewUser";
    }

    @GetMapping("/create")
    public String createUserPage(Model model) {
        model.addAttribute("user", new User());
        return USER_FOLDER + "user";
    }

    @GetMapping("/{id}/update")
    public String updateUserPage(Model model, @PathVariable("id") Optional<BigDecimal> id) {
        model.addAttribute("user", userService.getUser(id));
        return USER_FOLDER + "user";
    }

    @GetMapping("/{id}/delete")
    public String deleteUser(Model model, @PathVariable("id") Optional<BigDecimal> id) {
        userService.deleteUser(id);
        return "redirect:/users";
    }

    @PostMapping
    public String saveUser(Model model, @ModelAttribute User user) {
        userService.saveUser(user);
        return "redirect:/users";
    }
    @GetMapping("/{id}/lock")
    public String lockUser(Model model,@PathVariable("id") Optional<BigDecimal> id){
        userService.lockUser(id);
        return "redirect:/users";
    }

    /*
    Redirect Attributes is used to send attributes to redirected API's model
     */
    @PostMapping("/password")
    public  String changePassword(Model model, @ModelAttribute ChangePassword changePassword,
                                  RedirectAttributes redirectAttributes){
        redirectAttributes.addFlashAttribute("message"
                ,userService.changePassword(changePassword));
        return "redirect:/home";
    }
}
