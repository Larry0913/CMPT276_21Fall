package ca.cmpt276.gamemodule.Model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Game {
    private final ArrayList<Integer> gameScore = new ArrayList<>();
    private final ArrayList<Integer> winIndex = new ArrayList<>();
    private final LocalDateTime localDateTime = LocalDateTime.now();

    DateTimeFormatter dt = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public String getDate(){
        return dt.format(localDateTime);
    }

    public ArrayList<Integer> getGameScore(){
        return gameScore;
    }

    public ArrayList<Integer> getWinIndex(){
        return winIndex;
    }

    public void addGameScore(int score){
        gameScore.add(score);
    }

    public ArrayList<Integer> createWinIndex(){
        int maxScore = 0;
        for(int i = 0;i < gameScore.size();i++){
            if(maxScore < gameScore.get(i))
                maxScore = gameScore.get(i);
        }
        for(int j = 0;j < gameScore.size();j++){
            if (maxScore == gameScore.get(j))
                winIndex.add(j);
        }
        return winIndex;
    }
}
