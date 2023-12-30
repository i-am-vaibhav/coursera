package com.coursera.service;

import com.coursera.exception.CourseNotFoundException;
import com.coursera.model.Course;
import com.coursera.model.User;
import com.coursera.repository.CourseRepository;
import com.coursera.security.AuthenticatedUser;
import com.coursera.util.Role;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.test.context.support.WithMockUser;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CourseServiceTest {

    @Mock
    private CourseRepository courseRepository;

    @InjectMocks
    private CourseService courseService;

    @Test
    void getCourses() {
        AuthenticatedUser user = new AuthenticatedUser(new User(BigDecimal.ONE,"Vaibhav","V@gmail.com","Vtest", Role.STUDENT, false));
        List<Course> courses = courseService.getCourses(user);
        assertNotNull(courses);
    }

    @Test
    void getCoursesAdmin() {
        AuthenticatedUser user = new AuthenticatedUser(new User(BigDecimal.ONE,"Vaibhav","V@gmail.com","Vtest", Role.ADMIN, false));
        List<Course> courses = courseService.getCourses(user);
        assertNotNull(courses);
    }

    @Test
    void getCoursesWithData() {
        AuthenticatedUser user = new AuthenticatedUser(new User(BigDecimal.ONE,"Vaibhav","V@gmail.com","Vtest", Role.STUDENT,false));
        List<Course> expected = new ArrayList<>();
        expected.add(new Course());
        when(courseRepository.findByActiveTrue()).thenReturn(expected);
        List<Course> courses = courseService.getCourses(user);
        assertIterableEquals(expected,courses);
    }

    @Test
    void getCoursesWithDataAdmin() {
        AuthenticatedUser user = new AuthenticatedUser(new User(BigDecimal.ONE,"Vaibhav","V@gmail.com","Vtest", Role.ADMIN, false));
        List<Course> expected = new ArrayList<>();
        expected.add(new Course());
        when(courseRepository.findAll()).thenReturn(expected);
        List<Course> courses = courseService.getCourses(user);
        assertIterableEquals(expected,courses);
    }

    @Test
    void saveCourse() {
        Course mockCourse = new Course(BigDecimal.ONE,"cat1","Vname", "Desc","",true,"Vaibhav");
        when(courseRepository.save(Mockito.any(Course.class))).thenReturn(mockCourse);
        Course course = new Course(null,"cat1","Vname", "Desc","",true,"Vaibhav");
        Course course1 = courseService.saveCourse(course);
        assertNotNull(course1.getId());
    }

    @Test
    void saveCourseUpdate() {
        Course mockCourse = new Course(BigDecimal.ONE,"cat1","Vname1", "Desc","",true,"Vaibhav");
        when(courseRepository.save(Mockito.any(Course.class))).thenReturn(mockCourse);
        Course course = new Course(null,"cat1","Vname", "Desc","",true,"Vaibhav");
        Course course1 = courseService.saveCourse(course);
        assertNotEquals(course.getName(),course1.getName());
    }
    @Test
    void getCourse() {
        Optional<Course> courseOptional = Optional.of(new Course(BigDecimal.ONE,"Vaibhav","V@gmail.com","Vtest","",true,"Vaibhav"));
        when(courseRepository.findById(Mockito.any())).thenReturn(courseOptional);
        Course course = courseService.getCourse(Optional.of(BigDecimal.ONE));
        assertNotNull(course);
        assertEquals(course.getId(),courseOptional.get().getId());
    }

    @Test
    void getCourseWithIllegalArgEx() {
        assertThrows(IllegalArgumentException.class , () -> courseService.getCourse(Optional.empty()));
    }

    @Test
    void getCourseWithCourseNotFoundExe() {
        assertThrows(CourseNotFoundException.class , () -> courseService.getCourse(Optional.of(BigDecimal.ONE)));
    }
}