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

    public ArticleServiceImpl(ArticleRepository articleRepository, GalleryRepository galleryRepository) {
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
        return articleRepository.getArticlesSortedByPublicationDate();
    }

    @Override
    public Page<Article> getArticlesSortedByDate(Pageable pageable) {
        return articleRepository.getArticlesSortedByPublicationDate(pageable);
    }

    @Override
    public Optional<Gallery> getArticleImage(Long articleId, Long imageId) {
        return galleryRepository.findByArticle_ArticleIdAndId(articleId, imageId);
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
    public void saveArticle(Article article, List<MultipartFile> imageFiles) throws IOException {
        if(imageFiles.getFirst().getSize() == 0 && article.getArticleId() != null) {
            article.setImages(articleRepository.getReferenceById(article.getArticleId()).getImages());
            articleRepository.save(article);
        } else {
            List<Gallery> newImages = processArticleImages(article, imageFiles);
            article.setImages(newImages);
            articleRepository.save(article);
        }
    }

    public List<Gallery> processArticleImages(Article article, List<MultipartFile> imageFiles) throws IOException {
        List<Gallery> newImages = new ArrayList<>();
        for (MultipartFile file : imageFiles) {
            Gallery articleImage = new Gallery();
            articleImage.setImage(Base64.getEncoder().encodeToString(file.getBytes()));
            articleImage.setArticle(article);
            newImages.add(articleImage);
        }
        return newImages;
    }

    @Override
    public void deleteArticleById(Long id) {
        articleRepository.deleteById(id);
    }
}
