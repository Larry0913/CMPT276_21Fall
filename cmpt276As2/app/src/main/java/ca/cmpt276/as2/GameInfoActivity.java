package ca.cmpt276.as2;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;

import ca.cmpt276.as2.databinding.ActivityGameInfoBinding;
import ca.cmpt276.as2.model.Game;
import ca.cmpt276.as2.model.GameManager;
import ca.cmpt276.as2.model.PlayerScore;

public class GameInfoActivity extends AppCompatActivity implements TextWatcher {
    public static final String PARAM_GAME_INDEX = "Game";
    private Game game;
    private int gameIndex = -1;
    private ActivityGameInfoBinding binding;
    private int player1NumberCards = 0;
    private int player2NumberCards = 0;
    private int player1SumPoints = 0;
    private int player2SumPoints = 0;
    private int player1NumberWagers = 0;
    private int player2NumberWagers = 0;
    private boolean anyThingChanged = false;
    int player1Score = 0;
    int player2Score = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityGameInfoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //setup actionBar,show back button
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        //read intent extra,if no extra , show new game screen,else show edit screen
        gameIndex = getIntent().getIntExtra(PARAM_GAME_INDEX, -1);
        if (gameIndex == -1) {
            setTitle("New Game Score");
            binding.gameResult.setText("-");
            game = new Game();
        } else {
            setTitle("Edit Game Score");
            game = GameManager.getInstance().getGameList().get(gameIndex);
            binding.gameDt.setText(game.getDate());

            binding.player1NumberCards.setText(game.getGameScore().get(0).getNumOfCards() + "");
            binding.player2NumberCards.setText(game.getGameScore().get(1).getNumOfCards() + "");

            binding.player1SumPoints.setText(game.getGameScore().get(0).getSumOfPoints() + "");
            binding.player2SumPoints.setText(game.getGameScore().get(1).getSumOfPoints() + "");

            binding.player1NumberWagers.setText(game.getGameScore().get(0).getNumOfWagerCards() + "");
            binding.player2NumberWagers.setText(game.getGameScore().get(1).getNumOfWagerCards() + "");

            binding.player1Scores.setText(game.getGameScore().get(0).getTotalScore() + "");
            binding.player2Scores.setText(game.getGameScore().get(1).getTotalScore() + "");

            if (game.getWinIndex() == -1) {
                binding.gameResult.setText("Tie Game");
            } else {
                binding.gameResult.setText("Winner is Player" + (game.getWinIndex() + 1));
            }
        }
        binding.player1NumberCards.addTextChangedListener(this);
        binding.player2NumberCards.addTextChangedListener(this);

        binding.player1SumPoints.addTextChangedListener(this);
        binding.player2SumPoints.addTextChangedListener(this);

        binding.player1NumberWagers.addTextChangedListener(this);
        binding.player2NumberWagers.addTextChangedListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.game_info_menu, menu);
        if (gameIndex >= 0) {
            menu.findItem(R.id.menu_delete).setVisible(true);
        }
        return super.onCreateOptionsMenu(menu);
    }

    public void cancel() {
        if (anyThingChanged == false) {
            this.finish(); // back button
        } else {
            new AlertDialog.Builder(this)
                    .setTitle("Confirm")
                    .setMessage("you have changed something, \nif you exit, Nothing will be save.")
                    .setNegativeButton("cancel", null)
                    .setPositiveButton("exit", (dialog, which) -> {
                        finish();
                    })
                    .show();
        }
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            cancel();
            return true;
        }
        return super.onKeyUp(keyCode, event);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                cancel();
                return true;
            case R.id.menu_save:
                save();
                return true;
            case R.id.menu_delete:
                delete();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void save() {
        if (calcGameResult() == false) {
            Snackbar.make(this.binding.getRoot(), "Game info not correct,save failed!", Snackbar.LENGTH_LONG).show();
            return;
        }
        game.getGameScore().clear();
        game.addScore(new PlayerScore(player1NumberCards, player1SumPoints, player1NumberWagers));
        game.addScore(new PlayerScore(player2NumberCards, player2SumPoints, player2NumberWagers));

        if (gameIndex == -1) {
            GameManager.getInstance().addGame(game);
        }
        GameManager.getInstance().save(this);

        Toast.makeText(this, "Game info saved!", Toast.LENGTH_LONG).show();
        finish();
    }

    private void delete() {
        new AlertDialog.Builder(this)
                .setTitle("Warning")
                .setMessage("Delete this game ,Sure ? This operation can not be undo!")
                .setNegativeButton("No", null)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        GameManager.getInstance().removeGame(gameIndex);
                        finish();
                    }
                }).show();
    }

    private boolean calcGameResult() {
        try {
            player1NumberCards = Integer.parseInt(binding.player1NumberCards.getText().toString());
            player2NumberCards = Integer.parseInt(binding.player2NumberCards.getText().toString());
            player1SumPoints = Integer.parseInt(binding.player1SumPoints.getText().toString());
            player2SumPoints = Integer.parseInt(binding.player2SumPoints.getText().toString());
            player1NumberWagers = Integer.parseInt(binding.player1NumberWagers.getText().toString());
            player2NumberWagers = Integer.parseInt(binding.player2NumberWagers.getText().toString());

            player1Score = PlayerScore.calcScore(player1NumberCards, player1SumPoints, player1NumberWagers);
            player2Score = PlayerScore.calcScore(player2NumberCards, player2SumPoints, player2NumberWagers);

            binding.player1Scores.setText(player1Score + "");
            binding.player2Scores.setText(player2Score + "");

            if (player1Score == player2Score) {
                binding.gameResult.setText("Tie Game");
            } else {
                binding.gameResult.setText("Winner is Player " + (player1Score > player2Score ? 1 : 2));
            }
            return true;
        } catch (NumberFormatException numberFormatException) {
            binding.player1Scores.setText("-");
            binding.player2Scores.setText("-");
            binding.gameResult.setText("");
            return false;
        } catch (Exception e) {
            Toast.makeText(this, "error:" + e.getMessage(), Toast.LENGTH_LONG).show();
            binding.player1Scores.setText("-");
            binding.player2Scores.setText("-");
            binding.gameResult.setText("");
            return false;
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        calcGameResult();
        anyThingChanged = true;
    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}