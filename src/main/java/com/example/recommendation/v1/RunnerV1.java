package com.example.recommendation.v1;

import com.example.recommendation.InputData;
import com.example.recommendation.domain.Item;
import com.example.recommendation.domain.Users;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Map;

public class RunnerV1 {

    //아이템 간 선호도 차이 , 각 아이템 쌍에 대한 평균 평가 차이를 저장
    private final Map<Item, Map<Item, Double>> diff = new HashMap<>();
    
    //아이템 간 평가 빈도를 저장 , 각 아이템 쌍에 대해 얼마나 많은 사용자가 평가를 남겼는지
    private final Map<Item, Map<Item, Integer>> freq = new HashMap<>();
    
    // 최종적으로 생성된 사용자별 아이템 쌍에 대해서 출력
    private final Map<Users, HashMap<Item, Double>> outputData = new HashMap<>();

    public void run() {
        Map<Users, HashMap<Item, Double>> inputData = InputData.initializeData(10);
        System.out.println("슬로프 1 - 예측 전\n");
        buildDifferencesMatrix(inputData);
        System.out.println("\n슬로프 1 - 예측 포함\n");
        predict(inputData);
    }

    /**
     * 이용 가능한 데이터를 바탕으로 두 항목 간의 관계를 계산합니다.
     * 항목 및 발생 횟수
     *
     * @param data 기존 사용자 데이터 및 해당 아이템 평가
     */
    private void buildDifferencesMatrix(Map<Users, HashMap<Item, Double>> data) {
        for (HashMap<Item, Double> user : data.values()) {
            for (Map.Entry<Item, Double> e : user.entrySet()) {
                if (!diff.containsKey(e.getKey())) {
                    diff.put(e.getKey(), new HashMap<>());
                    freq.put(e.getKey(), new HashMap<>());
                }
                for (Map.Entry<Item, Double> e2 : user.entrySet()) {
                    int oldCount = 0;
                    if (freq.get(e.getKey()).containsKey(e2.getKey())) {
                        oldCount = freq.get(e.getKey()).get(e2.getKey());
                    }
                    double oldDiff = 0.0;
                    if (diff.get(e.getKey()).containsKey(e2.getKey())) {
                        oldDiff = diff.get(e.getKey()).get(e2.getKey());
                    }
                    double observedDiff = e.getValue() - e2.getValue();
                    freq.get(e.getKey()).put(e2.getKey(), oldCount + 1);
                    diff.get(e.getKey()).put(e2.getKey(), oldDiff + observedDiff);
                }
            }
        }
        for (Item j : diff.keySet()) {
            for (Item i : diff.get(j).keySet()) {
                double oldValue = diff.get(j).get(i).doubleValue();
                int count = freq.get(j).get(i).intValue();
                diff.get(j).put(i, oldValue / count);
            }
        }
        printData(data);
    }

    /**
     * 기존 데이터를 기반으로 누락된 모든 평가를 예측합니다. 예측이 안되면
     * 가능하면 값은 -1과 같습니다.
     *
     * @param data 기존 사용자 데이터 및 해당 아이템 평가
     */
    private void predict(Map<Users, HashMap<Item, Double>> data) {
        HashMap<Item, Double> uPred = new HashMap<>();
        HashMap<Item, Integer> uFreq = new HashMap<>();
        for (Item j : diff.keySet()) {
            uFreq.put(j, 0);
            uPred.put(j, 0.0);
        }
        for (Map.Entry<Users, HashMap<Item, Double>> e : data.entrySet()) {
            for (Item j : e.getValue().keySet()) {
                for (Item k : diff.keySet()) {
                    double predictedValue = diff.get(k).get(j) + e.getValue().get(j);
                    double finalValue = predictedValue * freq.get(k).get(j);
                    uPred.put(k, uPred.get(k) + finalValue);
                    uFreq.put(k, uFreq.get(k) + freq.get(k).get(j));

                }
            }
            HashMap<Item, Double> clean = new HashMap<>();
            for (Item j : uPred.keySet()) {
                if (uFreq.get(j) > 0) {
                    clean.put(j, uPred.get(j) / uFreq.get(j));
                }
            }
            for (Item j : InputData.items) {
                if (e.getValue().containsKey(j)) {
                    clean.put(j, e.getValue().get(j));
                } else if (!clean.containsKey(j)) {
                    clean.put(j, -1.0);
                }
            }
            outputData.put(e.getKey(), clean);
        }
        printData(outputData);
    }

    private void printData(Map<Users, HashMap<Item, Double>> data) {
        for (Users user : data.keySet()) {
            System.out.println(user.getUsername() + ":");
            print(data.get(user));
        }
    }

    private void print(HashMap<Item, Double> hashMap) {
        NumberFormat formatter = new DecimalFormat("#0.00");
        for (Item j : hashMap.keySet()) {
            System.out.println(" " + j.getItemName() + " 선호도 : " + formatter.format(hashMap.get(j).doubleValue()));
        }
    }
}
