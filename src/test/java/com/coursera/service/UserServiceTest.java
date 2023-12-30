package com.coursera.service;

import com.coursera.model.User;
import com.coursera.repository.UserRepository;
import com.coursera.util.Role;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    void getUsers() {
        List<User> users = userService.getUsers();
        assertNotNull(users);
    }

    @Test
    void getUsersWithData() {
        List<User> expected = new ArrayList<>();
        expected.add(new User());
        when(userRepository.findAll()).thenReturn(expected);
        List<User> users = userService.getUsers();
        assertIterableEquals(expected,users);
    }

    @Test
    void getUser() {
        Optional<User> userOptional = Optional.of(new User(BigDecimal.ONE,"Vaibhav","V@gmail.com","Vtest", Role.STUDENT,false));
        when(userRepository.findById(Mockito.any())).thenReturn(userOptional);
        User user = userService.getUser(Optional.of(BigDecimal.ONE));
        assertNotNull(user);
        assertEquals(user.getId(),userOptional.get().getId());
    }

    @Test
    void getUserWithIllegalArgEx() {
        assertThrows(IllegalArgumentException.class , () -> userService.getUser(Optional.empty()));
    }

    @Test
    void getUserWithUserNotFoundExe() {
        assertThrows(UsernameNotFoundException.class , () -> userService.getUser(Optional.of(BigDecimal.ONE)));
    }

    @Test
    void saveUser() {
        User mockUser = new User(BigDecimal.ONE,"Vaibhav","V@gmail.com","Vtest", Role.STUDENT,false);
        when(userRepository.save(Mockito.any(User.class))).thenReturn(mockUser);
        User user = new User(null,"Vaibhav","V@gmail.com","Vtest", Role.STUDENT, false);
        User user1 = userService.saveUser(user);
        assertNotNull(user1.getId());
    }

    @Test
    void saveUserUpdate() {
        User mockUser = new User(BigDecimal.ONE,"Vaibhav","V@gmail.com","Vtest", Role.STUDENT, false);
        when(userRepository.save(Mockito.any(User.class))).thenReturn(mockUser);
        User user = new User(BigDecimal.ONE,"Vaibhav","V@gmail.com","test", Role.STUDENT,false);
        User user1 = userService.saveUser(user);
        assertNotEquals(user1.getPassword(),user.getPassword());
    }

    @Test
    void getUserWithMockedData() {
        Optional<User> userOptional = Optional.of(new User(BigDecimal.ONE,"Vaibhav","V@gmail.com","Vtest", Role.STUDENT, false));
        when(userRepository.findByUserName(Mockito.any())).thenReturn(userOptional);
        User user = userService.getUser("Vaibhav");
        assertNotNull(user);
        assertEquals(user.getUserName(),userOptional.get().getUserName());
    }

    @Test
    void getUserWithEmpty() {
        assertThrows(UsernameNotFoundException.class,() -> userService.getUser(""));
    }
    @Test
    void getUserWithNull() {
        assertThrows(UsernameNotFoundException.class,() -> userService.getUser((String) null));
    }
    @Test
    void deleteUser() {
        assertDoesNotThrow(() -> userService.deleteUser(Optional.of(BigDecimal.ONE)));
    }

    @Test
    void deleteUserExceptionScenario() {
        assertThrows(IllegalArgumentException.class,() -> userService.deleteUser(Optional.empty()));
    }



}