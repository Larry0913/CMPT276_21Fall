package com.example.mineseeker.model;

import java.io.Serializable;

public class CellInGame implements Serializable {
    /**
     * Whether the current cell has been scanned
     */
    private boolean scanned;
    /**
     * Does the current cell contain Mine
     */
    private boolean isMine;
    /**
     * The sum of Mines in the row and column of the current cell
     */
    private int minesCount;


    public CellInGame() {
    }

    public boolean isScanned() {
        return scanned;
    }

    public void setScanned(boolean scanned) {
        this.scanned = scanned;
    }

    public boolean isMine() {
        return isMine;
    }

    public void setMine(boolean mine) {
        isMine = mine;
    }

    public int getMinesCount() {
        return minesCount;
    }

    public void setMinesCount(int minesCount) {
        this.minesCount = minesCount;
    }

    @Override
    public String toString() {
        return "Cell";
    }
}
