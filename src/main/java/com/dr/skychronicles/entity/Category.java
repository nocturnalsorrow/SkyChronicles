package com.dr.skychronicles.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Collection;

@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Data
public class Category {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    Long categoryId;

    @Column(name = "name", nullable = false)
    String name;

    @OneToMany(mappedBy = "categoryId")
    Collection<Article> articlesByCategoryId;
}
