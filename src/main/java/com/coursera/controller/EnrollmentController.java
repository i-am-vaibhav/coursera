package com.coursera.controller;

import com.coursera.model.Course;
import com.coursera.service.EnrollmentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/enrollments")
@Secured({"ROLE_ADMIN", "ROLE_CONTRIBUTER"})
@Slf4j
public class EnrollmentController {

    public static final String ENROLL_FOLDER = "enrollment/";
    private final EnrollmentService enrollmentService;

    public EnrollmentController(EnrollmentService enrollmentService) {
        this.enrollmentService = enrollmentService;
    }

    @GetMapping
    public String getStudentEnrollments(Model model) {
        model.addAttribute("enrollments", enrollmentService.getEnrollments());
        return ENROLL_FOLDER + "enrollments";
    }

    @GetMapping("/{name}")
    public String viewStudentEnrollment(Model model, @PathVariable String name) {
        List<Course> enrollmentsByUserName = enrollmentService.getEnrollmentsByUserName(name);
        if (enrollmentsByUserName.isEmpty())
            return "redirect:/enrollments";
        model.addAttribute("courses", enrollmentsByUserName);
        model.addAttribute("name", name);
        return ENROLL_FOLDER + "showEnrollment";
    }

    @GetMapping("/{id}/disenroll/{name}")
    public String disenrollCourse(Model model, @PathVariable Optional<BigDecimal> id,
                                  @PathVariable String name) {
        log.debug("disenrollCourse started");
        enrollmentService.disenrollCourse(name, id);
        return "redirect:/enrollments/" + name;
    }

}
