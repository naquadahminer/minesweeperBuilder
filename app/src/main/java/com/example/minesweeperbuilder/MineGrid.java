package com.example.minesweeperbuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MineGrid {
    private List<Cell> cells;
    private int size;

    public MineGrid(int size){
        this.size = size;
        cells = new ArrayList<>();

        for (int i = 0; i < size*size; i++) {
            cells.add(new Cell(Cell.BLANK));
        }
    }

    public void generateGrid(int numberBombs){
        int bombsPlaced = 0;
        while(bombsPlaced < numberBombs){
            int x = new Random().nextInt(size);
            int y = new Random().nextInt(size);

            int index = toIndex(x,y);
            if(cells.get(index).getValue() == Cell.BLANK){
                cells.set(index, new Cell(Cell.BOMB));
                bombsPlaced++;
            }
        }

        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
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

    public void revealAllBombs() {
        for (Cell cell: cells) {
            if (cell.getValue() == Cell.BOMB) {
                cell.setRevealed(true);
            }
        }
    }

    public int toIndex(int x, int y) {
        return x + (y*size);
    }

    public int[] toXY(int index){
        int y = index / size;
        int x = index - (y * size);
        return new int[]{x,y};
    }

    public Cell cellAt(int x, int y){
        if (x < 0 || x >= size || y < 0 || y >= size){
            return null;
        }
        return cells.get(toIndex(x, y));
    }

    public List<Cell> getCells() {
        return cells;
    }
}
