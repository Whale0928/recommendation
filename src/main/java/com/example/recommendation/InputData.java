package com.example.recommendation;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.example.recommendation.domain.Item;
import com.example.recommendation.domain.Users;
import lombok.Data;


@Data
public class InputData {

    public static List<Item> items = Arrays.asList(
            new Item("위스키"),
            new Item("소주"),
            new Item("탄산"),
            new Item("꼬냑"),
            new Item("와인"));

    public static Map<Users, HashMap<Item, Double>> initializeData(int numberOfUsers) {
        Map<Users, HashMap<Item, Double>> data = new HashMap<>();
        HashMap<Item, Double> newUser; //
        Set<Item> newRecommendationSet; // 추천 목록

        for (int i = 0; i < numberOfUsers; i++) {
            newUser = new HashMap<>(); // 새로운 사용자
            newRecommendationSet = new HashSet<>(); // 새로운 추천 목록
            for (int j = 0; j < 3; j++) { // 추천 목록에 3개의 아이템을 추가
                newRecommendationSet.add(items.get((int) (Math.random() * 5)));// 0~4
            }
            for (Item item : newRecommendationSet) { // 추천 목록에 있는 아이템에 대해
                newUser.put(item, Math.random());    // 랜덤한 점수를 부여
            }
            data.put(new Users("사용자 " + i), newUser);
        }
        return data;
    }

}