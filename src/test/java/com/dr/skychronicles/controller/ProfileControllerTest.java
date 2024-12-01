package com.dr.skychronicles.controller;

import com.dr.skychronicles.entity.Category;
import com.dr.skychronicles.entity.User;
import com.dr.skychronicles.service.CategoryService;
import com.dr.skychronicles.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.core.Authentication;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class ProfileControllerTest {

    @Mock
    private UserService userService;

    @Mock
    private CategoryService categoryService;

    @InjectMocks
    private ProfileController profileController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        //        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(profileController).build();
    }

    @Test
    void getProfile() throws Exception {
        // Given
        Authentication authentication = mock(Authentication.class);
        User user = new User();
        List<Category> categories = new ArrayList<>();
        categories.add(new Category());
        categories.add(new Category());

        when(userService.getUserByEmail(authentication.getName())).thenReturn(user);
        when(categoryService.getAllCategories()).thenReturn(categories);

        // When and Then
        mockMvc.perform(get("/profile").principal(authentication))
                .andExpect(status().isOk())
                .andExpect(view().name("userProfile"))
                .andExpect(model().attributeExists("user"))
                .andExpect(model().attribute("user", user))
                .andExpect(model().attributeExists("categories"))
                .andExpect(model().attribute("categories", categories));

        verify(userService, times(1)).getUserByEmail(authentication.getName());
        verify(categoryService, times(1)).getAllCategories();
    }

    @Test
    void edit() throws Exception {
        // Given
        Authentication authentication = mock(Authentication.class);
        User updatedUser = new User();
        MockMultipartFile imageFile = new MockMultipartFile(
                "imageFile",
                "profile.jpg",
                "image/jpeg",
                "Test Image Content".getBytes()
        );

        when(authentication.getName()).thenReturn("user@example.com");

        // When and Then
        mockMvc.perform(multipart("/profile/edit")
                        .file(imageFile)
                        .flashAttr("updatedUser", updatedUser)
                        .principal(authentication))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/profile"));

        verify(userService, times(1)).saveUser(updatedUser, imageFile, authentication);
    }
}