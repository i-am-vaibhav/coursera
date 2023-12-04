package com.coursera.controller;

import com.coursera.model.Course;
import com.coursera.model.User;
import com.coursera.service.CourseService;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Optional;

@Controller
@RequestMapping("/courses")
@Secured({"ROLE_ADMIN","ROLE_STUDENT"})
public class CourseController {

    private static final String COURSE_FOLDER = "course/";

    public final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping
    public String getCoursesPage(Model model){
        model.addAttribute("courses",courseService.getCourses());
        return COURSE_FOLDER + "courses";
    }

    @GetMapping("/create")
    public String getCoursesCreatePage(Model model){
        model.addAttribute("course",new Course());
        return COURSE_FOLDER + "course";
    }

    @GetMapping("/{id}/update")
    public String getCoursesUpdatePage(Model model,
                                       @PathVariable Optional<BigDecimal> id){
        model.addAttribute("course",courseService.getCourse(id));
        return COURSE_FOLDER + "course";
    }

    @PostMapping
    public String saveCourse(Model model, @ModelAttribute Course course){
        courseService.saveCourse(course);
        return "redirect:/courses";
    }

}
