package com.example.minesweeperbuilder;

public enum Difficulty {
    BEGINNER(8, 8, 10),
    INTERMEDIATE(16, 16, 40),
    EXPERT(16, 30, 99);

    public final int width;
    public final int height;
    public final int bombs;

    Difficulty(int width, int height, int bombs) {
        this.width = width;
        this.height = height;
        this.bombs = bombs;
    }
}