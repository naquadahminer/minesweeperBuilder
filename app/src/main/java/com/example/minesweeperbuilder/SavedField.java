package com.example.minesweeperbuilder;

import java.util.List;
import java.util.UUID;

public class SavedField {
    public String name;
    String id;
    public int width;
    public int height;
    public int bombCount;
    public boolean portraitMode;
    public List<Integer> bombPositions;

    public SavedField(String name, int width, int height, int bombCount, boolean portraitMode, List<Integer> bombPositions) {
        this.name = name;
        this.id = UUID.randomUUID().toString();
        this.width = width;
        this.height = height;
        this.bombCount = bombCount;
        this.portraitMode = portraitMode;
        this.bombPositions = bombPositions;
    }

    public SavedField() {
        // empty constructor for firestore
    }
}
