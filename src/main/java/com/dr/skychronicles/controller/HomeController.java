package com.dr.skychronicles.controller;

import com.dr.skychronicles.entity.Article;
import com.dr.skychronicles.entity.Category;
import com.dr.skychronicles.service.ArticleService;
import com.dr.skychronicles.service.CategoryService;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class HomeController {
    private final ArticleService articleService;
    private final CategoryService categoryService;

    public HomeController(ArticleService articleService, CategoryService categoryService) {
        this.articleService = articleService;
        this.categoryService = categoryService;
    }

    @GetMapping("/search")
    public String searchArticles(@RequestParam(name = "keyword", required = false) String keyword, Model model) {
        try {
            if (keyword == null || keyword.isEmpty()) {
                return "redirect:/";
            } else {
                List<Article> searchResults = articleService.getArticlesByTitle(keyword);
                model.addAttribute("searchResults", searchResults);
                return "searchResults";
            }
        } catch (Exception e) {
            //Error logging or other actions for exception handling
            return "error";
        }
    }

    @GetMapping("/")
    public String getHomePage(@RequestParam int page, @RequestParam int size, Model model) {
        try {
            List<Category> allCategories = categoryService.getAllCategories();
            Long defaultCategoryId = (!allCategories.isEmpty()) ? allCategories.getFirst().getCategoryId() : null;

            PageRequest pageRequest = PageRequest.of(page, size);

            List<Article> categoryArticles = articleService.getArticlesByCategoryId(defaultCategoryId, pageRequest);

            model.addAttribute("categories", allCategories);
            model.addAttribute("categoryArticles", categoryArticles);
            model.addAttribute("selectedCategoryId", defaultCategoryId);
            model.addAttribute("recentArticles", articleService.getArticlesSortedByDate());

            return "index";
        } catch (Exception e) {
            //Error logging or other actions for exception handling
            return "error";
        }
    }

    @GetMapping("/category/{categoryId}")
    public String getCategoryArticles(@PathVariable Long categoryId, Model model) {
        try {
            List<Article> categoryArticles = articleService.getArticlesByCategoryId(categoryId);

            model.addAttribute("categories", categoryService.getAllCategories());
            model.addAttribute("categoryArticles", categoryArticles);
            model.addAttribute("selectedCategoryId", categoryId);
            model.addAttribute("recentArticles", articleService.getArticlesSortedByDate());

            return "index";
        } catch (Exception e) {
            //Error logging or other actions for exception handling
            return "error";
        }
    }
}

