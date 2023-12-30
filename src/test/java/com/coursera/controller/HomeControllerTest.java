package com.coursera.controller;

import com.coursera.model.User;
import com.coursera.repository.UserRepository;
import com.coursera.service.UserService;
import com.coursera.util.Role;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@WebMvcTest(HomeController.class)
class HomeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;
    
    @MockBean
    private UserRepository userRepository;

    @Test
    @WithAnonymousUser
    void loginPage() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/login"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                        .andReturn();
    }

    @Test
    @WithMockUser(username = "Vaibhav",roles = {"ADMIN"})
    void homePage() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/home"))
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
    }

    @Test
    @WithMockUser(username = "Vaibhav",password = "Admin@123",authorities = {"ROLE_ADMIN"})
    void profilePage() throws Exception {
        BDDMockito.given(userService.getUser(BDDMockito.anyString())).willReturn(
                new User(BigDecimal.ONE,"Vaibhav","v@gmail.com","pwd", Role.STUDENT,false)
        );
        MvcResult user = mockMvc.perform(MockMvcRequestBuilders.get("/profile"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attributeExists("user"))
                .andExpect(MockMvcResultMatchers.model().hasNoErrors())
                .andReturn();
    }
}