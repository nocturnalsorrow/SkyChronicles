package com.dr.skychronicles.controller;

import com.dr.skychronicles.entity.User;
import com.dr.skychronicles.service.CategoryService;
import com.dr.skychronicles.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class SignUpController {
    private final UserService userService;
    private final CategoryService  categoryService;

    public SignUpController(UserService userService, CategoryService categoryService) {
        this.userService = userService;
        this.categoryService = categoryService;
    }

    @GetMapping("/signUp")
    public String signUpForm(Model model){
        model.addAttribute("user", new User());
        model.addAttribute("categories", categoryService.getAllCategories());
        return "signUpPage";
    }

    @PostMapping("/signUp")
    public String signUpSubmit(@ModelAttribute User user){
        userService.signUpUser(user);

        return "redirect:/login";
    }
}
