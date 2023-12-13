package com.example.recommendation.v2;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RecommenderTest {

    private Recommender recommender;
    private RunnerV2 runnerV2;

    @BeforeEach
    public void setUp() {
        List<DataModel> dataModels = Arrays.asList(
                new DataModel(1L, 1L, 5.0),
                new DataModel(1L, 2L, 3.0),
                new DataModel(2L, 1L, 4.0),
                new DataModel(2L, 2L, 2.0),
                new DataModel(3L, 1L, 5.0),
                new DataModel(3L, 2L, 1.0),
                new DataModel(4L, 1L, 1.0)
        );
        runnerV2 = new RunnerV2(dataModels);
        recommender = new Recommender();
    }

    @Test
    @DisplayName("추천 아이템의 리스트가 null이 아닌지 확인")
    public void testRecommend() {
        List<RecommendationResponse> recommendItems = recommender.recommend(runnerV2, 1L, 2.0);
        recommendItems.forEach(System.out::println);
    }
}