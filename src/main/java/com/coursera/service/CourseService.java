package com.coursera.service;

import com.coursera.model.Course;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface CourseService {
    List<Course> getCourses(org.springframework.security.core.userdetails.User user);

    Course saveCourse(Course course);

    Course getCourse(Optional<BigDecimal> id);

    void enrollCourse(String userName, Optional<BigDecimal> id);

    void activateCourse(Optional<BigDecimal> id);
}
