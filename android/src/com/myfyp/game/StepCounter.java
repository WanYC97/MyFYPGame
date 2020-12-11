package com.myfyp.game;

import com.myfyp.game.helper.StepCounterInterface;

public class StepCounter implements StepCounterInterface {

    private StepCounterApi stepCounterApi;
    public int CURRENT_STEP_COUNT;

    public StepCounter(){
        stepCounterApi = new StepCounterApi();
    }


    public int getStepCount() {
        System.out.println("WTF");
        System.out.println("This is how much" + stepCounterApi.getStepCount());
        CURRENT_STEP_COUNT = stepCounterApi.getStepCount();
        return CURRENT_STEP_COUNT;
    }
}
