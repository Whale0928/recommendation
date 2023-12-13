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
    private final Map<Long, Map<Long, ItemCounter>> matrix; // 아이템 간 선호도 차이를 저장 하는 맵 (아이템 ID, (아이템 ID, 선호도 차이))
    private final Map<Long, Map<Long, Double>> dataByMember; //사용자별 아이템 선호도를 저장 하는 맵 (사용자 ID, (아이템 ID, 선호도))

    /**
     * RunnerV2 클래스의 생성자입니다.
     *
     * @param dataModel 사용자별 아이템 선호도 데이터 모델의 리스트
     */
    public RunnerV2(List<DataModel> dataModel) {
        this.dataByMember = new HashMap<>();
        this.matrix = new HashMap<>();
        prepareMatrix(dataModel);
    }

    /**
     * 사용자별 아이템 선호도 데이터 모델을 기반으로 matrix를 준비하는 메소드입니다.
     *
     * @param dataModel 사용자별 아이템 선호도 데이터 모델의 리스트
     */
    private void prepareMatrix(List<DataModel> dataModel) {

        // 호출 시 주입된 사용자, 아이템 선호도 데이터 모델의 리스트를 순회합니다.
        for (DataModel model : dataModel) {
            // 현재 회원의 ID를 가져옵니다.
            final Long memberId = model.getMemberId();

            // 사용자별 아이템 선호도를 저장 하는 맵에서 현재 회원의 아이템 선호도 맵을 가져온다. (없을 경우 빈 해시맵을 생성)
            final Map<Long, Double> itemByMember = dataByMember.computeIfAbsent(memberId, id -> new HashMap<>());

            // 현재 회원의 아이템 선호도 맵에서 하나씩 꺼내서 순회
            for (Map.Entry<Long, Double> itemPreference : itemByMember.entrySet()) {

                //현재 접근 중인 아이템의 ID
                final Long itemId = itemPreference.getKey();
                //현재 접근 중인 아이템의 선호도
                final Double preference = itemPreference.getValue();

                // 동일한 아이템에 대한 처리는 건너뜁니다.
                if (itemId.equals(model.getItemId())) continue;

                // 현재 처리중인 아이템과 다른 모든 아이템에 대한 선호도 차이를 저장할 맵을 가져오거나 새로 생성합니다.
                final Map<Long, ItemCounter> primaryMap = matrix.computeIfAbsent(model.getItemId(), id -> new HashMap<>());
                final Map<Long, ItemCounter> secondaryMap = matrix.computeIfAbsent(itemId, id -> new HashMap<>());

                // 현재 아이템과 다른 아이템 간의 선호도 차이를 계산하고, 이를 해당 ItemCounter에 더합니다.
                primaryMap.computeIfAbsent(itemId, id -> new ItemCounter()).addSum(model.getPreference() - preference);
                secondaryMap.computeIfAbsent(model.getItemId(), id -> new ItemCounter()).addSum(preference - model.getPreference());
            }
            // 현재 회원의 현재 아이템에 대한 선호도를 저장합니다.
            itemByMember.put(model.getItemId(), model.getPreference());
        }
    }

    /**
     * 주어진 사용자 ID에 대한 아이템 선호도 데이터를 반환하는 메소드입니다.
     *
     * @param id 사용자 ID
     * @return 사용자 ID에 대한 아이템 선호도 데이터
     */
    public Map<Long, Double> getDataByMember(Long id) {
        return dataByMember.getOrDefault(id, new HashMap<>());
    }

}
