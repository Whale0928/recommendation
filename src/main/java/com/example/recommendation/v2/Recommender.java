package com.example.recommendation.v2;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Recommender 클래스는 사용자에게 아이템을 추천하는 기능을 제공합니다.
 */
public class Recommender {


    /**
     * 사용자에게 아이템을 추천하는 메소드입니다.
     *
     * @param dataMatrix    사용자별 아이템 선호도 데이터 매트릭스
     * @param memberId      사용자 ID
     * @param minPreference 최소 선호도
     * @return 추천 아이템의 리스트
     */
    public List<RecommendationResponse> recommend(RunnerV2 dataMatrix, Long memberId, double minPreference) {
        final Map<Long, Map<Long, ItemCounter>> matrix = dataMatrix.getMatrix();
        final Map<Long, Double> dataByMember = dataMatrix.getDataByMember(memberId);
        final List<RecommendationResponse> recommendItems = new ArrayList<>();

        for (Map.Entry<Long, Map<Long, ItemCounter>> matrixEntry : matrix.entrySet()) {
            final Long primaryItemId = matrixEntry.getKey();
            final Map<Long, ItemCounter> matrixValue = matrixEntry.getValue();
            if (dataByMember.containsKey(primaryItemId)) continue;

            double sumValue = 0.0;
            long count = 0;

            for (Map.Entry<Long, Double> itemPreference : dataByMember.entrySet()) {
                final Long itemId = itemPreference.getKey();
                final Double preference = itemPreference.getValue();
                final ItemCounter itemCounter = matrixValue.get(itemId);
                final double deviation = itemCounter.getDeviation();
                sumValue += (preference + deviation) * itemCounter.getCount();
                count += itemCounter.getCount();
            }

            final double expectedPreference = sumValue / count;
            if (expectedPreference >= minPreference) {
                recommendItems.add(new RecommendationResponse(primaryItemId, expectedPreference));
            }
        }
        return recommendItems;
    }
}
