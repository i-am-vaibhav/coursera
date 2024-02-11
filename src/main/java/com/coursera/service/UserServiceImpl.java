package com.coursera.service;

import com.coursera.model.Course;
import com.coursera.model.User;
import com.coursera.model.UserCourseDtl;
import com.coursera.repository.CourseRepository;
import com.coursera.repository.UserCourseDtlRepository;
import com.coursera.repository.UserRepository;
import com.coursera.vo.ChangePassword;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final UserCourseDtlRepository userCourseDtlRepository;

    private final CourseRepository courseRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserServiceImpl(UserRepository userRepository, UserCourseDtlRepository userCourseDtlRepository, CourseRepository courseRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.userCourseDtlRepository = userCourseDtlRepository;
        this.courseRepository = courseRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public List<User> getUsers() {
        return userRepository.findAll();
    }


    @Override
    public User getUser(Optional<BigDecimal> id) {
        return userRepository.findById(id.orElseThrow(IllegalArgumentException::new))
                .orElseThrow(() -> new UsernameNotFoundException("User not found with " + id.get()));
    }


    @Override
    public User getUser(String userName) {
        return userRepository.findByUserName(userName)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with " + userName));
    }


    @Override
    public User saveUser(User user) {
        return userRepository.save(user);
    }


    @Override
    public void deleteUser(Optional<BigDecimal> id) {
        userRepository.deleteById(id.orElseThrow(IllegalArgumentException::new));
    }


    @Override
    public List<Course> getEnrolledCourses(Optional<BigDecimal> id) {
        List<UserCourseDtl> userCourseDtls = userCourseDtlRepository
                .findByUserId(id.orElseThrow(IllegalArgumentException::new));
        List<BigDecimal> list = userCourseDtls.stream().map(UserCourseDtl::getCourseId).collect(Collectors.toList());
        return courseRepository.findAllById(list);
    }

    @Override
    public void lockUser(Optional<BigDecimal>id){
        User user = getUser(id);
        if(user.getLocked()){
            user.setLocked(false);
        }else {
            user.setLocked(true);
        }
        saveUser(user);
    }


    @Override
    public String changePassword(ChangePassword changePassword){
        User user = getUser(Optional.ofNullable(changePassword.id()));
        if (!bCryptPasswordEncoder.matches(changePassword.oldPassword(), user.getPassword())){
            return "Sorry, that password isn't right!";
        } else {
            String password = bCryptPasswordEncoder.encode(changePassword.newPassword());
            user.setPassword(password);
            saveUser(user);
        }
        return "Password changed successfully!";
    }
}
