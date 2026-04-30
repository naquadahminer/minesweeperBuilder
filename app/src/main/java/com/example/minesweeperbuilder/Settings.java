package com.example.minesweeperbuilder;

import android.content.Context;
import android.content.SharedPreferences;

public class Settings {
    private static final String PREFS_NAME = "settings";

    public int width = 8;
    public int height = 8;
    public int bombCount = 10;
    public int buildingWidth = 8;
    public int buildingHeight = 8;
    public String difficulty = "BEGINNER";
    public boolean soundEnabled = true;
    public boolean portraitOrientation = true;
    public boolean explore = true;
    public boolean forcedNF = false;

    public void save(Context context) {
        SharedPreferences.Editor editor = context
                .getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
                .edit();
        editor.putString("difficulty", difficulty);
        editor.putInt("height", height);
        editor.putInt("width", width);
        editor.putInt("bombCount", bombCount);
        editor.putInt("buildingWidth", buildingWidth);
        editor.putInt("buildingHeight", buildingHeight);
        editor.putBoolean("soundEnabled", soundEnabled);
        editor.putBoolean("portraitOrientation", portraitOrientation);
        editor.putBoolean("explore", explore);
        editor.putBoolean("forcedNF", forcedNF);
        editor.apply();
    }

    public void load(Context context) {
        SharedPreferences prefs = context
                .getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        difficulty = prefs.getString("difficulty", difficulty);
        height = prefs.getInt("height", height);
        width = prefs.getInt("width", width);
        bombCount = prefs.getInt("bombCount", bombCount);
        buildingHeight = prefs.getInt("buildingHeight", buildingHeight);
        buildingWidth = prefs.getInt("buildingWidth", buildingWidth);
        soundEnabled = prefs.getBoolean("soundEnabled", soundEnabled);
        portraitOrientation = prefs.getBoolean("portraitOrientation", portraitOrientation);
        explore = prefs.getBoolean("explore", explore);
        forcedNF = prefs.getBoolean("forcedNF", forcedNF);
    }
}