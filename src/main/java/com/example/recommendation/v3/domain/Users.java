package com.example.recommendation.v3.domain;


import jakarta.persistence.*;
import lombok.Getter;

import java.util.List;

@Getter
@Entity
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    private String username;

    @OneToMany(mappedBy = "user")
    private List<Preference> preferences;

    protected Users() {
    }

    public Users(String email, String username) {
        this.email = email;
        this.username = username;
    }
}
