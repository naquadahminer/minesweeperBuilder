package com.example.minesweeperbuilder;

import java.util.ArrayList;
import java.util.List;

public class Game {
    protected MineGrid mineGrid;
    protected int numberOfBombs;

    public Game(int height, int width, int numberOfBombs, boolean isBuildingMode) {
        this.mineGrid = new MineGrid(height, width, isBuildingMode);
        this.numberOfBombs = numberOfBombs;
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

    public List<Integer> getSimplifiedGridList() {
        List<Integer> simplifiedField = new ArrayList<Integer>();
        for (Cell cell: mineGrid.getCells()) {
            if (cell.getValue() != Cell.BOMB) {
                simplifiedField.add(mineGrid.getCells().indexOf(cell), 0);
            } else {
                simplifiedField.add(mineGrid.getCells().indexOf(cell), -1);
            }
        }
        return simplifiedField;
    }

    public int getNumberOfBombs() {
        return numberOfBombs;
    }

    public MineGrid getMineGrid() {
        return mineGrid;
    }

}
