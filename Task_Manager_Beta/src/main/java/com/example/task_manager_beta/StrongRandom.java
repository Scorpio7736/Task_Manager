package com.example.task_manager_beta;

import java.util.concurrent.ThreadLocalRandom;

public class StrongRandom {

    public static double getDouble( double min, double max){
        return ThreadLocalRandom.current().nextDouble(min, max);
    }

}
