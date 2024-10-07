package com.dr.skychronicles.repository;

import com.dr.skychronicles.entity.Gallery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
@Repository
@Transactional
public interface GalleryRepository extends JpaRepository<Gallery, Long> {
    //Get article image by article id and image id
    Optional<Gallery> findByArticle_ArticleIdAndId(Long articleId, Long imageId);
}
