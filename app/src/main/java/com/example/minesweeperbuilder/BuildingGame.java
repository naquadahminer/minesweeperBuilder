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
            numberOfBombs += 1;
        } else {
            mineGrid.getCells().set(index, new Cell(Cell.BLANK, true));
            numberOfBombs -= 1;
        }
        calculateAdjacentCells(index);
    }

    public MineGrid getMineGrid() {
        return mineGrid;
    }

    private void calculateAdjacentCells(int index) {
        int[] cellPos = mineGrid.toXY(index);
        List<Cell> adjacentCells = mineGrid.adjacentCells(cellPos[0], cellPos[1]);
        adjacentCells.add(mineGrid.getCells().get(index));
        for (Cell adjCell:adjacentCells){
            int[] adjCellPos = mineGrid.toXY(mineGrid.getCells().indexOf(adjCell));
            int adjCellBombCount = mineGrid.countBombs(adjCellPos[0], adjCellPos[1]);
            if (adjCell.getValue() != Cell.BOMB) {
                mineGrid.getCells().set(mineGrid.getCells().indexOf(adjCell), new Cell(adjCellBombCount, true));
            }
        }
    }

    public int getNumberOfBombs() {
        return numberOfBombs;
    }

    public int[] getSimplifiedGrid() {
        int[] simplifiedField = new int[mineGrid.getCells().size()];
        for (Cell cell: mineGrid.getCells()) {
            if (cell.getValue() != Cell.BOMB) {
                simplifiedField[mineGrid.getCells().indexOf(cell)] = 0;
            } else {
                simplifiedField[mineGrid.getCells().indexOf(cell)] = -1;
            }
        }
        return simplifiedField;
    }
}
