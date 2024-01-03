package com.coursera.service;

import com.coursera.model.Course;
import com.coursera.model.User;
import com.coursera.model.UserCourseDtl;
import com.coursera.repository.CourseRepository;
import com.coursera.repository.UserCourseDtlRepository;
import com.coursera.repository.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service

public class UserService {

    private final UserRepository userRepository;

    private final UserCourseDtlRepository userCourseDtlRepository;

    private final CourseRepository courseRepository;

    public UserService(UserRepository userRepository, UserCourseDtlRepository userCourseDtlRepository, CourseRepository courseRepository) {
        this.userRepository = userRepository;
        this.userCourseDtlRepository = userCourseDtlRepository;
        this.courseRepository = courseRepository;
    }

    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public User getUser(Optional<BigDecimal> id) {
        return userRepository.findById(id.orElseThrow(IllegalArgumentException::new))
                .orElseThrow(() -> new UsernameNotFoundException("User not found with " + id.get()));
    }

    public User getUser(String userName) {
        return userRepository.findByUserName(userName)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with " + userName));
    }

    public User saveUser(User user) {
        return userRepository.save(user);
    }

    public void deleteUser(Optional<BigDecimal> id) {
        userRepository.deleteById(id.orElseThrow(IllegalArgumentException::new));
    }

    public List<Course> getEnrolledCourses(String name) {
        User user = getUser(name);
        List<UserCourseDtl> userCourseDtls = userCourseDtlRepository.findByUserId(user.getId());
        List<BigDecimal> list = userCourseDtls.stream().map(UserCourseDtl::getCourseId).collect(Collectors.toList());
        return courseRepository.findAllById(list);
    }
    public void lockUser(Optional<BigDecimal>id){
        User user = getUser(id);
        if(user.getLocked()){
            user.setLocked(false);
        }else {
            user.setLocked(true);
        }
        saveUser(user);
    }
}
