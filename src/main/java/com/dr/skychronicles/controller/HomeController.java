package com.dr.skychronicles.controller;

import com.dr.skychronicles.entity.Article;
import com.dr.skychronicles.entity.Category;
import com.dr.skychronicles.service.ArticleService;
import com.dr.skychronicles.service.CategoryService;
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
        if (keyword != null && !keyword.isEmpty()) {
            List<Article> searchResults = articleService.getArticlesByTitle(keyword);
            model.addAttribute("searchResults", searchResults);
        } else {
            // Если поисковой запрос пустой или отсутствует, можно предпринять действие по умолчанию или показать сообщение
            // Например, можно перенаправить пользователя на страницу со всеми статьями.
            return "redirect:/index";
        }
        return "searchResults"; // Возвращаем имя HTML шаблона для отображения результатов
    }

    @GetMapping("/")
    public String getDefaultCategoryArticles(Model model) {
        // Получите первую категорию (или проверьте, есть ли категории)
        List<Category> allCategories = categoryService.getAllCategories();
        Long defaultCategoryId = null;

        if (!allCategories.isEmpty()) {
            defaultCategoryId = allCategories.get(0).getCategoryId();
        }

        // Получите статьи для выбранной категории (по умолчанию первая категория)
        List<Article> categoryArticles = articleService.getArticlesByCategoryId(defaultCategoryId);

        // Передайте данные в модель
        model.addAttribute("categories", allCategories);
        model.addAttribute("categoryArticles", categoryArticles);
        model.addAttribute("selectedCategoryId", defaultCategoryId);
        model.addAttribute("recentArticles", articleService.getArticlesSortedByDate());

        return "index";
    }

    @GetMapping("/category/{categoryId}")
    public String getCategoryArticles(@PathVariable Long categoryId, Model model) {
        // Получите статьи для выбранной категории
        List<Article> categoryArticles = articleService.getArticlesByCategoryId(categoryId);

        // Передайте данные в модель
        model.addAttribute("categories", categoryService.getAllCategories());
        model.addAttribute("categoryArticles", categoryArticles);
        model.addAttribute("selectedCategoryId", categoryId);
        model.addAttribute("recentArticles", articleService.getArticlesSortedByDate());

        return "index";
    }
}
