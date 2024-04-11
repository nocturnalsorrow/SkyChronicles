package com.dr.skychronicles.repository;

import com.dr.skychronicles.entity.Article;
import com.dr.skychronicles.entity.Category;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

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

        // Проверка результатов
        assertEquals(expectedArticles.size(), actualArticles.size());
        assertEquals(expectedArticles, actualArticles);
    }

    @Test
    void getArticlesByTitlePage() {
    }

    @Test
    void getArticlesByCategoryId() {
    }

    @Test
    void getArticlesByCategoryIdPage() {
    }

    @Test
    void getArticlesSortedByPublicationDate() {
    }

    @Test
    void testGetArticlesSortedByPublicationDate() {
    }
}