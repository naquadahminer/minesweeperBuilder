package com.example.minesweeperbuilder;

import android.content.Context;
import android.content.SharedPreferences;

public class Settings {
    private static final String PREFS_NAME = "settings";

    public Difficulty difficulty = Difficulty.BEGINNER;
    public boolean soundEnabled = true;

    public void save(Context context) {
        SharedPreferences.Editor editor = context
                .getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
                .edit();
        editor.putString("difficulty", difficulty.name());
        editor.putBoolean("soundEnabled", soundEnabled);
        editor.apply();
    }

    public void load(Context context) {
        SharedPreferences prefs = context
                .getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        String savedName = prefs.getString("difficulty", Difficulty.BEGINNER.name());
        difficulty = Difficulty.valueOf(savedName);
        soundEnabled = prefs.getBoolean("soundEnabled", soundEnabled);
    }
}