package com.example.minesweeperbuilder;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class SavedFieldsList {
    private static final String PREFS_NAME = "savedFieldsList";
    private List<SavedField> savedFields;
    private String savedFieldsJSON = "[{\"bombPositions\":[0,0,0,0,0,0,0,0,0,-1,-1,0,0,-1,-1,0,-1,0,0,-1,-1,0,0,-1,-1,0,0,0,0,0,0,-1,0,-1,0,0,0,0,-1,0,0,0,-1,0,0,-1,0,0,0,0,0,-1,-1,0,0,0,0,0,0,0,0,0,0,0],\"height\":8,\"id\":\"34885c2c-c446-4756-b3ea-ce71d30fded2\",\"name\":\"example\",\"portraitMode\":true,\"width\":8}]";
    Gson gson = new Gson();

    public void save(Context context) {
        savedFieldsJSON = gson.toJson(savedFields);
        SharedPreferences.Editor editor = context
                .getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
                .edit();
        editor.putString("savedFieldsJSON", savedFieldsJSON);
        editor.apply();
    }

    public void load(Context context) {
        SharedPreferences prefs = context
                .getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        savedFieldsJSON = prefs.getString("savedFieldsJSON", savedFieldsJSON);
        Type type = new TypeToken<List<SavedField>>(){}.getType();
        savedFields = gson.fromJson(savedFieldsJSON, type);
        if (savedFields == null) {
            savedFields = new ArrayList<>();
        }
    }

    public void addField(Context context, SavedField field) {
        load(context);
        savedFields.add(field);
        save(context);
    }

    public void removeField(Context context, int pos) {
        load(context);
        savedFields.remove(pos);
        save(context);
    }

    public boolean containsField(Context context, String name) {
        load(context);
        for (SavedField field: savedFields) {
            if (field.name.equals(name)) {
                return true;
            }
        }
        return false;
    }

    public List<SavedField> getFields() {
        return savedFields;
    }
}
