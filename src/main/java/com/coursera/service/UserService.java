package com.coursera.service;

import com.coursera.model.Course;
import com.coursera.model.User;
import com.coursera.vo.ChangePassword;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface UserService {
    List<User> getUsers();

    User getUser(Optional<BigDecimal> id);

    User getUser(String userName);

    User saveUser(User user);

    void deleteUser(Optional<BigDecimal> id);

    List<Course> getEnrolledCourses(Optional<BigDecimal> id);

    void lockUser(Optional<BigDecimal> id);

    String changePassword(ChangePassword changePassword);
}
