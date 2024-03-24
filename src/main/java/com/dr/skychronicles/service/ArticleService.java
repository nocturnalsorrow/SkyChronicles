package com.dr.skychronicles.service;

import com.dr.skychronicles.entity.Article;
import com.dr.skychronicles.entity.Gallery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface ArticleService {
    List<Article> getAllArticles();
    Optional<Article> getArticleById(Long id);
    List<Article> getArticlesByTitle(String partialTitle);
    List<Article> getArticlesByCategoryId(Long categoryId);
    Page<Article> getArticlesByCategoryId(Long categoryId, Pageable pageable);
    List<Article> getArticlesSortedByDate();
    Page<Article> getArticlesSortedByDate(Pageable pageable);
    Optional<Gallery> getArticleImage(Long articleId, Long imageId);
    void saveArticle(Article article, List<MultipartFile> imageFiles) throws IOException;
    Article saveArticle(Article article);
    void deleteArticleById(Long id);
}
