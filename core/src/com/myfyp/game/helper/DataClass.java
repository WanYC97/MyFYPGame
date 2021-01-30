package com.myfyp.game.helper;

public class DataClass {
    private static float STEP_COUNT;
    private static float LAST_COUNT;
    private static float MONEY;
    private static float UPGRADE1_COUNT;
    private static float UPGRADE2_COUNT;
    private static float UPGRADE3_COUNT;
    private static float COST;

    //FIXED VALUE FOR NOW
    private static float PRICE1 = 100;
    private static float PRICE2 = 300;
    private static float PRICE3 = 1000;
    private static float UPGRADE1_MULTIPLIER = 0.20f;
    private static float UPGRADE2_MULTIPLIER = 0.50f;
    private static float UPGRADE3_MULTIPLIER = 1.50f;


    private static int REWARDS;
    private static int AFFINITY;

    public static int getREWARDS(){
        return REWARDS;
    }

    public static int getAFFINITY(){
        return AFFINITY;
    }

    public static void setREWARDS(int rewards){
        REWARDS = rewards;
    }

    public static void setAFFINITY(int affinity){
        AFFINITY = affinity;
    }

    public static float getStepCount() {
        return STEP_COUNT;
    }

    public static void setStepCount(float stepCount) {
        STEP_COUNT = stepCount;
    }

    public static float getLastCount() {
        return LAST_COUNT;
    }

    public static void setLastCount(float lastCount) {
        LAST_COUNT = lastCount;
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

    public static float getUpgrade3Count() {
        return UPGRADE3_COUNT;
    }

    public static void setUpgrade3Count(float upgrade3Count) {
        UPGRADE3_COUNT = upgrade3Count;
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

    public static float getPRICE3() {
        return PRICE3;
    }

    public static void setPRICE3(float PRICE3) {
        DataClass.PRICE3 = PRICE3;
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

    public static float getUpgrade3Multiplier() {
        return UPGRADE3_MULTIPLIER;
    }

    public static void setUpgrade3Multiplier(float upgrade3Multiplier) {
        UPGRADE2_MULTIPLIER = upgrade3Multiplier;
    }

}
