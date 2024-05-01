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
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class ArticleControllerTest {
    @Mock
    private ArticleService articleService;
    @Mock
    private CategoryService categoryService;

    @InjectMocks
    private ArticleController articleController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
//        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(articleController).build();
    }

    @Test
    void getArticlePage() throws Exception {
        Article article = new Article(1L, "Title", "Content", Date.valueOf("2002-04-13"), new Category(1L, "Name", "photo_url", new ArrayList<>()), new ArrayList<>());

        when(articleService.getArticleById(1L)).thenReturn(Optional.of(article));
        mockMvc.perform(get("/article/{articleId}", 1L)).andExpect(status().isOk());

        verify(articleService, times(1)).getArticleById(1L);
    }


    @Test
    void getArticleImage() {
    }

    @Test
    void getAllArticles() {
    }

    @Test
    void createArticle() {
    }

    @Test
    void postCreateArticle() throws Exception {
        Article article = new Article(1L, "Title", "Content", Date.valueOf("2002-04-13"), new Category(1L, "Name", "photo_url", new ArrayList<>()), new ArrayList<>());
        MockMultipartFile imageFile = new MockMultipartFile("imageFiles", "test1.jpg", "image/jpeg", "test data".getBytes());

        mockMvc.perform(multipart("/article")
                        .file(imageFile)
                        .param("title", article.getTitle())
                        .param("content", article.getContent())
                        .param("categoryId", String.valueOf(article.getCategoryId())))
                .andExpect(status().isCreated());
    }

    @Test
    void getArticleToUpdate() {
    }

    @Test
    void updateArticle() {
    }

    @Test
    void deleteArticle() {
    }
}