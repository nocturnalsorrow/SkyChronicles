package com.dr.skychronicles.controller;

import com.dr.skychronicles.entity.Article;
import com.dr.skychronicles.service.ArticleService;
import com.dr.skychronicles.service.CategoryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Optional;

@Controller
public class ArticleController {
    private final ArticleService articleService;
    private final CategoryService categoryService;

    public ArticleController(ArticleService articleService, CategoryService categoryService) {
        this.articleService = articleService;
        this.categoryService = categoryService;
    }

    @GetMapping("/article/{articleId}")
    public String getArticlePage(@PathVariable Long articleId, Model model) {
        Optional<Article> optionalArticle = articleService.getArticleById(articleId);

        if (optionalArticle.isPresent()) {
            model.addAttribute("article", optionalArticle.get());
        } else {
            model.addAttribute("article", null);
        }
        model.addAttribute("recentArticles", articleService.getArticlesSortedByDate());
        return "article";
    }

    @GetMapping("/articles")
    public String getAllArticles(Model model) {
        model.addAttribute("articles", articleService.getArticlesSortedByDate());

        return "articles";
    }

    @GetMapping("/article/update/{articleId}")
    public String getArticleToUpdate(@PathVariable Long articleId, Model model) {
        Optional<Article> optionalArticle = articleService.getArticleById(articleId);
        if (optionalArticle.isPresent()) {
            model.addAttribute("article", optionalArticle.get());
        } else {
            model.addAttribute("article", null);
        }
        return "updateArticle";
    }

    @PostMapping("/article/update")
    public String updateArticle(@ModelAttribute Article updatedArticle) {
        articleService.updateArticle(updatedArticle);

        return "redirect:/articles";
    }

    @GetMapping ("/article")
    public String getArticleToCreate(Model model) {
        model.addAttribute("article", new Article());
        model.addAttribute("categories", categoryService.getAllCategories());

        return "createArticle";
    }

    @PostMapping("/article")
    public String createArticle(@ModelAttribute Article article) {
        articleService.createArticle(article);

        return "redirect:/articles";
    }

    @GetMapping("/article/delete/{articleId}")
    public String deleteArticle(@PathVariable Long articleId) {
        articleService.deleteArticleById(articleId);

        return "redirect:/articles";
    }
}
