package com.example.minesweeperbuilder;

import java.util.List;

public class BuildingGame extends Game {

    public BuildingGame(int height, int width, int numberOfBombs) {
        super(height, width, numberOfBombs, true);
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
}
