package com.coursera.service;

import com.coursera.model.Course;
import com.coursera.vo.StudentEnrollments;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface EnrollmentService {
    List<StudentEnrollments> getEnrollments();

    List<Course> getEnrollmentsByUserName(String name);

    void disenrollCourse(String userName, Optional<BigDecimal> id);
}
