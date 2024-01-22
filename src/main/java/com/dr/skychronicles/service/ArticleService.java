package com.dr.skychronicles.service;

import com.dr.skychronicles.entity.Article;

import java.util.List;
import java.util.Optional;

public interface ArticleService {
    List<Article> getAllArticles();
    Optional<Article> getArticleById(Long id);
    List<Article> getArticlesByTitle(String partialTitle);

    List<Article> getArticlesByCategoryId(Long categoryId);

    List<Article> getArticlesSortedByDate();

    Article createArticle(Article article);

    Article updateArticle(Article article);

    void deleteArticleById(Long id);
}
