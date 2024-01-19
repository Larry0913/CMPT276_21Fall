package ca.cmpt276.as2.model;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializer;
import com.google.gson.reflect.TypeToken;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class GameManager {
    private static GameManager instance;

    private static void addMockData() {
        GameManager gameManager = getInstance();

        PlayerScore playerScore1 = new PlayerScore(4, 50, 2);
        PlayerScore playerScore2 = new PlayerScore(5, 20, 3);
        PlayerScore playerScore3 = new PlayerScore(2, 42, 2);
        gameManager.addGame(new Game(LocalDateTime.of(2021, 9, 21, 19, 21, 30), playerScore1, playerScore2));
        gameManager.addGame(new Game(LocalDateTime.of(2021, 8, 11, 16, 21, 30), playerScore1, playerScore3));
        gameManager.addGame(new Game(LocalDateTime.of(2021, 7, 25, 18, 21, 30), playerScore2, playerScore2));
        gameManager.addGame(new Game(LocalDateTime.of(2021, 10, 12, 11, 21, 30), playerScore2, playerScore1));
        gameManager.addGame(new Game(LocalDateTime.of(2021, 6, 8, 12, 21, 30), playerScore3, playerScore1));
    }

    public static GameManager getInstance() {
        if (instance == null) {
            instance = new GameManager();
//            addMockData();
        }
        return instance;
    }

    private GameManager() {

    }

    private ArrayList<Game> gameList = new ArrayList<Game>();

    public ArrayList<Game> getGameList() {
        return gameList;
    }

    public void addGame(Game newGame) {
        gameList.add(newGame);
    }

    public void removeGame(int index) {
        if (index < 0)
            throw new IndexOutOfBoundsException("Number of game you want delete must be 0 or more.");
        else
            gameList.remove(index);
    }

    public void save(Context context) {
        SharedPreferences preferences = context.getSharedPreferences("games", Context.MODE_PRIVATE);
        // LocalDateTime cannot covert to json if do not  registerTypeAdapter
        // https://www.javaguides.net/2019/11/gson-localdatetime-localdate.html
        Gson gson = new GsonBuilder().registerTypeAdapter(LocalDateTime.class, (JsonSerializer<LocalDateTime>) (src, typeOfSrc, context1) -> new JsonPrimitive(src.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))).create();
        String json = gson.toJson(gameList);
        preferences.edit().clear().putString("games", json).commit();

    }

    public void load(Context context) {
        SharedPreferences preferences = context.getSharedPreferences("games", Context.MODE_PRIVATE);
        // https://www.javaguides.net/2019/11/gson-localdatetime-localdate.html
        Gson gson = new GsonBuilder().registerTypeAdapter(LocalDateTime.class, (JsonDeserializer<LocalDateTime>) (json, type, jsonDeserializationContext) -> {
            String datetime = json.getAsJsonPrimitive().getAsString();
            return LocalDateTime.parse(datetime, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        }).create();
        gameList = gson.fromJson(preferences.getString("games", "[]"), new TypeToken<ArrayList<Game>>() {
        }.getType());
        for (Game game : gameList) {
            game.createWinIndex();
        }
    }
}
