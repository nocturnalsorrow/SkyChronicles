package com.dr.skychronicles.service;

import com.dr.skychronicles.entity.Article;
import com.dr.skychronicles.entity.Gallery;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface ArticleService {
    List<Article> getAllArticles();
    Optional<Article> getArticleById(Long id);
    List<Article> getArticlesByTitle(String partialTitle);

    List<Article> getArticlesByCategoryId(Long categoryId);

    List<Article> getArticlesSortedByDate();

    Optional<Gallery> getArticleImage(Long articleId, Long imageId);
    Article saveArticle(Article article, List<MultipartFile> imageFiles) throws IOException;

    Article saveArticle(Article article);

    void deleteArticleById(Long id);
}
