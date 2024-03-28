package com.dr.skychronicles.service;

import com.dr.skychronicles.entity.Article;
import com.dr.skychronicles.entity.Gallery;
import com.dr.skychronicles.repository.ArticleRepository;
import com.dr.skychronicles.repository.GalleryRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

@Service
public class ArticleServiceImpl implements ArticleService {

    private final ArticleRepository articleRepository;
    private final GalleryRepository galleryRepository;

    public ArticleServiceImpl(ArticleRepository articleRepository,GalleryRepository galleryRepository) {
        this.articleRepository = articleRepository;
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
    public Page<Article> getArticlesByTitle(String partialTitle, Pageable pageable) {
        return articleRepository.getArticlesByTitle(partialTitle, pageable);
    }

    @Override
    public List<Article> getArticlesByCategoryId(Long categoryId) {
        return articleRepository.getArticlesByCategoryId(categoryId);
    }

    @Override
    public Page<Article> getArticlesByCategoryId(Long categoryId, Pageable pageable) {
        return articleRepository.getArticlesByCategoryId(categoryId, pageable);
    }

    @Override
    public List<Article> getArticlesSortedByDate() {
        return articleRepository.getArticlesSortedByDate();
    }

    @Override
    public Page<Article> getArticlesSortedByDate(Pageable pageable) {
        return articleRepository.getArticlesSortedByDate(pageable);
    }

    @Override
    public Optional<Gallery> getArticleImage(Long articleId, Long imageId) {
        return galleryRepository.findByArticle_ArticleIdAndId(articleId, imageId);
    }

    @Override
    public void saveArticle(Article article, List<MultipartFile> imageFiles) throws IOException {
        List<Gallery> existingImages = article.getImages();
        List<Gallery> newImages = new ArrayList<>();

        if (!imageFiles.isEmpty()) {
            // Обработка новых изображений
            for (MultipartFile file : imageFiles) {
                Gallery articleImage = new Gallery();
                articleImage.setImage(Base64.getEncoder().encodeToString(file.getBytes()));
                articleImage.setArticle(article);
                newImages.add(articleImage);
            }
        } else if (existingImages != null && !existingImages.isEmpty()) {
            // Обработка существующих изображений
            for (Gallery existingImage : existingImages) {
                // Декодирование существующего изображения
                byte[] imageBytes = Base64.getDecoder().decode(existingImage.getImage());

                // Создание объекта Gallery для существующего изображения
                Gallery articleImage = new Gallery();
                articleImage.setImage(Base64.getEncoder().encodeToString(imageBytes));
                articleImage.setArticle(article);
                newImages.add(articleImage);
            }
        }

        article.setImages(newImages);

        // Сохранение статьи с обновленными или новыми изображениями
        articleRepository.save(article);
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
