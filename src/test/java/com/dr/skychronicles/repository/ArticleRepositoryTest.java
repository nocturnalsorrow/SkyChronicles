package com.dr.skychronicles.repository;

import com.dr.skychronicles.entity.Article;
import com.dr.skychronicles.entity.Category;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class ArticleRepositoryTest {

    @Mock
    private ArticleRepository articleRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getArticlesByTitle() {
        List<Article> expectedArticles = new ArrayList<>();
        expectedArticles.add(new Article(1L, "Title", "Content", Date.valueOf("2002-04-13"), new Category(), new ArrayList<>()));
        expectedArticles.add(new Article(2L, "Title 2", "Content 2", Date.valueOf("2002-04-14"), new Category(), new ArrayList<>()));

        when(articleRepository.getArticlesByTitle("Title")).thenReturn(expectedArticles);

        List<Article> actualArticles = articleRepository.getArticlesByTitle("Title");

        assertEquals(expectedArticles.size(), actualArticles.size());
        assertEquals(expectedArticles, actualArticles);
    }

    @Test
    void getArticlesByTitlePage() {
        List<Article> expectedArticles = new ArrayList<>();
        expectedArticles.add(new Article(1L, "Title", "Content", Date.valueOf("2002-04-13"), new Category(), new ArrayList<>()));
        expectedArticles.add(new Article(2L, "Title 2", "Content 2", Date.valueOf("2002-04-14"), new Category(), new ArrayList<>()));
        Page<Article> expectedPage = new PageImpl<>(expectedArticles);

        when(articleRepository.getArticlesByTitle("Title", PageRequest.of(0, 5))).thenReturn(expectedPage);
        Page<Article> actualPage = articleRepository.getArticlesByTitle("Title", PageRequest.of(0, 5));

        assertEquals(expectedPage.getTotalElements(), actualPage.getTotalElements());
        assertEquals(expectedPage.getContent(), actualPage.getContent());
    }

    @Test
    void getArticlesByCategoryId() {
        List<Article> expectedArticles = new ArrayList<>();
        expectedArticles.add(new Article(1L, "Title", "Content", Date.valueOf("2002-04-13"), new Category(1L, "Name", "photo_url", new ArrayList<>()), new ArrayList<>()));
        expectedArticles.add(new Article(2L, "Title 2", "Content 2", Date.valueOf("2002-04-14"), new Category(1L, "Name 2", "photo_url", new ArrayList<>()), new ArrayList<>()));
        expectedArticles.add(new Article(3L, "Title 3", "Content 3", Date.valueOf("2002-04-15"), new Category(2L, "Name 3", "photo_url", new ArrayList<>()), new ArrayList<>()));

        when(articleRepository.getArticlesByCategoryId(1L)).thenReturn(expectedArticles);
        List<Article> actualArticles = articleRepository.getArticlesByCategoryId(1L);

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

        when(articleRepository.getArticlesByCategoryId(1L, PageRequest.of(0, 5))).thenReturn(expectedPage);
        Page<Article> actualPage = articleRepository.getArticlesByCategoryId(1L, PageRequest.of(0, 5));

        assertEquals(actualPage.getTotalElements(), expectedPage.getTotalElements());
        assertEquals(actualPage, expectedPage);
    }

    @Test
    void getArticlesSortedByPublicationDate() {
        List<Article> expectedArticles = new ArrayList<>();
        expectedArticles.add(new Article(1L, "Title 2", "Content", Date.valueOf("2002-04-13"), new Category(), new ArrayList<>()));
        expectedArticles.add(new Article(2L, "Title 3", "Content 2", Date.valueOf("2002-04-14"), new Category(), new ArrayList<>()));
        expectedArticles.add(new Article(3L, "Title", "Content 3", Date.valueOf("2002-04-10"), new Category(), new ArrayList<>()));

        when(articleRepository.getArticlesSortedByPublicationDate()).thenReturn(expectedArticles);
        List<Article> actualArticles = articleRepository.getArticlesSortedByPublicationDate();

        assertEquals(expectedArticles, actualArticles);
    }



    @Test
    void getArticlesSortedByPublicationDatePage() {
        List<Article> expectedArticles = new ArrayList<>();
        expectedArticles.add(new Article(1L, "Title 2", "Content", Date.valueOf("2002-04-13"), new Category(), new ArrayList<>()));
        expectedArticles.add(new Article(2L, "Title 3", "Content 2", Date.valueOf("2002-04-14"), new Category(), new ArrayList<>()));
        expectedArticles.add(new Article(3L, "Title", "Content 3", Date.valueOf("2002-04-10"), new Category(), new ArrayList<>()));
        Page<Article> expectedPage = new PageImpl<>(expectedArticles);

        when(articleRepository.getArticlesSortedByPublicationDate(PageRequest.of(0, 5))).thenReturn(expectedPage);
        Page<Article> actualPage = articleRepository.getArticlesSortedByPublicationDate(PageRequest.of(0, 5));

        assertEquals(expectedPage, actualPage);
    }
}