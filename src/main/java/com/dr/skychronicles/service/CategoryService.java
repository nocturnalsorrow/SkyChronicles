package com.dr.skychronicles.service;

import com.dr.skychronicles.entity.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryService {
    List<Category> getAllCategories();

    Optional<Category> getCategoryById(Long id);

    Category createCategory(Category category);

    Category updateCategory(Category category);

    void deleteCategoryById(Long id);
}
