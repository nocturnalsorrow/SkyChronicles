package com.dr.skychronicles.repository;

import com.dr.skychronicles.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;

class UserRepositoryTest {
    @Mock
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetUserByEmail() {
        User expectedUser = new User("test2@example.com", "pass124", "username2", "USER");

        when(userRepository.getUserByEmail("test2@example.com")).thenReturn(expectedUser);
        User actualUser = userRepository.getUserByEmail("test2@example.com");

        assertEquals(expectedUser, actualUser);
        assertEquals(expectedUser.getEmail(), actualUser.getEmail());
    }

    @Test
    void testDeleteUserByEmail() {
        User userToDelete = new User("test@example.com", "password", "username", "USER");
        userRepository.save(userToDelete);

        assertNotNull(userToDelete);

        userRepository.deleteUserByEmail("test@example.com");

        verify(userRepository).deleteUserByEmail("test@example.com");

        assertNull(userRepository.getUserByEmail("test@example.com"));
    }

}