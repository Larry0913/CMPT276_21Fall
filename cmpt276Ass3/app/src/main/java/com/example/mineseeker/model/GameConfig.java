package com.example.mineseeker.model;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.example.mineseeker.R;

public class GameConfig {
    private static GameConfig instance;
    private int rows;
    private int columns;
    private int mines;

    public static GameConfig getInstance() {
        if (instance == null) {
            instance = new GameConfig();
        }
        return instance;
    }

    private GameConfig() {

    }



    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public int getColumns() {
        return columns;
    }

    public void setColumns(int columns) {
        this.columns = columns;
    }

    public int getMines() {
        return mines;
    }

    public void setMines(int mines) {
        this.mines = mines;
    }
}
