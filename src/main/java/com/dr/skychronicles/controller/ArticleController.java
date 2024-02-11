package com.dr.skychronicles.controller;

import com.dr.skychronicles.entity.Article;
import com.dr.skychronicles.entity.Gallery;
import com.dr.skychronicles.service.ArticleService;
import com.dr.skychronicles.service.CategoryService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;
import java.util.List;
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

    @GetMapping("/{articleId}/image/{imageId}")
    public ResponseEntity<byte[]> getArticleImage(@PathVariable Long articleId, @PathVariable Long imageId) {
        Optional<Gallery> articleImageOptional = articleService.getArticleImage(articleId, imageId);

        if (articleImageOptional.isPresent()) {
            Gallery articleImage = articleImageOptional.get();
            byte[] imageBytes = Base64.getDecoder().decode(articleImage.getImage());
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_JPEG);
            return new ResponseEntity<>(imageBytes, headers, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new byte[0], HttpStatus.NOT_FOUND);
        }
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
    public String createArticle(Model model) {
        model.addAttribute("article", new Article());
        model.addAttribute("categories", categoryService.getAllCategories());

        return "createArticle";
    }

    @PostMapping("/article")
    public String createArticle(@ModelAttribute Article article, @RequestParam("imageFiles") List<MultipartFile> imageFiles) throws IOException {
        articleService.createArticle(article, imageFiles);

        return "redirect:/articles";
    }

    @GetMapping("/article/delete/{articleId}")
    public String deleteArticle(@PathVariable Long articleId) {
        articleService.deleteArticleById(articleId);

        return "redirect:/articles";
    }
}
