package com.dr.skychronicles.controller;

import com.dr.skychronicles.entity.User;
import com.dr.skychronicles.service.CategoryService;
import com.dr.skychronicles.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class ProfileController {
    private  final UserService userService;
    private final CategoryService categoryService;

    public ProfileController(UserService userService, CategoryService categoryService) {
        this.userService = userService;
        this.categoryService = categoryService;
    }

    @GetMapping("/profile")
    public String getProfile(Authentication authentication, Model model) {
        model.addAttribute("user", userService.getUserByEmail(authentication.getName()));
        model.addAttribute("categories", categoryService.getAllCategories());

        return "userProfile";
    }

    @PostMapping("/profile/edit")
    public String edit(@ModelAttribute User updatedUser, MultipartFile imageFile, Authentication authentication) {
        userService.saveUser(updatedUser, imageFile, authentication);

        return "redirect:/profile";
    }
}
