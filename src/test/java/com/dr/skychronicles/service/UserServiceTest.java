package com.dr.skychronicles.service;

import com.dr.skychronicles.entity.User;
import com.dr.skychronicles.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

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
        expectedUsers.add(new User("danial.rehman35@gmail.com", "1234", "username","USER", "profileImage"));
        expectedUsers.add(new User("daniel.rehman36@gmail.com", "2345", "username1","ADMIN", "profileImage"));

        when(userService.getAllUsers()).thenReturn(expectedUsers);
        List<User> actualUsers = userService.getAllUsers();

        assertEquals(expectedUsers.size(), actualUsers.size());
        assertEquals(expectedUsers, actualUsers);
    }

    @Test
    void getUserByEmail() {
        User expectedUser = new User("some@gmail.com", "2345", "username1","ADMIN", "profileImage");

        when(userService.getUserByEmail("some@gmail.com")).thenReturn(expectedUser);
        User actualuser = userService.getUserByEmail("some@gmail.com");

        assertEquals(expectedUser, actualuser);
        assertEquals(expectedUser.getEmail(), actualuser.getEmail());
    }

    @Test
    void signUpUser_ShouldReturnTrue_WhenUserIsNew() {
        User user = new User("some@gmail.com", "2345", "username1", null, "profileImage");

        // Пользователя с таким email не существует
        when(userRepository.getUserByEmail("some@gmail.com")).thenReturn(null);

        // Создаем UserServiceImpl с только userRepository (PasswordEncoder внутри метода)
        UserServiceImpl userServiceImpl = new UserServiceImpl(userRepository);

        // Act
        boolean result = userServiceImpl.signUpUser(user);

        // Assert
        assertTrue(result);
        assertEquals("USER", user.getRole());

        // Проверяем, что пароль действительно захэширован
        assertTrue(new BCryptPasswordEncoder().matches("2345", user.getPassword()));

        // Проверка, что пользователь был сохранён
        verify(userRepository).save(user);
    }

    @Test
    void signUpUser_ShouldReturnFalse_WhenUserAlreadyExists() {
        // Given
        User user = new User("some@gmail.com", "2345", "username1", null, "profileImage");

        when(userRepository.getUserByEmail("some@gmail.com")).thenReturn(new User());

        UserServiceImpl userService = new UserServiceImpl(userRepository);
        boolean result = userService.signUpUser(user);

        // Then
        assertFalse(result);
        verify(userRepository, never()).save(any());
    }


    @Test
    void saveUser() {
        User oldUser = new User("mail@example.com","old1234", "oldUsername","USER", "profileImage");
        User newUser = new User("mail@example.com","new1234", "newUsername", "USER","profileImage");

        MockMultipartFile imageFile = new MockMultipartFile(
                "imageFile",                 // Название параметра
                "profile.jpg",               // Имя файла
                "image/jpeg",                // Тип контента
                "Test Image Content".getBytes() // Контент изображения
        );

        when(userService.getUserByEmail("mail@example.com")).thenReturn(oldUser);
        when(authentication.getName()).thenReturn("mail@example.com");
        userService.saveUser(newUser, imageFile, authentication);

        assertEquals("mail@example.com", authentication.getName());
        assertEquals("new1234", newUser.getPassword());
        assertEquals("newUsername", newUser.getUsername());
    }

    @Test
    void simpleSaveUser() {
        User expectedUser = new User("some@gmail.com", "2345", "username1","USER","profileImage");

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