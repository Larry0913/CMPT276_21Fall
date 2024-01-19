package ca.cmpt276.gamemodule.Model;

import java.util.ArrayList;

public class GameManager {
    private ArrayList<Game> gameList = new ArrayList<Game>();

    public ArrayList<Game> getGameList() {
        return gameList;
    }

    public void addGame(Game newGame){
        gameList.add(newGame);
    }

    public void removeGame(int index){
        if(index<0)
            throw new IndexOutOfBoundsException("Number of game you want delete must be 0 or more.");
        else
            gameList.remove(index);
    }



}
