package com.coursera.security;

import com.coursera.model.User;
import com.coursera.util.Role;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class AuthenticatedUserTest {
    @Test
    void getUserTest() {
        User user=new User(BigDecimal.ONE,"Name","name@gmail.com","test", Role.STUDENT,false);
        AuthenticatedUser authenticatedUser = new AuthenticatedUser(user);
        assertNotNull(authenticatedUser.getUser());
    }
}