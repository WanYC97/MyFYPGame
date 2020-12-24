package com.myfyp.game.helper;

public class DataClass {
    private static float STEP_COUNT;
    private static float MONEY;
    private static float UPGRADE1_COUNT;
    private static float UPGRADE2_COUNT;

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
}
