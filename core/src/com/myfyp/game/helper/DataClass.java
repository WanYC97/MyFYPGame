package com.myfyp.game.helper;

public class DataClass {
    private static float STEP_COUNT;
    private static float MONEY;
    private static float UPGRADE1_COUNT;
    private static float UPGRADE2_COUNT;
    private static float COST;

    //FIXED VALUE FOR NOW
    private static float PRICE1 = 100;
    private static float PRICE2 = 300;
    private static float UPGRADE1_MULTIPLIER = 0.05f;
    private static float UPGRADE2_MULTIPLIER = 0.10f;


    public static float getStepCount() {
        return STEP_COUNT;
    }

    public static void setStepCount(float stepCount) {
        STEP_COUNT = stepCount;
    }

    public static float getMONEY() {
        return MONEY;
    }

    public static void setMONEY(float MONEY) {
        DataClass.MONEY = MONEY;
    }

    public static float getUpgrade1Count() {
        return UPGRADE1_COUNT;
    }

    public static void setUpgrade1Count(float upgrade1Count) {
        UPGRADE1_COUNT = upgrade1Count;
    }

    public static float getUpgrade2Count() {
        return UPGRADE2_COUNT;
    }

    public static void setUpgrade2Count(float upgrade2Count) {
        UPGRADE2_COUNT = upgrade2Count;
    }

    public static float getCOST() {
        return COST;
    }

    public static void setCOST(float COST) {
        DataClass.COST = COST;
    }

    public static float getPRICE1() {
        return PRICE1;
    }

    public static void setPRICE1(float PRICE1) {
        DataClass.PRICE1 = PRICE1;
    }

    public static float getPRICE2() {
        return PRICE2;
    }

    public static void setPRICE2(float PRICE2) {
        DataClass.PRICE2 = PRICE2;
    }

    //METHOD FOR FIXED VALUE BELOW

    public static float getUpgrade1Multiplier() {
        return UPGRADE1_MULTIPLIER;
    }

    public static void setUpgrade1Multiplier(float upgrade1Multiplier) {
        UPGRADE1_MULTIPLIER = upgrade1Multiplier;
    }

    public static float getUpgrade2Multiplier() {
        return UPGRADE2_MULTIPLIER;
    }

    public static void setUpgrade2Multiplier(float upgrade2Multiplier) {
        UPGRADE2_MULTIPLIER = upgrade2Multiplier;
    }

}
