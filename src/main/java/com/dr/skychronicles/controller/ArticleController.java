package com.dr.skychronicles.controller;

import com.dr.skychronicles.entity.Article;
import com.dr.skychronicles.service.ArticleService;
import com.dr.skychronicles.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

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
    public String getArticle(@PathVariable Long articleId, Model model) {
        Optional<Article> optionalArticle = articleService.getArticleById(articleId);

        if (optionalArticle.isPresent()) {
            model.addAttribute("article", optionalArticle.get());
        } else {
            model.addAttribute("article", null);
        }
        model.addAttribute("recentArticles", articleService.getArticlesSortedByDate());
        return "article";
    }

}
