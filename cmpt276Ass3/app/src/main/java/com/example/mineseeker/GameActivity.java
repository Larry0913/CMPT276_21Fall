package com.example.mineseeker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.mineseeker.model.CellInGame;
import com.example.mineseeker.model.GameConfig;
import com.example.mineseeker.model.MineSeekerGame;

import java.util.ArrayList;

public class GameActivity extends AppCompatActivity {
    private GameConfig config = GameConfig.getInstance();
    private TableLayout gameBoardView;
    private MineSeekerGame game;
    private TextView foundTextView;
    private TextView scannedCountTextView;
    private TextView timeUsedTextView;

    /**
     * Read the game configuration set by the user in the settings Activity from SharedPreferences
     */
    public void loadConfigFromSharedPreference(SharedPreferences preferences, Context context) {
        boolean custom = preferences.getBoolean(context.getString(R.string.setting_use_custom_setting), false);
        if (custom) {
            config.setMines(Integer.parseInt(preferences.getString(context.getString(R.string.setting_custom_game_mines), "6")));
            config.setRows(Integer.parseInt(preferences.getString(context.getString(R.string.setting_custom_game_row), "4")));
            config.setColumns(Integer.parseInt(preferences.getString(context.getString(R.string.setting_custom_game_col), "6")));
        } else {
            String[] sizeInfo = preferences.getString(context.getString(R.string.setting_game_board), "4 x 6").split(" x ");
            config.setRows(Integer.parseInt(sizeInfo[0]));
            config.setColumns(Integer.parseInt(sizeInfo[1]));
            config.setMines(Integer.parseInt(preferences.getString(context.getString(R.string.setting_game_mines), "6")));
        }
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            layoutCells();
        }
    }

    private void layoutCells() {
        int perHeight = gameBoardView.getMeasuredHeight() / config.getRows();
        int perWidth = gameBoardView.getMeasuredWidth() / config.getColumns();

        TableRow.LayoutParams layoutParams = new TableRow.LayoutParams();
        layoutParams.width = perWidth - 5;
        layoutParams.height = perHeight - 5;
        //layoutParams.setMargins(10, 10, 10, 10);

        for (int i = 0; i < config.getRows(); i++) {
            TableRow row = (TableRow) gameBoardView.getChildAt(i);
            for (int j = 0; j < config.getColumns(); j++) {
                View view = row.getChildAt(j);
                //View view = row.getChildAt(i * config.getColumns() + j);
                view.setLayoutParams(layoutParams);
            }
        }
    }

    private void prepareGameBoard() {
//        ArrayAdapter<CellInGame> listAdapter = new ArrayAdapter<CellInGame>(this, R.layout.game_board_cell_layout);
//        for (ArrayList<CellInGame> cells : game.getCells()) {
//            listAdapter.addAll(cells);
//        }

        gameBoardView.removeAllViews();

        //gameBoardView.setRowCount(config.getRows());
        //gameBoardView.setColumnCount(config.getColumns());

        for (int i = 0; i < config.getRows(); i++) {
            TableRow tableRow = new TableRow(this);

            for (int j = 0; j < config.getColumns(); j++) {
                CellInGame cell = game.getCells().get(i).get(j);

                View view = View.inflate(this, R.layout.game_cell_layout, null);
                Button button = view.findViewById(R.id.cell_button);
                view.setTag(R.string.rows, i);
                view.setTag(R.string.cols, j);
                tableRow.addView(view);
                //gameBoardView.addView(view);
                displayCell(cell, button, view.findViewById(R.id.cell_image));
                button.setOnClickListener(v -> {
                    int row = (int) view.getTag(R.string.rows);
                    int col = (int) view.getTag(R.string.cols);
                    scanCell(view, row, col);
                });
            }
            gameBoardView.addView(tableRow);
        }
        foundTextView.setText(String.format(getString(R.string.game_found_mines_text), game.getScannedMines(), config.getMines()));
        scannedCountTextView.setText(String.format(getString(R.string.game_scanned_count_text), game.getScannedMines()));
        timeUsedTextView.setText(String.format(getString(R.string.game_time_used_text), game.getUsedTimeString()));
    }

    private void displayCell(CellInGame cell, Button button, ImageView imageView) {
        if (!cell.isScanned()) return;
        ;
        if (cell.isMine()) {
            button.setVisibility(View.GONE);
            imageView.setImageResource(R.drawable.bomb);
            imageView.setVisibility(View.VISIBLE);
            Vibrator vibrator = (Vibrator) this.getSystemService(this.VIBRATOR_SERVICE);
            long[] patter = {1000, 1000, 2000, 50};
            vibrator.vibrate(patter, 0);
        } else {
            button.setText(cell.getMinesCount() + "");
        }
    }

    private void scanCell(View view, int row, int col) {
        Button button = view.findViewById(R.id.cell_button);
        ImageView imageView = view.findViewById(R.id.cell_image);

        CellInGame cell = game.scan(row, col);
        displayCell(cell, button, imageView);

        foundTextView.setText(String.format(getString(R.string.game_found_mines_text), game.getScannedMines(), config.getMines()));
        scannedCountTextView.setText(String.format(getString(R.string.game_scanned_count_text), game.getScannedCount()));
        timeUsedTextView.setText(String.format(getString(R.string.game_time_used_text), game.getUsedTimeString()));


        if (game.isGameOver()) {
            gameOver();
        }
    }

    private void gameOver() {
        new AlertDialog.Builder(this).setMessage("Congratulations,you win this game!")
                .setTitle("Game over")
                .setPositiveButton("menu", (dialog, which) -> finish())
                .setNegativeButton("restart", (dialog, which) -> restartGame())
                .create()
                .show();
    }

    private void restartGame() {
        prepareGameBoard();

    }

    Runnable timeTick = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        foundTextView = findViewById(R.id.game_found_mines_textview);
        scannedCountTextView = findViewById(R.id.game_scanned_count_textview);
        timeUsedTextView = findViewById(R.id.game_time_used_textview);
        gameBoardView = findViewById(R.id.game_grid);

        timeTick = () -> {
            timeUsedTextView.setText(String.format(getString(R.string.game_time_used_text), game.getUsedTimeString()));
            gameBoardView.postDelayed(timeTick, 1000);
        };
        gameBoardView.postDelayed(timeTick, 1000);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        loadConfigFromSharedPreference(PreferenceManager.getDefaultSharedPreferences(this), this);
        game = new MineSeekerGame();
        game.start(config);
        prepareGameBoard();
    }

    @Override
    protected void onResume() {
        super.onResume();
        prepareGameBoard();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("rows", config.getRows());
        outState.putInt("columns", config.getColumns());
        outState.putInt("scannedMines", game.getScannedMines());
        outState.putInt("scannedCount", game.getScannedCount());
        outState.putInt("startTime", game.getStartTime().getDate());
        outState.putSerializable("cells", game.getCells());
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState.containsKey("rows") == false) return;
        config.setRows(savedInstanceState.getInt("rows"));
        config.setColumns(savedInstanceState.getInt("columns"));

        game.restore(
                config,
                savedInstanceState.getInt("scannedMines"),
                savedInstanceState.getInt("scannedCount"),
                savedInstanceState.getInt("startTime"),
                (ArrayList<ArrayList<CellInGame>>) savedInstanceState.getSerializable("cells")
        );
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}