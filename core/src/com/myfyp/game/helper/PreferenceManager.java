package com.myfyp.game.helper;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

public class PreferenceManager {
    private static Preferences preferences;

    public PreferenceManager() {
        preferences = Gdx.app.getPreferences("GamePreferences");
    }

    public boolean containsKey(final String key){
        return preferences.contains(key);
    }

    //for step data, money, upgrade number and cost
    public void setFloatValue(final String key, final float value){
        preferences.putFloat(key, value);
        preferences.flush();
    }

    public float getFloatValue(final String key){
        return preferences.getFloat(key, 0.0f);
    }

    public void setIntValue(final String key, final int value){
        preferences.putInteger(key, value);
        preferences.flush();
    }

    public int getIntValue(final String key){
        return preferences.getInteger(key, 0);
    }
}
