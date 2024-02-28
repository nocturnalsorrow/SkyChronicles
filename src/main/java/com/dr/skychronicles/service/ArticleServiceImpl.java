package com.dr.skychronicles.service;

import com.dr.skychronicles.entity.Article;
import com.dr.skychronicles.entity.Gallery;
import com.dr.skychronicles.repository.ArticleRepository;
import com.dr.skychronicles.repository.CategoryRepository;
import com.dr.skychronicles.repository.GalleryRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

@Service
public class ArticleServiceImpl implements ArticleService {

    private final ArticleRepository articleRepository;
    private final CategoryRepository categoryRepository;
    private final GalleryRepository galleryRepository;

    public ArticleServiceImpl(ArticleRepository articleRepository, CategoryRepository categoryRepository, GalleryRepository galleryRepository) {
        this.articleRepository = articleRepository;
        this.categoryRepository = categoryRepository;
        this.galleryRepository = galleryRepository;
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
    public Optional<Gallery> getArticleImage(Long articleId, Long imageId) {
        return galleryRepository.findByArticle_ArticleIdAndId(articleId, imageId);
    }

    @Override
    public Article saveArticle(Article article, List<MultipartFile> imageFiles) throws IOException {
        if (imageFiles != null && !imageFiles.isEmpty()) {
            List<Gallery> articleImages = new ArrayList<>();
            for (MultipartFile file : imageFiles) {
                if (file.getSize() > 0) { // Проверка, что файл не пуст
                    Gallery articleImage = new Gallery();
                    articleImage.setImage(Base64.getEncoder().encodeToString(file.getBytes()));
                    articleImage.setArticle(article);
                    articleImages.add(articleImage);
                }
            }
            article.setImages(articleImages);
        } else {
            // Если fileImages не содержит фотографий, присвоить ему null
            article.setImages(null);
        }
        article.setPublicationDate(Date.valueOf(new SimpleDateFormat("yyyy-MM-dd").format(new java.util.Date())));
        return articleRepository.save(article);
    }

    @Override
    public Optional<Article> getArticleById(Long id) {
        return articleRepository.findById(id);
    }

    @Override
    public Article saveArticle(Article article) {
        return articleRepository.save(article);
    }

    @Override
    public void deleteArticleById(Long id) {
        articleRepository.deleteById(id);
    }
}
