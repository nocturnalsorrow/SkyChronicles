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

class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserServiceImpl userServiceImpl;

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

        when(userServiceImpl.getAllUsers()).thenReturn(expectedUsers);
        List<User> actualUsers = userServiceImpl.getAllUsers();

        assertEquals(expectedUsers.size(), actualUsers.size());
        assertEquals(expectedUsers, actualUsers);
    }

    @Test
    void getUserByEmail() {
        User expectedUser = new User("some@gmail.com", "2345", "username1","ADMIN", "profileImage");

        when(userServiceImpl.getUserByEmail("some@gmail.com")).thenReturn(expectedUser);
        User actualuser = userServiceImpl.getUserByEmail("some@gmail.com");

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
    void saveUser() {
        User oldUser = new User("mail@example.com","old1234", "oldUsername","USER", "profileImage");
        User newUser = new User("mail@example.com","new1234", "newUsername", "USER", "profileImage");
        MockMultipartFile imageFile = new MockMultipartFile(
                "imageFile",                 // Название параметра
                "profile.jpg",               // Имя файла
                "image/jpeg",                // Тип контента
                "Test Image Content".getBytes() // Контент изображения
        );

        when(userServiceImpl.getUserByEmail("mail@example.com")).thenReturn(oldUser);
        when(authentication.getName()).thenReturn("mail@example.com");
        userServiceImpl.saveUser(newUser, imageFile, authentication);

        assertEquals("mail@example.com", authentication.getName());
        assertEquals("new1234", newUser.getPassword());
        assertEquals("newUsername", newUser.getUsername());
    }

    @Test
    void simpleSaveUser() {
        User expectedUser = new User("some@gmail.com", "2345", "username1","USER", "profileImage");

        when(userServiceImpl.saveUser(expectedUser)).thenReturn(expectedUser);
        User actualUser = userServiceImpl.saveUser(expectedUser);

        assertNotNull(actualUser);
        assertEquals(expectedUser, actualUser);
        verify(userServiceImpl, times(1)).saveUser(expectedUser);
    }

    @Test
    void deleteUserByEmail() {
        String userEmail = "some@gmail.com";

        userServiceImpl.deleteUserByEmail(userEmail);

        verify(userServiceImpl, times(1)).deleteUserByEmail(userEmail);
    }
}