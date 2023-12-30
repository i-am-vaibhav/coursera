package com.coursera.repository;

import com.coursera.vo.StudentEnrollments;

import java.util.List;

public interface CustomJpaRepository {

    List<StudentEnrollments> findAllEnrollments(String createdBy);
}
