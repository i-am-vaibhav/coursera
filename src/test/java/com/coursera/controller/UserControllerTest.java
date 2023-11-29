package com.coursera.controller;

import com.coursera.model.User;
import com.coursera.service.UserService;
import com.coursera.util.Role;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    private ArrayList<User> userList;
    private User mock;

    @BeforeEach
    void setUp() {
        this.userList = new ArrayList<>();
        this.userList.add(new User(BigDecimal.ONE, "user1","user1@gmail.com", "pwd1",Role.STUDENT));
        this.userList.add(new User(new BigDecimal(2), "user2","2@gmail.com", "pwd2",Role.STUDENT));
        this.userList.add(new User(new BigDecimal(3), "user3","3@gmail.com", "pwd3", Role.STUDENT));
    }

    @Test
    @WithMockUser(username = "admin", roles={"ADMIN"})
    void getUsersPage() throws Exception {
        BDDMockito.given(userService.getUsers()).willReturn(userList);
        MvcResult users = mockMvc.perform(MockMvcRequestBuilders.get(URI.create("/users")))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attributeExists("users"))
                .andReturn();
        Assertions.assertIterableEquals(userList, (Iterable<?>) users.getModelAndView().getModel().get("users"));
    }

    @Test
    @WithMockUser(username = "admin", roles={"ADMIN"})
    void getUserDetailsPage() throws Exception {
        mock = new User(BigDecimal.ONE, "user1", "user1@gmail.com", "pwd1", Role.STUDENT);
        BDDMockito.given(userService.getUser(Optional.ofNullable(BDDMockito.any()))).willReturn(mock);
        MvcResult users = mockMvc.perform(MockMvcRequestBuilders.get(URI.create("/users/1.0")))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attributeExists("user"))
                .andExpect(MockMvcResultMatchers.view().name("user/viewUser"))
                .andReturn();
        Assertions.assertEquals(mock, users.getModelAndView().getModel().get("user"));
    }

    @Test
    @WithMockUser(username = "admin", roles={"ADMIN"})
    void getUserDetailsPageException() throws Exception {
        BDDMockito.given(userService.getUser(Optional.ofNullable(BDDMockito.any()))).willThrow(UsernameNotFoundException.class);
        MvcResult users = mockMvc.perform(MockMvcRequestBuilders.get(URI.create("/users/2.0")))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("error"))
                .andReturn();
    }

    @Test
    @WithMockUser(username = "admin", roles={"ADMIN"})
    void createUserPage() throws Exception {
        MvcResult users = mockMvc.perform(MockMvcRequestBuilders.get(URI.create("/users/create")))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("user/user"))
                .andReturn();
    }

    @Test
    @WithMockUser(username = "admin", roles={"ADMIN"})
    void updateUserPage() throws Exception {
        mock = new User(BigDecimal.ONE, "user1", "user1@gmail.com", "pwd1", Role.STUDENT);
        BDDMockito.given(userService.getUser(Optional.ofNullable(BDDMockito.any()))).willReturn(mock);
        MvcResult users = mockMvc.perform(MockMvcRequestBuilders.get(URI.create("/users/1.0/update")))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("user/user"))
                .andReturn();
        Assertions.assertEquals(mock, users.getModelAndView().getModel().get("user"));
    }

    @Test
    @WithMockUser(username = "admin", roles={"ADMIN"})
    void updateUserPageExec() throws Exception {
        BDDMockito.given(userService.getUser(Optional.ofNullable(BDDMockito.any()))).willThrow(UsernameNotFoundException.class);
        MvcResult users = mockMvc.perform(MockMvcRequestBuilders.get(URI.create("/users/2.0/update")))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("error"))
                .andReturn();
    }

    @Test
    @WithMockUser(username = "admin", roles={"ADMIN"})
    void deleteUser() throws Exception {
        MvcResult users = mockMvc.perform(MockMvcRequestBuilders.get(URI.create("/users/1.0/delete")))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.view().name("redirect:/users"))
                .andReturn();
    }


    @WithMockUser(username = "admin",authorities = {"ROLE_ADMIN"})
    void saveUser() throws Exception {
        MvcResult users = mockMvc.perform(MockMvcRequestBuilders.post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{" +
                                "\"id\":\"\"," +
                                "\"userName\":\"Vaibhav\", \"email\":\"v@gmail.com\"," +
                                "\"role\":\"STUDENT\"" +
                                "}"))
                .andExpect(MockMvcResultMatchers.view().name("redirect:/users"))
                .andReturn();
    }
}