package com.dr.skychronicles.service;

import com.dr.skychronicles.entity.Article;
import com.dr.skychronicles.entity.Category;
import com.dr.skychronicles.entity.Gallery;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ArticleServiceTest {
    @Mock
    private ArticleService articleService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllArticles() {
        List<Article> expectedArticles = new ArrayList<>();
        expectedArticles.add(new Article(1L, "Title", "Content", Date.valueOf("2002-04-13"), new Category(), new ArrayList<>()));
        expectedArticles.add(new Article(2L, "Title 2", "Content 2", Date.valueOf("2002-04-14"), new Category(), new ArrayList<>()));

        when(articleService.getAllArticles()).thenReturn(expectedArticles);
        List<Article> actualArticles = articleService.getAllArticles();

        assertEquals(expectedArticles.size(), actualArticles.size());
        assertEquals(expectedArticles, actualArticles);
    }

    @Test
    void getArticlesByTitle() {
        List<Article> expectedArticles = new ArrayList<>();
        expectedArticles.add(new Article(1L, "Title 1", "Content", Date.valueOf("2002-04-13"), new Category(), new ArrayList<>()));
        expectedArticles.add(new Article(2L, "Title 2", "Content 2", Date.valueOf("2002-04-14"), new Category(), new ArrayList<>()));

        when(articleService.getArticlesByTitle("Title")).thenReturn(expectedArticles);
        List<Article> actualArticles = articleService.getArticlesByTitle("Title");

        assertEquals(expectedArticles.getFirst().getTitle(), actualArticles.getFirst().getTitle());
        assertEquals(expectedArticles.size(), actualArticles.size());
        assertEquals(expectedArticles, actualArticles);
    }

    @Test
    void getArticlesByTitlePage() {
        List<Article> expectedArticles = new ArrayList<>();
        expectedArticles.add(new Article(1L, "Title 1", "Content", Date.valueOf("2002-04-13"), new Category(), new ArrayList<>()));
        expectedArticles.add(new Article(2L, "Title 2", "Content 2", Date.valueOf("2002-04-14"), new Category(), new ArrayList<>()));
        Page<Article> expectedPage = new PageImpl<>(expectedArticles);

        when(articleService.getArticlesByTitle("Title", PageRequest.of(0, 5))).thenReturn(expectedPage);
        Page<Article> actualPage = articleService.getArticlesByTitle("Title", PageRequest.of(0,5));

        assertEquals(expectedPage.toList().getFirst().getTitle(), actualPage.toList().getFirst().getTitle());
        assertEquals(expectedPage.getTotalElements(), actualPage.getTotalElements());
        assertEquals(expectedPage, actualPage);
    }

    @Test
    void getArticlesByCategoryId() {
        List<Article> expectedArticles = new ArrayList<>();
        expectedArticles.add(new Article(1L, "Title", "Content", Date.valueOf("2002-04-13"), new Category(1L, "Name", new ArrayList<>()), new ArrayList<>()));
        expectedArticles.add(new Article(2L, "Title 2", "Content 2", Date.valueOf("2002-04-14"), new Category(1L, "Name 2", new ArrayList<>()), new ArrayList<>()));
        expectedArticles.add(new Article(3L, "Title 3", "Content 3", Date.valueOf("2002-04-15"), new Category(2L, "Name 3", new ArrayList<>()), new ArrayList<>()));

        when(articleService.getArticlesByCategoryId(1L)).thenReturn(expectedArticles);
        List<Article> actualArticles = articleService.getArticlesByCategoryId(1L);

        assertEquals(expectedArticles.size(), actualArticles.size());
        assertEquals(expectedArticles, actualArticles);
    }

    @Test
    void getArticlesByCategoryIdPage() {
        List<Article> expectedArticles = new ArrayList<>();
        expectedArticles.add(new Article(1L, "Title", "Content", Date.valueOf("2002-04-13"), new Category(1L, "Name", new ArrayList<>()), new ArrayList<>()));
        expectedArticles.add(new Article(2L, "Title 2", "Content 2", Date.valueOf("2002-04-14"), new Category(1L, "Name 2", new ArrayList<>()), new ArrayList<>()));
        expectedArticles.add(new Article(3L, "Title 3", "Content 3", Date.valueOf("2002-04-15"), new Category(2L, "Name 3", new ArrayList<>()), new ArrayList<>()));
        Page<Article> expectedPage = new PageImpl<>(expectedArticles);

        when(articleService.getArticlesByCategoryId(1L, PageRequest.of(0, 5))).thenReturn(expectedPage);
        Page<Article> actualPage = articleService.getArticlesByCategoryId(1L, PageRequest.of(0, 5));

        assertEquals(actualPage.getTotalElements(), expectedPage.getTotalElements());
        assertEquals(actualPage, expectedPage);
    }

    @Test
    void getArticlesSortedByDate() {
        List<Article> expectedArticles = new ArrayList<>();
        expectedArticles.add(new Article(1L, "Title 2", "Content", Date.valueOf("2002-04-13"), new Category(), new ArrayList<>()));
        expectedArticles.add(new Article(2L, "Title 3", "Content 2", Date.valueOf("2002-04-14"), new Category(), new ArrayList<>()));
        expectedArticles.add(new Article(3L, "Title", "Content 3", Date.valueOf("2002-04-10"), new Category(), new ArrayList<>()));

        when(articleService.getArticlesSortedByDate()).thenReturn(expectedArticles);
        List<Article> actualArticles = articleService.getArticlesSortedByDate();

        assertEquals(expectedArticles, actualArticles);
    }

    @Test
    void getArticlesSortedByDatePage() {
        List<Article> expectedArticles = new ArrayList<>();
        expectedArticles.add(new Article(1L, "Title 2", "Content", Date.valueOf("2002-04-13"), new Category(), new ArrayList<>()));
        expectedArticles.add(new Article(2L, "Title 3", "Content 2", Date.valueOf("2002-04-14"), new Category(), new ArrayList<>()));
        expectedArticles.add(new Article(3L, "Title", "Content 3", Date.valueOf("2002-04-10"), new Category(), new ArrayList<>()));
        Page<Article> expectedPage = new PageImpl<>(expectedArticles);

        when(articleService.getArticlesSortedByDate(PageRequest.of(0, 5))).thenReturn(expectedPage);
        Page<Article> actualPage = articleService.getArticlesSortedByDate(PageRequest.of(0, 5));

        assertEquals(expectedPage, actualPage);
    }

    @Test
    void getArticleImage() {
        Gallery expectedGallery = new Gallery(1L,"imageCode", new Article(10L, "Title", "Content", Date.valueOf("2002-04-13"), new Category(), new ArrayList<>()));

        when(articleService.getArticleImage(10L, 1L)).thenReturn(Optional.of(expectedGallery));
        Optional<Gallery> actualGallery = articleService.getArticleImage(10L, 1L);

        assertTrue(actualGallery.isPresent());
        assertEquals(expectedGallery.getId(), actualGallery.get().getId());
    }

    @Test
    void saveArticle() throws IOException {
        Article article = new Article();
        List<MultipartFile> imageFiles = new ArrayList<>();

        imageFiles.add(new MockMultipartFile("image1", new byte[0]));
        articleService.saveArticle(article, imageFiles);

        verify(articleService, times(1)).saveArticle(article, imageFiles);
    }

    @Test
    void processImages() throws IOException {

    }

    @Test
    void getArticleById() {
        Article expectedArticle = new Article(3L, "Title 3", "Content 3", Date.valueOf("2002-04-10"), new Category(), new ArrayList<>());

        when(articleService.getArticleById(2L)).thenReturn(Optional.of(expectedArticle));

        Optional<Article> actualArticle = articleService.getArticleById(2L);

        assertTrue(actualArticle.isPresent());
        assertEquals(expectedArticle, actualArticle.get());
    }

    @Test
    void simpleSaveArticle() {
        Article expectedArticle = new Article(1L, "Title", "Content", Date.valueOf("2024-04-19"), new Category(), new ArrayList<>());
        when(articleService.saveArticle(expectedArticle)).thenReturn(expectedArticle);

        Article actualArticle = articleService.saveArticle(expectedArticle);

        assertNotNull(actualArticle);
        assertEquals(expectedArticle, actualArticle);
        verify(articleService, times(1)).saveArticle(expectedArticle);
    }


    @Test
    void deleteArticleById() {
        long articleId = 2L;

        articleService.deleteArticleById(articleId);

        verify(articleService, times(1)).deleteArticleById(articleId);
    }
}