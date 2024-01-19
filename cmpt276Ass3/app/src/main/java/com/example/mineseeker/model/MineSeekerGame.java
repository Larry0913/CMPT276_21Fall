package com.example.mineseeker.model;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class MineSeekerGame {
    private GameConfig config;
    private ArrayList<ArrayList<CellInGame>> cells;
    private Date startTime;

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    SimpleDateFormat dt = new SimpleDateFormat("HH:mm:ss");

    public int getScannedMines() {
        return scannedMines;
    }

    public int getScannedCount() {
        return scannedCount;
    }

    private int scannedMines = 0;
    private int scannedCount = 0;


    public MineSeekerGame() {

    }

    public void restart() {
        start(config);
    }

    public void restore(GameConfig config, int scannedMines, int scannedCount, int startTime, ArrayList<ArrayList<CellInGame>> cells) {
        this.config = config;
        this.scannedMines = scannedMines;
        this.scannedCount = scannedCount;
        this.startTime = new Date(startTime);
        this.cells = cells;
    }

    private void randomMines() {
        Random random = new Random();
        Set<Integer> indexes = new HashSet<>();
        int maxIndex = config.getColumns() * config.getRows();
        while (indexes.size() < config.getMines()) {
            int randomIndex = random.nextInt(maxIndex);
            if (!indexes.contains(randomIndex)) {
                indexes.add(randomIndex);
                int row = randomIndex / config.getColumns();
                int col = randomIndex % config.getColumns();
                cells.get(row).get(col).setMine(true);
            }
        }
    }

    public ArrayList<CellInGame> getAllCellList() {
        ArrayList<CellInGame> cellList = new ArrayList<>();
        for (ArrayList<CellInGame> list : cells) {
            cellList.addAll(list);
        }
        return cellList;
    }

    public void start(GameConfig gameConfig) {
        config = gameConfig;
        cells = new ArrayList<>();
        for (int i = 0; i < config.getRows(); i++) {
            ArrayList<CellInGame> row = new ArrayList<>();
            for (int j = 0; j < config.getColumns(); j++) {
//                row.add(new CellInGame(i, j));
                row.add(new CellInGame());
            }
            cells.add(row);
        }
        randomMines();
        startTime = new Date();
    }

    public String getUsedTimeString() {
        Date now = new Date();
        return dt.format(new Date(now.getTime() - startTime.getTime()));
    }

    public boolean isGameOver() {
        return scannedMines == config.getMines();
    }

    public CellInGame scan(int row, int col) {
        CellInGame cell = cells.get(row).get(col);
        if (cell.isScanned()) return cell;

        cell.setScanned(true);
        scannedCount++;
        if (cell.isMine()) scannedMines++;
        else {
            cell.setMinesCount(countMinesInRow(row) + countMinesInColumn(col));
        }
        return cell;
    }

    private int countMinesInRow(int row) {
        int count = 0;
        ArrayList<CellInGame> rowCells = cells.get(row);
        for (int i = 0; i < rowCells.size(); i++) {
            CellInGame cell = rowCells.get(i);
            if (cell.isMine()) count++;
        }
        return count;
    }

    private int countMinesInColumn(int col) {
        int count = 0;

        for (int i = 0; i < cells.size(); i++) {
            CellInGame cell = cells.get(i).get(col);
            if (cell.isMine()) count++;
        }
        return count;
    }

    public ArrayList<ArrayList<CellInGame>> getCells() {
        return cells;
    }
}