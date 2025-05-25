package com.dr.skychronicles.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Data
public class Gallery {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    Long id;

    @Lob
    @Column(columnDefinition = "LONGBLOB", name = "image")
    String image;

    @ManyToOne
    @JoinColumn(name = "article_id", referencedColumnName = "id")
    Article article;

}
