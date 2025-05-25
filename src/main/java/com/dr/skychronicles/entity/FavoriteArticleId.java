package com.dr.skychronicles.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
@Embeddable
public class FavoriteArticleId implements Serializable {

    @Column(name = "article_id", nullable = false)
    Long articleId;

    @Column(name = "user_email", nullable = false)
    String userEmail;
}