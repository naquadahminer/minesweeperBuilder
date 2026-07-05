package com.example.minesweeperbuilder;

import java.util.UUID;

public class SavedField {
    public String name;
    String id;
    public int width;
    public int height;
    public boolean portraitMode;
    public int[] bombPositions;

    public SavedField(String name, int width, int height, boolean portraitMode, int[] bombPositions) {
        this.name = name;
        this.id = UUID.randomUUID().toString();
        this.width = width;
        this.height = height;
        this.portraitMode = portraitMode;
        this.bombPositions = bombPositions;
    }
}
