package com.example.recommendation.v2;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class RunnerV2Test {
    private RunnerV2 runnerV2;

    @BeforeEach
    public void setUp() {
        List<DataModel> dataModels = Arrays.asList(
                new DataModel(1L, 1L, 5.0),
                new DataModel(1L, 2L, 3.0),
                new DataModel(2L, 1L, 4.0),
                new DataModel(2L, 2L, 2.0)
        );
        runnerV2 = new RunnerV2(dataModels);
    }
}