package com.dr.skychronicles.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Arrays;
import java.util.Objects;

@Entity
@Data
public class Gallery {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;
    @Lob
    @Column(columnDefinition = "LONGBLOB", name = "image")
    private String image;
    @ManyToOne
    @JoinColumn(name = "article_id", referencedColumnName = "id")
    private Article article;

}
