package com.dr.skychronicles.repository;

import com.dr.skychronicles.entity.Article;
import com.dr.skychronicles.entity.Category;
import com.dr.skychronicles.entity.Gallery;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class GalleryRepositoryTest {
    @Mock
    private GalleryRepository galleryRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findByArticle_ArticleIdAndId() {
        Gallery expectedGallery = new Gallery(1L,"imageCode", new Article(10L, "Title", "Content", Date.valueOf("2002-04-13"), new Category(), new ArrayList<>()));

        when(galleryRepository.findByArticle_ArticleIdAndId(10L, 1L)).thenReturn(Optional.of(expectedGallery));
        Optional<Gallery> actualGallery = galleryRepository.findByArticle_ArticleIdAndId(10L, 1L);

        assertTrue(actualGallery.isPresent());
        assertEquals(expectedGallery.getId(), actualGallery.get().getId());
    }
}