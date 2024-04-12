package com.me.jackthegiant;

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


}
