package com.dr.skychronicles.service;

import com.dr.skychronicles.entity.Article;
import com.dr.skychronicles.repository.ArticleRepository;
import com.dr.skychronicles.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ArticleServiceImpl implements ArticleService {

    private final ArticleRepository articleRepository;
    private final CategoryRepository categoryRepository;

    public ArticleServiceImpl(ArticleRepository articleRepository, CategoryRepository categoryRepository) {
        this.articleRepository = articleRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<Article> getAllArticles() {
        return articleRepository.findAll();
    }

    @Override
    public List<Article> getArticlesByTitle(String partialTitle) {
        return articleRepository.getArticlesByTitle(partialTitle);
    }

    @Override
    public List<Article> getArticlesByCategoryId(Long categoryId) {
        return articleRepository.getArticlesByCategoryId(categoryId);
    }

    @Override
    public List<Article> getArticlesSortedByDate() {
        return articleRepository.getArticlesSortedByDate();
    }

    @Override
    public Optional<Article> getArticleById(Long id) {
        return articleRepository.findById(id);
    }

    @Override
    public Article createArticle(Article article) {
        return articleRepository.save(article);
    }

    @Override
    public Article updateArticle(Article article) {
        return articleRepository.save(article);
    }

    @Override
    public void deleteArticleById(Long id) {
        articleRepository.deleteById(id);
    }
}
