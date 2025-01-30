package com.dr.skychronicles.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Embeddable
public class FavoriteArticleId implements Serializable {
    @Column(name = "article_id", nullable = false)
    private Long articleId;

    @Column(name = "user_email", nullable = false)
    private String userEmail;
}