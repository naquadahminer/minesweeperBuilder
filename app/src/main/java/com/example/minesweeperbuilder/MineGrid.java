package com.example.minesweeperbuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MineGrid {
    private List<Cell> cells;
    private int height, width;

    public MineGrid(int height, int width){
        this.height = height;
        this.width = width;
        cells = new ArrayList<>();

        for (int i = 0; i < height*width; i++) {
            cells.add(new Cell(Cell.BLANK));
        }
    }

    public void generateGrid(int numberBombs, int clickIndex){
        int bombsPlaced = 0;
        while(bombsPlaced < numberBombs){
            int x = new Random().nextInt(width);
            int y = new Random().nextInt(height);

            int index = toIndex(x,y);
            if(cells.get(index).getValue() == Cell.BLANK && index != clickIndex){
                cells.set(index, new Cell(Cell.BOMB));
                bombsPlaced++;
            }
        }

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                if (cellAt(x, y).getValue() != Cell.BOMB){
                    List<Cell> adjacentCells = adjacentCells(x, y);
                    int countBombs = 0;
                    for (Cell cell:adjacentCells){
                        if (cell.getValue() == Cell.BOMB){
                            countBombs++;
                        }
                    }
                    if (countBombs > 0) {
                        cells.set(toIndex(x, y), new Cell(countBombs));
                    }
                }
            }
        }
    }

    public List<Cell> adjacentCells(int x, int y){
        List<Cell> adjacentCells = new ArrayList<>();

        List<Cell> cellsList = new ArrayList<>();
        cellsList.add(cellAt(x-1, y-1));
        cellsList.add(cellAt(x-1, y));
        cellsList.add(cellAt(x-1, y+1));
        cellsList.add(cellAt(x, y-1));
        cellsList.add(cellAt(x, y+1));
        cellsList.add(cellAt(x+1, y-1));
        cellsList.add(cellAt(x+1, y));
        cellsList.add(cellAt(x+1, y+1));

        for (Cell cell: cellsList){
            if (cell != null){
                adjacentCells.add(cell);
            }
        }
        return adjacentCells;
    }

    public List<Cell> adjacentFlagged(int x, int y) {
        List<Cell> adjacentFlagged = new ArrayList<>();
        List<Cell> adjacentCells = adjacentCells(x, y);

        for (Cell cell: adjacentCells) {
            if(cell.isFlagged()) {
                adjacentFlagged.add(cell);
            }
        }

        return adjacentFlagged;
    }

    public void setAllFlags() {
        for (Cell cell: cells) {
            if (cell.getValue() == Cell.BOMB) {
                cell.setFlagged(true);
            }
        }
    }

    public void revealAllBombs() {
        for (Cell cell: cells) {
            if (cell.getValue() == Cell.BOMB) {
                cell.setRevealed(true);
            }
        }
    }

    public int toIndex(int x, int y) {
        return x + (y*width);
    }

    public int[] toXY(int index){
        int y = index / width;
        int x = index - (y * width);
        return new int[]{x,y};
    }

    public Cell cellAt(int x, int y){
        if (x < 0 || x >= width || y < 0 || y >= height){
            return null;
        }
        return cells.get(toIndex(x, y));
    }

    public List<Cell> getCells() {
        return cells;
    }
}
