package com.myfyp.game;

import android.os.Bundle;

import com.myfyp.game.helper.StepCounterInterface;

public class StepCounter implements StepCounterInterface {

    private StepCounterApi stepCounterApi;
    public float CURRENT_STEP_COUNT;

    public StepCounter(){
        System.out.println("BEGIN HERE");
        stepCounterApi = new StepCounterApi();
    }

    public float getStepCount() {
        System.out.println("get step count called");
        CURRENT_STEP_COUNT = stepCounterApi.getStepCount();
        return CURRENT_STEP_COUNT;
    }
}
