package com.dr.skychronicles.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;

@Entity
@Data
public class User {
    @Id
    @Column(name = "email")
    private String email;
    @Basic
    @Column(name = "password")
    private String password;
    @Basic
    @Column(name = "username")
    private String username;
    @Basic
    @Column(name = "role")
    private String role;
}
