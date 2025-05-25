package com.dr.skychronicles.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Data
public class User {

    @Id
    @Column(name = "email")
    String email;

    @Basic
    @Column(name = "password")
    String password;

    @Basic
    @Column(name = "username")
    String username;

    @Basic
    @Column(name = "role")
    String role;

    @Lob
    @Column(columnDefinition = "LONGBLOB", name = "profile_image")
    String profileImage;
}
