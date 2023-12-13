package com.example.recommendation.v2;


import lombok.Getter;
import lombok.Setter;

/**
 * ItemCounter 클래스는 아이템 선호도의 합계와 개수를 계산하고 저장하는 클래스입니다.
 */
@Getter
@Setter
public class ItemCounter {
    private double sum = 0.0;
    private long count = 0L;

    /**
     * 선호도의 합계에 값을 추가하는 메소드입니다.
     *
     * @param sum 추가할 선호도의 값
     */
    public void addSum(double sum) {
        ++this.count;
        this.sum += sum;
    }

    /**
     * 선호도의 평균 편차를 계산하여 반환하는 메소드입니다.
     *
     * @return 선호도의 평균 편차
     */
    public double getDeviation() {
        return sum / count;
    }
}
