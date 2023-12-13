package com.example.recommendation.v3.service;

import com.example.recommendation.v3.domain.Users;
import com.example.recommendation.v3.domain.UserRepository;
import com.example.recommendation.v3.domain.WhiskyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static java.time.LocalTime.now;

@Service
@RequiredArgsConstructor
public class DataManagerV3 {
    private final UserRepository userRepository;
    private final WhiskyRepository whiskyRepository;

    public void init() {
        System.out.println("DataManagerV3 init : " + now());
        for (int i = 0; i < 3; i++) {
            userRepository.save(new Users("user" + i, "user" + i + "@gmail.com"));
            whiskyRepository.save(new com.example.recommendation.v3.domain.Whisky("위스키" + i, "위스키" + i + "는 맛있다."));
        }
    }
}
