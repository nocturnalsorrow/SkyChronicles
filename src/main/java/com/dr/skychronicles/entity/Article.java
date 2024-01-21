package com.dr.skychronicles.entity;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Date;
import java.util.Objects;

@Entity
@Data
public class Article {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private Long articleId;
    @Column(name = "title", nullable = false, length = 255)
    private String title;
    @Column(name = "content", nullable = true, length = -1)
    private String content;
    @Column(name = "photo", nullable = true, length = -1)
    private String photo;
    @Column(name = "publication_date", nullable = true)
    private Date publicationDate;
    @ManyToOne
    @JoinColumn(name = "category_id", referencedColumnName = "id")
    private Category categoryId;


}
