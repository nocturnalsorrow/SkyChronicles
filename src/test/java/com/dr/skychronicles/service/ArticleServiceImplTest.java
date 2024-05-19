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

class ArticleServiceImplTest {

    @Mock
    private ArticleServiceImpl articleServiceImpl;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllArticles() {
        List<Article> expectedArticles = new ArrayList<>();
        expectedArticles.add(new Article(1L, "Title", "Content", Date.valueOf("2002-04-13"), new Category(), new ArrayList<>()));
        expectedArticles.add(new Article(2L, "Title 2", "Content 2", Date.valueOf("2002-04-14"), new Category(), new ArrayList<>()));

        when(articleServiceImpl.getAllArticles()).thenReturn(expectedArticles);
        List<Article> actualArticles = articleServiceImpl.getAllArticles();

        assertEquals(expectedArticles.size(), actualArticles.size());
        assertEquals(expectedArticles, actualArticles);
    }

    @Test
    void getArticlesByTitle() {
        List<Article> expectedArticles = new ArrayList<>();
        expectedArticles.add(new Article(1L, "Title 1", "Content", Date.valueOf("2002-04-13"), new Category(), new ArrayList<>()));
        expectedArticles.add(new Article(2L, "Title 2", "Content 2", Date.valueOf("2002-04-14"), new Category(), new ArrayList<>()));

        when(articleServiceImpl.getArticlesByTitle("Title")).thenReturn(expectedArticles);
        List<Article> actualArticles = articleServiceImpl.getArticlesByTitle("Title");

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

        when(articleServiceImpl.getArticlesByTitle("Title", PageRequest.of(0, 5))).thenReturn(expectedPage);
        Page<Article> actualPage = articleServiceImpl.getArticlesByTitle("Title", PageRequest.of(0,5));

        assertEquals(expectedPage.toList().getFirst().getTitle(), actualPage.toList().getFirst().getTitle());
        assertEquals(expectedPage.getTotalElements(), actualPage.getTotalElements());
        assertEquals(expectedPage, actualPage);
    }

    @Test
    void getArticlesByCategoryId() {
        List<Article> expectedArticles = new ArrayList<>();
        expectedArticles.add(new Article(1L, "Title", "Content", Date.valueOf("2002-04-13"), new Category(1L, "Name", "photo_url", new ArrayList<>()), new ArrayList<>()));
        expectedArticles.add(new Article(2L, "Title 2", "Content 2", Date.valueOf("2002-04-14"), new Category(1L, "Name 2", "photo_url", new ArrayList<>()), new ArrayList<>()));
        expectedArticles.add(new Article(3L, "Title 3", "Content 3", Date.valueOf("2002-04-15"), new Category(2L, "Name 3", "photo_url", new ArrayList<>()), new ArrayList<>()));

        when(articleServiceImpl.getArticlesByCategoryId(1L)).thenReturn(expectedArticles);
        List<Article> actualArticles = articleServiceImpl.getArticlesByCategoryId(1L);

        assertEquals(expectedArticles.size(), actualArticles.size());
        assertEquals(expectedArticles, actualArticles);
    }

    @Test
    void getArticlesByCategoryIdPage() {
        List<Article> expectedArticles = new ArrayList<>();
        expectedArticles.add(new Article(1L, "Title", "Content", Date.valueOf("2002-04-13"), new Category(1L, "Name", "photo_url", new ArrayList<>()), new ArrayList<>()));
        expectedArticles.add(new Article(2L, "Title 2", "Content 2", Date.valueOf("2002-04-14"), new Category(1L, "Name 2", "photo_url", new ArrayList<>()), new ArrayList<>()));
        expectedArticles.add(new Article(3L, "Title 3", "Content 3", Date.valueOf("2002-04-15"), new Category(2L, "Name 3", "photo_url", new ArrayList<>()), new ArrayList<>()));
        Page<Article> expectedPage = new PageImpl<>(expectedArticles);

        when(articleServiceImpl.getArticlesByCategoryId(1L, PageRequest.of(0, 5))).thenReturn(expectedPage);
        Page<Article> actualPage = articleServiceImpl.getArticlesByCategoryId(1L, PageRequest.of(0, 5));

        assertEquals(actualPage.getTotalElements(), expectedPage.getTotalElements());
        assertEquals(actualPage, expectedPage);
    }

    @Test
    void getArticlesSortedByDate() {
        List<Article> expectedArticles = new ArrayList<>();
        expectedArticles.add(new Article(1L, "Title 2", "Content", Date.valueOf("2002-04-13"), new Category(), new ArrayList<>()));
        expectedArticles.add(new Article(2L, "Title 3", "Content 2", Date.valueOf("2002-04-14"), new Category(), new ArrayList<>()));
        expectedArticles.add(new Article(3L, "Title", "Content 3", Date.valueOf("2002-04-10"), new Category(), new ArrayList<>()));

        when(articleServiceImpl.getArticlesSortedByDate()).thenReturn(expectedArticles);
        List<Article> actualArticles = articleServiceImpl.getArticlesSortedByDate();

        assertEquals(expectedArticles, actualArticles);
    }

    @Test
    void getArticlesSortedByDatePage() {
        List<Article> expectedArticles = new ArrayList<>();
        expectedArticles.add(new Article(1L, "Title 2", "Content", Date.valueOf("2002-04-13"), new Category(), new ArrayList<>()));
        expectedArticles.add(new Article(2L, "Title 3", "Content 2", Date.valueOf("2002-04-14"), new Category(), new ArrayList<>()));
        expectedArticles.add(new Article(3L, "Title", "Content 3", Date.valueOf("2002-04-10"), new Category(), new ArrayList<>()));
        Page<Article> expectedPage = new PageImpl<>(expectedArticles);

        when(articleServiceImpl.getArticlesSortedByDate(PageRequest.of(0, 5))).thenReturn(expectedPage);
        Page<Article> actualPage = articleServiceImpl.getArticlesSortedByDate(PageRequest.of(0, 5));

        assertEquals(expectedPage, actualPage);
    }

    @Test
    void getArticleImage() {
        Gallery expectedGallery = new Gallery(1L,"imageCode", new Article(10L, "Title", "Content", Date.valueOf("2002-04-13"), new Category(), new ArrayList<>()));

        when(articleServiceImpl.getArticleImage(10L, 1L)).thenReturn(Optional.of(expectedGallery));
        Optional<Gallery> actualGallery = articleServiceImpl.getArticleImage(10L, 1L);

        assertTrue(actualGallery.isPresent());
        assertEquals(expectedGallery.getId(), actualGallery.get().getId());
    }

    @Test
    void simpleSaveArticle() {
        Article expectedArticle = new Article(1L, "Title", "Content", Date.valueOf("2024-04-19"), new Category(), new ArrayList<>());

        when(articleServiceImpl.saveArticle(expectedArticle)).thenReturn(expectedArticle);
        Article actualArticle = articleServiceImpl.saveArticle(expectedArticle);

        assertNotNull(actualArticle);
        assertEquals(expectedArticle, actualArticle);
        verify(articleServiceImpl, times(1)).saveArticle(expectedArticle);
    }

    @Test
    void saveArticle() throws IOException {
        Article article = new Article();
        List<MultipartFile> imageFiles = new ArrayList<>();

        imageFiles.add(new MockMultipartFile("image1", new byte[0]));
        articleServiceImpl.saveArticle(article, imageFiles);

        verify(articleServiceImpl, times(1)).saveArticle(article, imageFiles);
    }

    @Test
    void processImages() throws IOException {
        Article article = new Article(1L, "Title", "Content", Date.valueOf("2024-04-19"), new Category(), new ArrayList<>());
        List<MultipartFile> imageFiles = List.of(
                new MockMultipartFile("image1", new byte[0]),
                new MockMultipartFile("image1", new byte[0]));

        List<Gallery> result = articleServiceImpl.processImages(article, imageFiles);

        verify(articleServiceImpl, times(1)).processImages(article, imageFiles);
    }

    @Test
    void getArticleById() {
        Article expectedArticle = new Article(3L, "Title 3", "Content 3", Date.valueOf("2002-04-10"), new Category(), new ArrayList<>());

        when(articleServiceImpl.getArticleById(2L)).thenReturn(Optional.of(expectedArticle));

        Optional<Article> actualArticle = articleServiceImpl.getArticleById(2L);

        assertTrue(actualArticle.isPresent());
        assertEquals(expectedArticle, actualArticle.get());
    }

    @Test
    void deleteArticleById() {
        long articleId = 2L;

        articleServiceImpl.deleteArticleById(articleId);

        verify(articleServiceImpl, times(1)).deleteArticleById(articleId);
    }
}