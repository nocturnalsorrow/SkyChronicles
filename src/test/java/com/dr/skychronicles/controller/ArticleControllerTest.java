package com.dr.skychronicles.controller;

import com.dr.skychronicles.entity.Article;
import com.dr.skychronicles.entity.Category;
import com.dr.skychronicles.entity.Gallery;
import com.dr.skychronicles.service.ArticleService;
import com.dr.skychronicles.service.CategoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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
        Article article = new Article(1L, "Title", "Content", Date.valueOf("2002-04-13"), new Category(1L, "Name", new ArrayList<>()), new ArrayList<>());

        when(articleService.getArticleById(1L)).thenReturn(Optional.of(article));

        mockMvc.perform(get("/article/{articleId}", 1L)).andExpect(status().isOk());

        verify(articleService, times(1)).getArticleById(1L);
    }


    @Test
    void getArticleImage() throws Exception {
        byte[] imageBytes = {20};
        String imageBase64 = Base64.getEncoder().encodeToString(imageBytes);
        Gallery gallery = new Gallery();
        gallery.setImage(imageBase64);

        when(articleService.getArticleImage(anyLong(), anyLong())).thenReturn(Optional.of(gallery));

        mockMvc.perform(get("/1/image/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.IMAGE_JPEG))
                .andReturn();

    }

    @Test
    void getAllArticles() throws Exception {
        List<Article> articles = new ArrayList<>();
        articles.add(new Article(1L, "Title", "Content", Date.valueOf("2002-04-13"), new Category(), new ArrayList<>()));
        articles.add(new Article(2L, "Title 2", "Content 2", Date.valueOf("2002-04-14"), new Category(), new ArrayList<>()));

        when(articleService.getArticlesSortedByDate()).thenReturn(articles);

        mockMvc.perform(get("/articles"))
                .andExpect(status().isOk());
    }

    @Test
    void createArticle() throws Exception {
        mockMvc.perform(get("/article"))
                .andExpect(status().isOk())
                .andExpect(view().name("createArticle"))
                .andReturn();
    }

    @Test
     void postCreateArticle() throws Exception {
        MockMultipartFile file = new MockMultipartFile("imageFiles", "filename.txt", "text/plain", "some bytes".getBytes());

        doNothing().when(articleService).saveArticle(any(Article.class), anyList());

        mockMvc.perform(multipart("/article")
                        .file(file)
                        .param("title", "Test Article")
                        .param("content", "Test Content"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/articles"));
    }

    @Test
    void getArticleToUpdate() throws Exception {
        Long articleId = 1L;
        Article article = new Article(articleId, "Title", "Content", Date.valueOf("2002-04-13"), new Category(), new ArrayList<>());

        when(articleService.getArticleById(articleId)).thenReturn(Optional.of(article));
        when(categoryService.getAllCategories()).thenReturn(new ArrayList<>());

        mockMvc.perform(get("/article/update/{articleId}", articleId))
                .andExpect(status().isOk())
                .andExpect(view().name("updateArticle"))
                .andReturn();
    }


    @Test
    void updateArticle() throws Exception {
        MockMultipartFile file = new MockMultipartFile("imageFiles", "filename.txt", "text/plain", "some bytes".getBytes());

        doNothing().when(articleService).saveArticle(any(Article.class), anyList());

        mockMvc.perform(multipart("/article/update")
                        .file(file)
                        .param("title", "Test Article")
                        .param("content", "Test Content"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/articles"));
    }

    @Test
    void deleteArticle() throws Exception {
        Long articleId = 1L;

        mockMvc.perform(MockMvcRequestBuilders.get("/article/delete/{articleId}", articleId))
                .andExpect(status().is3xxRedirection()) // Проверяем, что запрос вернул редирект
                .andExpect(redirectedUrl("/articles")); // Проверяем, что редирект ведет на ожидаемый URL

        // Проверяем, что метод deleteArticleById() был вызван с ожидаемым articleId
        verify(articleService, times(1)).deleteArticleById(articleId);
    }
}