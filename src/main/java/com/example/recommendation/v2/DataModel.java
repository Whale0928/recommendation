package com.example.recommendation.v2;


import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
public class DataModel {

    private Long memberId; // 사용자 ID
    private Long itemId;  // 아이템 ID
    private double preference; // 선호도 (사용자의 아이템에 대한 선호도)

    public DataModel(Long memberId, Long itemId, double preference) {
        this.memberId = memberId;
        this.itemId = itemId;
        this.preference = preference;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        DataModel dataModel = (DataModel) o;
        return Objects.equals(getMemberId(), dataModel.getMemberId()) && Objects
                .equals(getItemId(), dataModel.getItemId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getMemberId(), getItemId());
    }
}