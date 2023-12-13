package com.example.recommendation;

import com.example.recommendation.v3.service.DataManagerV3;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.annotation.Before;
import org.hibernate.service.spi.InjectService;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;


@SpringBootApplication
@RequiredArgsConstructor
public class RecommendationApplication {
    private final DataManagerV3 dataManagerV3;

    @Bean
    ApplicationRunner applicationRunner() {
        return args -> {
        dataManagerV3.init();
        };
    }

    public static void main(String[] args) {
        SpringApplication.run(RecommendationApplication.class, args);
    }

}
