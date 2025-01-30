package com.dr.skychronicles.repository;

import com.dr.skychronicles.entity.FavoriteArticle;
import com.dr.skychronicles.entity.FavoriteArticleId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FavoriteArticleRepository extends JpaRepository<FavoriteArticle, FavoriteArticleId> {
    List<FavoriteArticle> findByUserEmail(String userEmail);
}
