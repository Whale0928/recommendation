package com.example.recommendation.v2;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


/**
 * RecommendationResponse 클래스는 추천 아이템의 정보를 저장하는 클래스입니다.
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RecommendationResponse {
    private Long itemId; // 추천 아이템의 ID
    private double expectedPreference; // 추천 아이템의 예상 선호도
}