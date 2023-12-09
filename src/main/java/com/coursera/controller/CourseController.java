package com.coursera.controller;

import com.coursera.model.Course;
import com.coursera.security.AuthenticatedUser;
import com.coursera.service.CourseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Optional;

@Slf4j
@Controller
@RequestMapping("/courses")
@Secured({"ROLE_ADMIN"})
public class CourseController {

    private static final String COURSE_FOLDER = "course/";

    public final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping
    @Secured({"ROLE_STUDENT","ROLE_ADMIN"})
    public String getCoursesPage(Model model){
        log.debug("getCoursesPage started");
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("courses",courseService.getCourses(user));
        return COURSE_FOLDER + "courses";
    }

    @GetMapping("/create")
    public String getCoursesCreatePage(Model model){
        log.debug("getCoursesCreatePage started");
        model.addAttribute("course",new Course());
        return COURSE_FOLDER + "course";
    }

    @GetMapping("/{id}/update")
    public String getCoursesUpdatePage(Model model,
                                       @PathVariable Optional<BigDecimal> id){

        log.debug("getCoursesUpdatePage started");
        model.addAttribute("course",courseService.getCourse(id));
        return COURSE_FOLDER + "course";
    }

    @PostMapping
    public String saveCourse(Model model, @ModelAttribute Course course){
        log.debug("saveCourse started");
        courseService.saveCourse(course);
        return "redirect:/courses";
    }

    @GetMapping("/{id}/enroll")
    @Secured("ROLE_STUDENT")
    public String enrollCourse(Model model,@PathVariable Optional<BigDecimal> id){
        log.debug("enrollCourse started");
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        courseService.enrollCourse(userName,id);
        return "redirect:/home";
    }

    @GetMapping("/{id}/activate")
    public String activateCourse(Model model,@PathVariable Optional<BigDecimal> id){
        courseService.activateCourse(id);
        return "redirect:/courses";
    }

}
