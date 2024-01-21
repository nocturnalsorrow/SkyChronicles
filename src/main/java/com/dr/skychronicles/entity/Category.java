package com.dr.skychronicles.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Collection;
import java.util.Objects;

@Entity
@Data
public class Category {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private Long categoryId;
    @Column(name = "name", nullable = false, length = 255)
    private String name;
    @Column(name = "photo_url")
    private String photoUrl;
    @OneToMany(mappedBy = "categoryId")
    private Collection<Article> articlesByCategoryId;
}
