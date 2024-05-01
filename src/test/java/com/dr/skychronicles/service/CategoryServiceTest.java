package com.dr.skychronicles.service;

import com.dr.skychronicles.entity.Category;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CategoryServiceTest {

    @Mock
    private CategoryService categoryService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllCategories() {
        List<Category> expectedCategories = new ArrayList<>();
        expectedCategories.add(new Category());
        expectedCategories.add(new Category());

        when(categoryService.getAllCategories()).thenReturn(expectedCategories);
        List<Category> actualCategories = categoryService.getAllCategories();

        assertEquals(expectedCategories.size(), actualCategories.size());
        assertEquals(expectedCategories, actualCategories);
    }

    @Test
    void getCategoryById() {
        Category expectedCategory = new Category(3L, "Name3", "photo_url", new ArrayList<>());

        when(categoryService.getCategoryById(3L)).thenReturn(Optional.of(expectedCategory));
        Optional<Category> actualCategory = categoryService.getCategoryById(3L);

        assertEquals(Optional.of(expectedCategory), actualCategory);
    }

    @Test
    void saveCategory() {
        Category expectedCategory = new Category(3L, "Name3", "photo_url", new ArrayList<>());

        when(categoryService.saveCategory(expectedCategory)).thenReturn(expectedCategory);
        Category actualCategory = categoryService.saveCategory(expectedCategory);

        assertEquals(expectedCategory, actualCategory);
    }

    @Test
    void deleteCategoryById() {
        long categoryId = 2L;

        categoryService.deleteCategoryById(categoryId);

        verify(categoryService, times(1)).deleteCategoryById(categoryId);
    }
}