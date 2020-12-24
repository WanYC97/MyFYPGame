package com.myfyp.game.helper;

public class DataClass {
    private static float STEP_COUNT;
    private static float MONEY;
    private static float UPGRADE1_COUNT;
    private static float UPGRADE2_COUNT;

    private static float UPGRADE1_MULTIPLIER = 1.05f;
    private static float UPGRADE2_MULTIPLIER = 1.10f;


    public static float getMONEY() {
        return MONEY;
    }

    public static float getStepCount() {
        return STEP_COUNT;
    }

    public static float getUpgrade1Count() {
        return UPGRADE1_COUNT;
    }

    public static float getUpgrade2Count() {
        return UPGRADE2_COUNT;
    }

    public static void setMONEY(float MONEY) {
        DataClass.MONEY = MONEY;
    }

    public static void setStepCount(float stepCount) {
        STEP_COUNT = stepCount;
    }

    public static void setUpgrade1Count(float upgrade1Count) {
        UPGRADE1_COUNT = upgrade1Count;
    }

    public static void setUpgrade2Count(float upgrade2Count) {
        UPGRADE2_COUNT = upgrade2Count;
    }

    public static float getUpgrade1Multiplier() {
        return UPGRADE1_MULTIPLIER;
    }

    public static float getUpgrade2Multiplier() {
        return UPGRADE2_MULTIPLIER;
    }

    public static void setUpgrade1Multiplier(float upgrade1Multiplier) {
        UPGRADE1_MULTIPLIER = upgrade1Multiplier;
    }

    public static void setUpgrade2Multiplier(float upgrade2Multiplier) {
        UPGRADE2_MULTIPLIER = upgrade2Multiplier;
    }
}
