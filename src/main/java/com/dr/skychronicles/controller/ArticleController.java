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

    //Retrieve and display the details of a specific article.
    @GetMapping("/article/{articleId}")
    public String getArticlePage(@PathVariable Long articleId, Model model) {
        try {
            articleService.getArticleById(articleId)
                    .ifPresent(article -> model.addAttribute("article", article));

            model.addAttribute("recentArticles", articleService.getArticlesSortedByDate());
            return "article";
        } catch (Exception e) {
            //Error logging or other actions for exception handling
            return "error";
        }
    }

    //Retrieve and serve the image associated with a specific article.
    @GetMapping("/{articleId}/image/{imageId}")
    public ResponseEntity<byte[]> getArticleImage(@PathVariable Long articleId, @PathVariable Long imageId) {
        try {
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
        } catch (Exception e) {
            //Error logging or other actions for exception handling
            return new ResponseEntity<>(new byte[0], HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //Retrieve and display a list of all articles, sorted by date.
    @GetMapping("/articles")
    public String getAllArticles(Model model) {
        model.addAttribute("articles", articleService.getArticlesSortedByDate());

        return "articles";
    }

    //Display the form for creating a new article with an empty model and available categories.
    @GetMapping("/article")
    public String createArticle(Model model) {
        model.addAttribute("article", new Article());
        model.addAttribute("categories", categoryService.getAllCategories());

        return "createArticle";
    }

    //Create a new article with the provided data and images, then redirect to the articles listing page.
    @PostMapping("/article")
    public String createArticle(@ModelAttribute Article article, @RequestParam("imageFiles") List<MultipartFile> imageFiles) {
        try {
            articleService.saveArticle(article, imageFiles);
            return "redirect:/articles";
        } catch (IOException e) {
            //Error logging or other actions for exception handling
            return "error"; // Вернуть страницу ошибки
        }
    }

    //Retrieve the details of a specific article for updating and display the update form.
    @GetMapping("/article/update/{articleId}")
    public String getArticleToUpdate(@PathVariable Long articleId, Model model) {
        articleService.getArticleById(articleId)
                .ifPresent(article -> model.addAttribute("article", article));

        return "updateArticle";
    }

    //Update an existing article with the provided data and images, then redirect to the articles listing page.
    @PostMapping("/article/update")
    public String updateArticle(@ModelAttribute Article updatedArticle, @RequestParam("imageFiles") List<MultipartFile> imageFiles) {
        try {
            articleService.saveArticle(updatedArticle, imageFiles);
            return "redirect:/articles";
        } catch (IOException e) {
            //Error logging or other actions for exception handling
            return "error"; // Вернуть страницу ошибки
        }
    }

    //Delete a specific article by its ID and redirect to the articles listing page.
    @GetMapping("/article/delete/{articleId}")
    public String deleteArticle(@PathVariable Long articleId) {
        articleService.deleteArticleById(articleId);

        return "redirect:/articles";
    }
}
