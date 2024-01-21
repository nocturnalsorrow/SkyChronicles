package com.dr.skychronicles.service;

import com.dr.skychronicles.entity.Article;

import java.util.List;
import java.util.Optional;

public interface ArticleService {
    List<Article> getAllArticles();
    List<Article> getArticlesByTitle(String partialTitle);

    List<Article> getArticlesByCategoryId(Long categoryId);

    Optional<Article> getArticleById(Long id);

    Article createArticle(Article article);

    Article updateArticle(Article article);

    void deleteArticleById(Long id);
}
