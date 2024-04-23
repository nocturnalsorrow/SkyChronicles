package com.dr.skychronicles.controller;

import com.dr.skychronicles.service.CategoryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {
    private final CategoryService categoryService;

    public LoginController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/login")
    public String login(@RequestParam(required = false) String error, Model model) {
        if (error != null) {
            model.addAttribute("error", "Invalid email or password. Please try again.");
        }
        model.addAttribute("categories", categoryService.getAllCategories());
        return "login";
    }
}
