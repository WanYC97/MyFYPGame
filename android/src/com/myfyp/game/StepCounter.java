package com.myfyp.game;

import android.os.Bundle;

import com.myfyp.game.helper.StepCounterInterface;

public class StepCounter {

    private StepCounterApi stepCounterApi;
    private AndroidLauncher androidLauncher;
    public float CURRENT_STEP_COUNT;

    public StepCounter(){
        System.out.println("BEGIN HERE");
        androidLauncher = new AndroidLauncher();
    }

    public float getStepCount() {
        System.out.println("get step count called");
        CURRENT_STEP_COUNT = androidLauncher.getStepCount();
        return CURRENT_STEP_COUNT;
    }
}
