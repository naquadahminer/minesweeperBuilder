package com.example.minesweeperbuilder;

import java.util.List;

public class BuildingGame {
    private MineGrid mineGrid;
    private int numberOfBombs;

    public BuildingGame(int height, int width) {
        this.mineGrid = new MineGrid(height, width, true);
        this.numberOfBombs = 0;
    }

    public void handleCellClick(Cell cell){
        int index = mineGrid.getCells().indexOf(cell);
        if (cell.getValue() != Cell.BOMB) {
            mineGrid.getCells().set(index, new Cell(Cell.BOMB, true));
//            List<Cell> adjacentCells = adjacentCells(x, y);
//            int countBombs = 0;
//            for (Cell cell:adjacentCells){
//                if (cell.getValue() == Cell.BOMB){
//                    countBombs++;
//                }
//            }
//            if (countBombs > 0) {
//                cells.set(toIndex(x, y), new Cell(countBombs, false));
//            }
        } else {
            mineGrid.getCells().set(index, new Cell(Cell.BLANK, true));
        }
    }
}
