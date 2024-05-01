package com.dr.skychronicles.service;

import com.dr.skychronicles.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {
    @Mock
    private UserService userService;

    @Mock
    private Authentication authentication;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllUsers() {
        List<User> expectedUsers = new ArrayList<>();
        expectedUsers.add(new User("danial.rehman35@gmail.com", "1234", "username","USER"));
        expectedUsers.add(new User("daniel.rehman36@gmail.com", "2345", "username1","ADMIN"));

        when(userService.getAllUsers()).thenReturn(expectedUsers);
        List<User> actualUsers = userService.getAllUsers();

        assertEquals(expectedUsers.size(), actualUsers.size());
        assertEquals(expectedUsers, actualUsers);
    }

    @Test
    void getUserByEmail() {
        User expectedUser = new User("some@gmail.com", "2345", "username1","ADMIN");

        when(userService.getUserByEmail("some@gmail.com")).thenReturn(expectedUser);
        User actualuser = userService.getUserByEmail("some@gmail.com");

        assertEquals(expectedUser, actualuser);
        assertEquals(expectedUser.getEmail(), actualuser.getEmail());
    }

    @Test
    void signUpUser() {
        User user = new User("some@gmail.com", "2345", "username1","USER");

        when(userService.signUpUser(user)).thenReturn(user);
        User signedUpUser = userService.signUpUser(user);

        assertEquals("USER", signedUpUser.getRole());
        assertTrue(new BCryptPasswordEncoder().matches(signedUpUser.getPassword(), "$2a$12$kaypgTAzmN5KDnJDwDnqm.Hur/WKfkFhGUFWVouyQ/m5f4LHvrWv6"));
        verify(userService).signUpUser(user);
    }

    @Test
    void saveUser() {
        User oldUser = new User("mail@example.com","old1234", "oldUsername","USER");
        User newUser = new User("mail@example.com","new1234", "newUsername", "USER");

        when(userService.getUserByEmail("mail@example.com")).thenReturn(oldUser);
        when(authentication.getName()).thenReturn("mail@example.com");
        userService.saveUser(newUser, authentication);

        assertEquals("mail@example.com", authentication.getName());
        assertEquals("new1234", newUser.getPassword());
        assertEquals("newUsername", newUser.getUsername());
    }

    @Test
    void simpleSaveUser() {
        User expectedUser = new User("some@gmail.com", "2345", "username1","USER");

        when(userService.saveUser(expectedUser)).thenReturn(expectedUser);
        User actualUser = userService.saveUser(expectedUser);

        assertNotNull(actualUser);
        assertEquals(expectedUser, actualUser);
        verify(userService, times(1)).saveUser(expectedUser);
    }

    @Test
    void deleteUserByEmail() {
        String userEmail = "some@gmail.com";

        userService.deleteUserByEmail(userEmail);

        verify(userService, times(1)).deleteUserByEmail(userEmail);
    }
}