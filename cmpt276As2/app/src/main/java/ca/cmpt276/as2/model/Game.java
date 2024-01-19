package ca.cmpt276.as2.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;

public class Game {
    private final ArrayList<PlayerScore> gameScore = new ArrayList<>();
    private int winIndex = 0;
    private LocalDateTime localDateTime = LocalDateTime.now();

    protected static final DateTimeFormatter dt = DateTimeFormatter.ofPattern("MMM d@ h:mma");

    public void setLocalDateTime(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
    }

    public String getDate() {
        return dt.format(localDateTime);
    }

    public Game() {
        localDateTime = LocalDateTime.now();
    }

    public Game(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
    }

    public Game(LocalDateTime localDateTime, PlayerScore... scores) {
        this.localDateTime = localDateTime;
        Collections.addAll(this.gameScore, scores);
        createWinIndex();
    }

    public void addScore(PlayerScore playerScore) {
        gameScore.add(playerScore);
        createWinIndex();
    }

    protected int createWinIndex() {
        int maxScoreIndex = 0;
        winIndex = 0;

        for (int i = 0; i < gameScore.size(); i++) {
            int maxScore = gameScore.get(maxScoreIndex).getTotalScore();
            int score = gameScore.get(i).getTotalScore();
            //if maxScore == score and i != maxScoreIndex，it shows that there are more than one biggest score.
            // so ,this game may be tie.Suppose there are 4 players with scores of 1 2 2 3.
            // Before looping to player 4's score, the max score will appear 2 times
            if (maxScore == score && i != maxScoreIndex) {
                winIndex = -1;
            }

            if (maxScore < score) {
                maxScoreIndex = i;
                winIndex = maxScoreIndex;
            }
        }

        return winIndex;
    }

    public ArrayList<PlayerScore> getGameScore() {
        return gameScore;
    }

    /**
     * @return get Win Index,if tie game ,return -1
     */
    public int getWinIndex() {
        return winIndex;
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    @Override
    public String toString() {
        String gameResult = null;
        if (winIndex == -1) {
            gameResult = "tie game";
        } else {
            gameResult = String.format("Player %d won", winIndex + 1);
        }
        return String.format("%s - %s : %d vs %d", dt.format(localDateTime), gameResult, gameScore.get(0).getTotalScore(), gameScore.get(1).getTotalScore());
    }
}
