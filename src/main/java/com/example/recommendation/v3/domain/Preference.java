package com.example.recommendation.v3.domain;

import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
public class Preference {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY) // 유저는 여러개의 선호도를 가질 수 있다.
    private Users user;

    @ManyToOne(fetch = FetchType.LAZY) // 위스키는 여러개의 선호도를 가질 수 있다.
    private Whisky whisky;
}
