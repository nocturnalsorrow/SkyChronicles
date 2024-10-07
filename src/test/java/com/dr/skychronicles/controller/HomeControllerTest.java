package com.dr.skychronicles.controller;

import com.dr.skychronicles.entity.Article;
import com.dr.skychronicles.entity.Category;
import com.dr.skychronicles.service.ArticleService;
import com.dr.skychronicles.service.CategoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class HomeControllerTest {
    @Mock
    private ArticleService articleService;
    @Mock
    private CategoryService categoryService;

    @InjectMocks
    private HomeController homeController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        //        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(homeController).build();
    }

    @Test
    void searchArticles() throws Exception {
        List<Article> articles = new ArrayList<>();
        articles.add(new Article(1L, "Title", "Content", Date.valueOf("2002-04-13"), new Category(), new ArrayList<>()));
        articles.add(new Article(2L, "Title 2", "Content 2", Date.valueOf("2002-04-14"), new Category(), new ArrayList<>()));

        // Создаем объект Page
        Page<Article> page = new PageImpl<>(articles, PageRequest.of(0, 8), articles.size());

        when(articleService.getArticlesByTitle("title", PageRequest.of(0, 8))).thenReturn(page);

        mockMvc.perform(get("/search")
                        .param("keyword", "title")
                        .param("page", "1")
                        .param("size", "8"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("keyword"))
                .andExpect(model().attribute("keyword", "title"))
                .andExpect(model().attributeExists("searchResults"))
                .andExpect(model().attributeExists("categories"))
                .andExpect(model().attributeExists("recentArticles"))
                .andExpect(model().attributeExists("pageNumbers"))
                .andExpect(view().name("searchResults"));
    }

    @Test
    void searchArticlesRedirectWhenKeywordEmpty() throws Exception {
        mockMvc.perform(get("/search")
                        .param("keyword", "")
                        .param("page", "1")
                        .param("size", "8"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));
    }

    @Test
    void searchArticlesRedirectWhenKeywordNull() throws Exception {
        mockMvc.perform(get("/search")
                        .param("page", "1")
                        .param("size", "8"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));
    }

    @Test
    void getHomePage() throws Exception {
        int currentPage = 1;
        int pageSize = 8;

        List<Category> categories = new ArrayList<>();
        categories.add(new Category(1L, "Category 1", new ArrayList<>()));
        categories.add(new Category(2L, "Category 2", new ArrayList<>()));

        Long defaultCategoryId = categories.getFirst().getCategoryId();

        List<Article> articles = new ArrayList<>();
        articles.add(new Article(1L, "Title", "Content", Date.valueOf("2002-04-13"), new Category(), new ArrayList<>()));
        articles.add(new Article(2L, "Title 2", "Content 2", Date.valueOf("2002-04-14"), new Category(), new ArrayList<>()));

        Page<Article> articlePage = new PageImpl<>(articles);

        when(categoryService.getAllCategories()).thenReturn(categories);
        when(categoryService.getCategoryById(defaultCategoryId)).thenReturn(Optional.of(new Category(defaultCategoryId, "Category 1", new ArrayList<>())));
        when(articleService.getArticlesByCategoryId(defaultCategoryId, PageRequest.of(currentPage - 1, pageSize))).thenReturn(articlePage);
        when(articleService.getArticlesSortedByDate()).thenReturn(new ArrayList<>());

        mockMvc.perform(MockMvcRequestBuilders.get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("index"))
                .andExpect(model().attributeExists("categories"))
                .andExpect(model().attributeExists("categoryName"))
                .andExpect(model().attributeExists("categoryArticles"))
                .andExpect(model().attributeExists("selectedCategoryId"))
                .andExpect(model().attributeExists("recentArticles"))
                .andExpect(model().attributeExists("pageNumbers"));
    }

    @Test
    void getCategoryArticles() throws  Exception {
        long categoryId = 1L;
        int currentPage = 1;
        int pageSize = 8;

        List<Category> categories = new ArrayList<>();
        categories.add(new Category(1L, "Category 1", new ArrayList<>()));
        categories.add(new Category(2L, "Category 2",new ArrayList<>()));

        List<Article> articles = new ArrayList<>();
        articles.add(new Article(1L, "Title", "Content", Date.valueOf("2002-04-13"), new Category(), new ArrayList<>()));
        articles.add(new Article(2L, "Title 2", "Content 2", Date.valueOf("2002-04-14"), new Category(), new ArrayList<>()));
        Page<Article> articlePage = new PageImpl<>(articles);

        when(categoryService.getAllCategories()).thenReturn(categories);
        when(categoryService.getCategoryById(categoryId)).thenReturn(Optional.of(new Category(categoryId, "Category 1", new ArrayList<>())));
        when(articleService.getArticlesByCategoryId(categoryId, PageRequest.of(currentPage - 1, pageSize))).thenReturn(articlePage);
        when(articleService.getArticlesSortedByDate()).thenReturn(new ArrayList<>());

        mockMvc.perform(MockMvcRequestBuilders.get("/category/{categoryId}", categoryId))
                .andExpect(status().isOk())
                .andExpect(view().name("index"))
                .andExpect(model().attributeExists("categories"))
                .andExpect(model().attributeExists("categoryName"))
                .andExpect(model().attributeExists("categoryArticles"))
                .andExpect(model().attributeExists("selectedCategoryId"))
                .andExpect(model().attributeExists("recentArticles"))
                .andExpect(model().attributeExists("pageNumbers"));
    }

    @Test
    void getAboutUs() throws Exception {
        mockMvc.perform(get("/aboutUs"))
                .andExpect(status().isOk())
                .andExpect(view().name("about"));
    }
}