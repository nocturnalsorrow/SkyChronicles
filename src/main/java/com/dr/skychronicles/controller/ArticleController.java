package com.dr.skychronicles.controller;

import com.dr.skychronicles.entity.Article;
import com.dr.skychronicles.entity.Gallery;
import com.dr.skychronicles.exception.ArticleNotFoundException;
import com.dr.skychronicles.service.ArticleService;
import com.dr.skychronicles.service.CategoryService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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
        Optional<Article> optionalArticle = articleService.getArticleById(articleId);
        if (optionalArticle.isPresent()) {
            Article article = optionalArticle.get();
            model.addAttribute("article", article);
            model.addAttribute("categories", categoryService.getAllCategories());
            model.addAttribute("recentArticles", articleService.getArticlesSortedByDate());
            return "article";
        } else {
            throw new ArticleNotFoundException("Article with id " + articleId + " not found");
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
            return new ResponseEntity<>(new byte[0], HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //Retrieve and display a list of all articles, sorted by date.
    @GetMapping("/articles")
    public String getAllArticles(@RequestParam("page") Optional<Integer> page,
                                 @RequestParam("size") Optional<Integer> size,
                                 Model model) {
        try {
            int currentPage = page.orElse(1);
            int pageSize = size.orElse(7);

            Page<Article> articlesSortedByDate = articleService.getArticlesSortedByDate(PageRequest.of(currentPage - 1, pageSize));

            int totalPages = articlesSortedByDate.getTotalPages();
            if (totalPages > 0) {
                List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                        .boxed()
                        .collect(Collectors.toList());
                model.addAttribute("pageNumbers", pageNumbers);
            }

            model.addAttribute("categories", categoryService.getAllCategories());
            model.addAttribute("articles", articlesSortedByDate);

            return "articles";
        } catch (Exception ex) {
            return "error";
        }
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
            return "error";
        }
    }

    //Retrieve the details of a specific article for updating and display the update form.
    @GetMapping("/article/update/{articleId}")
    public String getArticleToUpdate(@PathVariable Long articleId, Model model) {
        Optional<Article> optionalArticle = articleService.getArticleById(articleId);
        if (optionalArticle.isPresent()) {
            Article article = optionalArticle.get();
            model.addAttribute("article", article);
            model.addAttribute("categories", categoryService.getAllCategories());
            return "updateArticle";
        } else {
            throw new ArticleNotFoundException("Article with id " + articleId + " not found");
        }
    }

    //Update an existing article with the provided data and images, then redirect to the articles listing page.
    @PostMapping("/article/update")
    public String updateArticle(@ModelAttribute Article updatedArticle, @RequestParam("imageFiles") List<MultipartFile> imageFiles) {
        try {
            articleService.saveArticle(updatedArticle, imageFiles);
            return "redirect:/articles";
        } catch (IOException e) {
            return "error";
        }
    }

    //Delete a specific article by its ID and redirect to the articles listing page.
    @GetMapping("/article/delete/{articleId}")
    public String deleteArticle(@PathVariable Long articleId) {
        articleService.deleteArticleById(articleId);

        return "redirect:/articles";
    }
}
