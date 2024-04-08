package com.dr.skychronicles.controller;

import com.dr.skychronicles.entity.Article;
import com.dr.skychronicles.entity.Category;
import com.dr.skychronicles.service.ArticleService;
import com.dr.skychronicles.service.CategoryService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
public class HomeController {
    private final ArticleService articleService;
    private final CategoryService categoryService;

    public HomeController(ArticleService articleService, CategoryService categoryService) {
        this.articleService = articleService;
        this.categoryService = categoryService;
    }

    @GetMapping("/search")
    public String searchArticles(@RequestParam(name = "keyword", required = false) String keyword,
                                 @RequestParam("page") Optional<Integer> page,
                                 @RequestParam("size") Optional<Integer> size,
                                 Model model) {
        try {
            int currentPage = page.orElse(1);
            int pageSize = size.orElse(8);

            if (keyword == null || keyword.isEmpty()) {
                return "redirect:/";
            } else {
                Page<Article> searchResultsPage = articleService.getArticlesByTitle(keyword, PageRequest.of(currentPage - 1, pageSize));

                int totalPages = searchResultsPage.getTotalPages();
                if (totalPages > 0) {
                    List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                            .boxed()
                            .collect(Collectors.toList());
                    model.addAttribute("pageNumbers", pageNumbers);
                }

                model.addAttribute("keyword", keyword);
                model.addAttribute("searchResults", searchResultsPage);
                model.addAttribute("categories", categoryService.getAllCategories());
                model.addAttribute("recentArticles", articleService.getArticlesSortedByDate());
                return "searchResults";
            }
        } catch (Exception e) {
            //Error logging or other actions for exception handling
            return "error";
        }
    }

    @GetMapping("/")
    public String getHomePage(@RequestParam("page") Optional<Integer> page,
                              @RequestParam("size") Optional<Integer> size,
                              Model model) {
        try {
            int currentPage = page.orElse(1);
            int pageSize = size.orElse(8);

            List<Category> allCategories = categoryService.getAllCategories();
            Long defaultCategoryId = (!allCategories.isEmpty()) ? allCategories.getFirst().getCategoryId() : null;

            Page<Article> categoryArticlePage = articleService.getArticlesByCategoryId(defaultCategoryId, PageRequest.of(currentPage - 1, pageSize));

            int totalPages = categoryArticlePage.getTotalPages();
            if (totalPages > 0) {
                List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                        .boxed()
                        .collect(Collectors.toList());
                model.addAttribute("pageNumbers", pageNumbers);
            }

            Optional<Category> categoryOptional = categoryService.getCategoryById(defaultCategoryId);
            String categoryName = categoryOptional.map(Category::getName).orElse("Unknown Category");

            model.addAttribute("categories", allCategories);
            model.addAttribute("categoryName", categoryName);
            model.addAttribute("categoryArticles", categoryArticlePage);
            model.addAttribute("selectedCategoryId", defaultCategoryId);
            model.addAttribute("recentArticles", articleService.getArticlesSortedByDate());

            return "index";
        } catch (Exception e) {
            return "error";
        }
    }

    @GetMapping("/category/{categoryId}")
    public String getCategoryArticles(@PathVariable Long categoryId,
                                      @RequestParam("page") Optional<Integer> page,
                                      @RequestParam("size") Optional<Integer> size,
                                      Model model) {
        try {
            int currentPage = page.orElse(1);
            int pageSize = size.orElse(8);

            Page<Article> categoryArticlesPage = articleService.getArticlesByCategoryId(categoryId, PageRequest.of(currentPage - 1, pageSize));

            int totalPages = categoryArticlesPage.getTotalPages();
            if (totalPages > 0) {
                List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                        .boxed()
                        .collect(Collectors.toList());
                model.addAttribute("pageNumbers", pageNumbers);
            }

            // Получение объекта Category по categoryId
            Optional<Category> categoryOptional = categoryService.getCategoryById(categoryId);
            String categoryName = categoryOptional.map(Category::getName).orElse("Unknown Category"); // Если категория не найдена, можно использовать какое-то стандартное значение

            model.addAttribute("categories", categoryService.getAllCategories());
            model.addAttribute("categoryName", categoryName);
            model.addAttribute("categoryArticles", categoryArticlesPage);
            model.addAttribute("selectedCategoryId", categoryId);
            model.addAttribute("recentArticles", articleService.getArticlesSortedByDate());

            return "index";
        } catch (Exception e) {
            return "error";
        }
    }
}

