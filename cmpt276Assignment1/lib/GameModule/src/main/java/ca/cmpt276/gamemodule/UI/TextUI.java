package ca.cmpt276.gamemodule.UI;

import java.util.ArrayList;
import java.util.Scanner;

import ca.cmpt276.gamemodule.Model.Game;
import ca.cmpt276.gamemodule.Model.GameManager;
import ca.cmpt276.gamemodule.Model.PlayerScore;

public class TextUI {

    private final GameManager gameManager;
    private final Scanner in = new Scanner(System.in);

    public TextUI(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    public void listGames(GameManager gameManager){
        System.out.println("Games:");
        System.out.println("--------------");
        if(gameManager.getGameList().isEmpty()) {
            System.out.println("  No games");
        }
        for(int i = 0;i<gameManager.getGameList().size();i++) {
            Game tempGame = gameManager.getGameList().get(i);
            if (tempGame.getGameScore().size() == 1) {
                System.out.println((i + 1) + ". " + tempGame.getGameScore().get(0) + ", winner player(s): 1 (@" + tempGame.getDate() + ")");
            } else {
                    System.out.print((i + 1) + ". ");
                    System.out.print(tempGame.getGameScore().get(0));
                    for (int j = 1; j < tempGame.getGameScore().size() ; j++) {
                        System.out.print(" vs " + tempGame.getGameScore().get(j));
                    }
                    System.out.print(", winner player(s): ");

                    System.out.print(tempGame.getWinIndex().get(0) + 1);
                    for (int k = 1; k < tempGame.getWinIndex().size(); k++) {
                        System.out.print(", " + (tempGame.getWinIndex().get(k) + 1));
                    }
                    System.out.println(" " + "(@" + tempGame.getDate() + ")");
                }
            }
        System.out.println();
    }

    public Game newGame(){
        PlayerScore temp = new PlayerScore();
        Game singleGame = new Game();
        System.out.print("How many players? ");
        int numOfPlayers = in.nextInt();
        while (numOfPlayers <= 0||numOfPlayers>4) {
            System.out.println("Invalid value.");
            if(numOfPlayers<=0)
                System.out.println("Please enter a value that is 1 or greater.");
            else
                System.out.println("Please enter a value that is 4 or less.");
            System.out.print("How many players? ");
            numOfPlayers = in.nextInt();
        }
        for (int i = 0; i < numOfPlayers; i++) {
            int score;
            System.out.println("Player " + (i + 1));
            System.out.print("  How many cards? ");
            int numOfCards = in.nextInt();
            if (numOfCards == 0) {
                temp.setNumOfCards(numOfCards);
                score = temp.countScore();
                singleGame.addGameScore(score);
                continue;
            }
            while(numOfCards<0) {
                System.out.println("Invalid value.\n" + "Please enter a value that is 0 or greater.");
                System.out.print("  How many cards? ");
                numOfCards = in.nextInt();
            }
            temp.setNumOfCards(numOfCards);
            System.out.print("  Sum of cards? ");
            int sumOfCards = in.nextInt();
            while(sumOfCards<0) {
                System.out.println("Invalid value.\n" + "Please enter a value that is 0 or greater.");
                System.out.print("  Sum of cards? ");
                sumOfCards = in.nextInt();
            }
            temp.setSumOfPoints(sumOfCards);
            System.out.print("  How many wagers? ");
            int numOfWagerCards = in.nextInt();
            while(numOfWagerCards<0) {
                System.out.println("Invalid value.\n" + "Please enter a value that is 0 or greater.");
                System.out.print("  How many wagers? ");
                numOfWagerCards = in.nextInt();
            }
            temp.setNumOfWagerCards(numOfWagerCards);
            score = temp.countScore();
            singleGame.addGameScore(score);
        }
        System.out.println();
        System.out.println("Adding game:");
        if (singleGame.getGameScore().size() == 1) {
            System.out.println("  " + singleGame.getGameScore().get(0) + ", " + "winner player(s): 1 " + "(@" + singleGame.getDate() + ")");
            System.out.println();
        } else {
            System.out.print("  ");
            for (int j = 0; j < singleGame.getGameScore().size() - 1; j++) {
                System.out.print(singleGame.getGameScore().get(j) + " vs ");
            }

            System.out.print(singleGame.getGameScore().get(numOfPlayers - 1) + ", winner player(s): ");

            ArrayList<Integer> tempWinIndex = singleGame.createWinIndex();
            System.out.print(tempWinIndex.get(0) + 1);
            for (int k = 1; k < tempWinIndex.size(); k++) {
                System.out.print(", " + (tempWinIndex.get(k) + 1));
            }
            System.out.print(" " + "(@" + singleGame.getDate() + ")");
            System.out.println("\r\n");
        }
        return singleGame;
    }

    public int deleteGames() {
        System.out.print("Which game to delete (0 for none)? ");
        int deleteGameNum = in.nextInt();
        while (true) {
            if (deleteGameNum == 0) {
                System.out.println();
                return 0;
            }
            else if(deleteGameNum < 0 || deleteGameNum > gameManager.getGameList().size()) {
                while (deleteGameNum < 0 || deleteGameNum > gameManager.getGameList().size()) {
                    System.out.println("Invalid value.");
                    if (deleteGameNum < 0)
                        System.out.println("Please enter a value that is 0 or greater.");
                    else
                        System.out.println("Please enter a value that is " + gameManager.getGameList().size() + " or less.");
                    System.out.print("Which game to delete (0 for none)? ");
                    deleteGameNum = in.nextInt();
                }
            }
            else{
                System.out.println("Game deleted.");
                System.out.println();
                return deleteGameNum;
            }
        }
    }

    public void showMenu() {
        System.out.println("Menu:");
        System.out.println("--------------");
        System.out.println("1.List games");
        System.out.println("2.New game");
        System.out.println("3.Delete games");
        System.out.println("0.Exit");
        System.out.print(":  ");
        int checkNumber = in.nextInt();
        boolean condition = true;
        while (condition) {
            switch (checkNumber) {
                case 1: {
                    condition = false;
                    System.out.println();
                    listGames(gameManager);
                    System.out.println();
                }
                break;
                case 2: {
                    condition = false;
                    System.out.println();
                    gameManager.addGame(newGame());
                }
                break;
                case 3: {
                    condition = false;
                    System.out.println();
                    listGames(gameManager);
                    if(gameManager.getGameList().isEmpty())
                        break;
                    else {
                        int deleteNum = deleteGames();
                        if (deleteNum != 0)
                            gameManager.removeGame(deleteNum - 1);
                        else
                            break;
                    }
                }
                break;
                case 0: {
                    System.out.println("DONE!");
                    System.exit(0);
                }
                break;
                default: {
                    while (checkNumber < 0 || checkNumber > 3) {
                        if (checkNumber < 0)
                            System.out.println("Invalid value.\r\nPlease enter a value that is 0 or greater.");
                        else
                            System.out.println("Invalid value.\r\nPlease enter a value that is 3 or less.");
                        System.out.print(":  ");
                        checkNumber = in.nextInt();
                    }
                }
                break;
            }
        }
    }
}
