package com.coursera.repository;

import com.coursera.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Course, BigDecimal> {

    List<Course> findByActiveTrue();

    List<Course> findByCreatedBy(String createdBy);
}
