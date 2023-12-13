package com.example.recommendation.v3.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Entity
public class Whisky {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;

    @OneToMany(mappedBy = "whisky")
    private List<Preference> preference;

    protected Whisky() {
    }

    public Whisky(String name, String description) {
        this.name = name;
        this.description = description;
    }
}
