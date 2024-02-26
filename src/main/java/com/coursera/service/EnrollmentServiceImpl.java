package com.coursera.service;

import com.coursera.exception.CourseNotFoundException;
import com.coursera.model.Course;
import com.coursera.model.User;
import com.coursera.model.UserCourseDtl;
import com.coursera.repository.CourseRepository;
import com.coursera.repository.CustomJpaRepository;
import com.coursera.repository.UserCourseDtlRepository;
import com.coursera.vo.StudentEnrollments;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EnrollmentServiceImpl implements EnrollmentService{

    private final CustomJpaRepository customJpaRepository;

    private final UserCourseDtlRepository userCourseDtlRepository;

    private final CourseRepository courseRepository;

    private final UserService userService;

    public EnrollmentServiceImpl(CustomJpaRepository customJpaRepository,
                             UserCourseDtlRepository userCourseDtlRepository1,
                             CourseRepository courseRepository,
                             UserService userService) {
        this.customJpaRepository = customJpaRepository;
        this.userCourseDtlRepository = userCourseDtlRepository1;
        this.courseRepository = courseRepository;
        this.userService = userService;
    }

    @Override
    public List<StudentEnrollments> getEnrollments() {
        org.springframework.security.core.userdetails.User user =
                (org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext()
                        .getAuthentication().getPrincipal();
        String createdBy = null;
        if (user.getAuthorities().stream().anyMatch(r -> "ROLE_CONTRIBUTER".equals(r.getAuthority()))) {
            createdBy = user.getUsername();
        }
        return customJpaRepository.findAllEnrollments(createdBy);
    }

    @Override
    public List<Course> getEnrollmentsByUserName(String name) {
        User user = userService.getUser(name);
        List<UserCourseDtl> userCourseDtlList = userCourseDtlRepository.findByUserId(user.getId());
        if (userCourseDtlList.isEmpty())
            return Collections.emptyList();
        List<BigDecimal> courseIdList = userCourseDtlList.stream()
                .map(UserCourseDtl::getCourseId).collect(Collectors.toList());
        List<Course> allById = courseRepository.findAllById(courseIdList);
        org.springframework.security.core.userdetails.User loggedUser
                = (org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
        if (loggedUser.getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_CONTRIBUTER"))) {
            return allById
                    .stream()
                    .filter(c -> loggedUser.getUsername().equals(c.getCreatedBy())).collect(Collectors.toList());
        }
        return allById;
    }

    @Override
    public void disenrollCourse(String userName, Optional<BigDecimal> id) {
        User user = userService.getUser(userName);
        UserCourseDtl userCourseDtl = userCourseDtlRepository
                .findByUserIdAndCourseId(user.getId(), id.orElseThrow(IllegalArgumentException::new))
                .orElseThrow(() -> new CourseNotFoundException("\"Enrolled Course not found with id " + id));
        userCourseDtlRepository.delete(userCourseDtl);
    }
}
