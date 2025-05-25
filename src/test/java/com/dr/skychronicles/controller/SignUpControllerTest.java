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
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class SignUpControllerTest {

    @Mock
    private UserService userService;

    @Mock
    private CategoryService categoryService;

    @InjectMocks
    private SignUpController signUpController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        //        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(signUpController).build();
    }

    @Test
    void signUpForm() throws Exception {
        // Given
        List<Category> categories = new ArrayList<>();
        categories.add(new Category());
        categories.add(new Category());

        when(categoryService.getAllCategories()).thenReturn(categories);

        // When and Then
        mockMvc.perform(get("/signUp"))
                .andExpect(status().isOk())
                .andExpect(view().name("signUpPage"))
                .andExpect(model().attributeExists("user"))
                .andExpect(model().attributeExists("categories"))
                .andExpect(model().attribute("categories", categories));

        verify(categoryService, times(1)).getAllCategories();
    }

    @Test
    void signUpSubmit() throws Exception {
        // Given
        User user = new User();
        user.setEmail("test@example.com");
        user.setPassword("securePassword");

        when(userService.signUpUser(any(User.class))).thenReturn(true);

        // When and Then
        mockMvc.perform(post("/signUp").flashAttr("user", user))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login"));

        verify(userService, times(1)).signUpUser(any(User.class));
    }

    @Test
    void signUpSubmit_UserAlreadyExists() throws Exception {
        User user = new User();
        user.setEmail("existing@example.com");
        user.setPassword("pass");

        when(userService.signUpUser(any(User.class))).thenReturn(false);

        mockMvc.perform(post("/signUp").flashAttr("user", user))
                .andExpect(status().isOk()) // 200, because no redirect
                .andExpect(view().name("signUpPage"));

        verify(userService, times(1)).signUpUser(any(User.class));
    }
}