package com.dr.skychronicles.repository;

import com.dr.skychronicles.entity.Article;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
@Repository
@Transactional
public interface ArticleRepository extends JpaRepository<Article, Long> {
    @Query("SELECT a FROM Article a WHERE LOWER(a.title) LIKE LOWER(concat('%', :partialTitle, '%'))")
    List<Article> getArticlesByTitle(@Param("partialTitle") String partialTitle);

    @Query("SELECT a FROM Article a WHERE a.categoryId.categoryId = :categoryId")
    List<Article> getArticlesByCategoryId(@Param("categoryId") Long categoryId);

    @Query("SELECT a FROM Article a order by a.publicationDate DESC ")
    List<Article> getArticlesSortedByDate();
}
