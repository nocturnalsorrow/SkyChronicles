package com.dr.skychronicles.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Data
public class Article {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    Long articleId;

    @Column(name = "title", nullable = false)
    String title;

    @Column(name = "content", length = -1)
    String content;

    @Column(name = "publication_date", nullable = false)
    Date publicationDate;

    @ManyToOne
    @JoinColumn(name = "category_id", referencedColumnName = "id")
    Category categoryId;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "article")
    List<Gallery> images = new ArrayList<>();
}
