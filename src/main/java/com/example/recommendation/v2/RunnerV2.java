package com.example.recommendation.v2;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * RunnerV2 클래스는 사용자별 아이템 선호도를 계산하고 저장하는 클래스입니다.
 */
@Getter
@Setter
public class RunnerV2 {


    private final Map<Long, Map<Long, ItemCounter>> matrix; // 아이템간 선호도 차이를 저장하는 맵 (아이템ID, (아이템ID, 선호도차이))
    private final Map<Long, Map<Long, Double>> dataByMember; //사용자별 아이템 선호도를 저장하는 맵 (사용자ID, (아이템ID, 선호도))

    /**
     * RunnerV2 클래스의 생성자입니다.
     * @param dataModel 사용자별 아이템 선호도 데이터 모델의 리스트
     */
    public RunnerV2(List<DataModel> dataModel) {
        this.dataByMember = new HashMap<>();
        this.matrix = new HashMap<>();
        prepareMatrix(dataModel);
    }

    /**
     * 사용자별 아이템 선호도 데이터 모델을 기반으로 matrix를 준비하는 메소드입니다.
     * @param dataModel 사용자별 아이템 선호도 데이터 모델의 리스트
     */
    private void prepareMatrix(List<DataModel> dataModel) {
        for (DataModel model : dataModel) {
            final Long memberId = model.getMemberId();
            final Map<Long, Double> itemByMember = dataByMember
                    .computeIfAbsent(memberId, id -> new HashMap<>());

            for (Map.Entry<Long, Double> itemPreference : itemByMember.entrySet()) {
                final Long itemId = itemPreference.getKey();
                final Double preference = itemPreference.getValue();
                if (itemId.equals(model.getItemId())) continue;

                final Map<Long, ItemCounter> primaryMap =
                        matrix.computeIfAbsent(model.getItemId(), id -> new HashMap<>());
                final Map<Long, ItemCounter> secondaryMap =
                        matrix.computeIfAbsent(itemId, id -> new HashMap<>());

                primaryMap.computeIfAbsent(itemId, id -> new ItemCounter()).addSum(model.getPreference() - preference);
                secondaryMap.computeIfAbsent(model.getItemId(), id -> new ItemCounter()).addSum(preference - model.getPreference());
            }
            itemByMember.put(model.getItemId(), model.getPreference());
        }
    }

    /**
     * 주어진 사용자 ID에 대한 아이템 선호도 데이터를 반환하는 메소드입니다.
     * @param id 사용자 ID
     * @return 사용자 ID에 대한 아이템 선호도 데이터
     */
    public Map<Long, Double> getDataByMember(Long id) {
        return dataByMember.getOrDefault(id, new HashMap<>());
    }

}
