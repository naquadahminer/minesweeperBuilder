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

    public byte[] getSimplifiedGrid() {
        byte[] bitArray = new byte[(mineGrid.getCells().size() + 7) / 8];
        int currIndex = 0;
        for (Cell cell: mineGrid.getCells()) {
            if (cell.getValue() == Cell.BOMB) {
                bitArray[currIndex/8] |= 1 << (currIndex % 8);
            }
            currIndex++;
        }
        return bitArray;
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
