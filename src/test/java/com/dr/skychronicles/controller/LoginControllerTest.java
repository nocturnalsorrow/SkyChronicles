package com.dr.skychronicles.controller;

import com.dr.skychronicles.entity.Category;
import com.dr.skychronicles.service.CategoryService;
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

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class LoginControllerTest {

    @Mock
    private CategoryService categoryService;

    @InjectMocks
    private LoginController loginController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        //        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(loginController).build();

    }

    @Test
    void login() throws Exception {
        // Given
        List<Category> categories = new ArrayList<>();
        categories.add(new Category());
        categories.add(new Category());

        when(categoryService.getAllCategories()).thenReturn(categories);

        // When and Then
        mockMvc.perform(get("/login"))
                .andExpect(status().isOk())
                .andExpect(view().name("loginPage"))
                .andExpect(model().attributeExists("categories"))
                .andExpect(model().attribute("categories", categories));

        verify(categoryService, times(1)).getAllCategories();
    }

    @Test
    void loginWithError() throws Exception {
        // Given
        List<Category> categories = new ArrayList<>();
        categories.add(new Category());
        categories.add(new Category());

        when(categoryService.getAllCategories()).thenReturn(categories);

        // When and Then
        mockMvc.perform(get("/login").param("error", "true"))
                .andExpect(status().isOk())
                .andExpect(view().name("loginPage"))
                .andExpect(model().attributeExists("categories"))
                .andExpect(model().attribute("categories", categories))
                .andExpect(model().attributeExists("error"))
                .andExpect(model().attribute("error", "Invalid email or password. Please try again."));

        verify(categoryService, times(1)).getAllCategories();
    }
}