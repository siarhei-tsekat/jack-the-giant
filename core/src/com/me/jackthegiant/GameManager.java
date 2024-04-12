package com.me.jackthegiant;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Base64Coder;
import com.badlogic.gdx.utils.Json;

public class GameManager {

    private static GameManager instance = new GameManager();

    public static GameManager getInstance() {
        return instance;
    }

    private GameManager() {
    }

    public boolean gameStartedFromMainMenu;
    public boolean isPaused = true;

    public int lifeScore;
    public int coinScore;
    public int score;

    private GameData gameData;
    private Json json = new Json();
    private FileHandle fileHandle = Gdx.files.local("bin/gamedata.json");

    public void initializeData() {
        if (!fileHandle.exists()) {
            gameData = new GameData();

            gameData.setHighScore(0);
            gameData.setCoinHighScore(0);
            gameData.setEasyDifficulty(false);
            gameData.setMediumDifficulty(true);
            gameData.setHardDifficulty(false);

            gameData.setMusicOn(false);

            saveData();

        } else {

            loadData();
        }
    }

    public void saveData() {
        if (gameData != null) {
//            fileHandle.writeString(json.prettyPrint(gameData), false);
            fileHandle.writeString(Base64Coder.encodeString(json.prettyPrint(gameData)), false);
        }
    }

    public void loadData() {
//        gameData = json.fromJson(GameData.class, fileHandle.readString());
        gameData = json.fromJson(GameData.class, Base64Coder.decodeString(fileHandle.readString()));
    }

    public void checkForNewHighScores() {
        int oldHighScore = gameData.getHighScore();
        int oldCoinScore = gameData.getCoinHighScore();

        if (oldHighScore < score) {
            gameData.setHighScore(score);
        }

        if (oldCoinScore < coinScore) {
            gameData.setCoinHighScore(coinScore);
        }

        saveData();
    }

    public GameData gameData() {
        return gameData;
    }
}
