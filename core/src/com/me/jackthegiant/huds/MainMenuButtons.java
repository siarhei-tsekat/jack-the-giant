package com.me.jackthegiant.huds;

import static com.me.jackthegiant.scenes.Gameplay.W_HEIGHT;
import static com.me.jackthegiant.scenes.Gameplay.W_WIDTH;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.me.jackthegiant.GameMain;
import com.me.jackthegiant.GameManager;
import com.me.jackthegiant.scenes.Gameplay;
import com.me.jackthegiant.scenes.Highscore;
import com.me.jackthegiant.scenes.OptionsScreen;

public class MainMenuButtons {
    private GameMain game;
    private Stage stage;
    private Viewport gameViewPort;
    private ImageButton playBtn;
    private ImageButton highscoreBtn;
    private ImageButton optionsBtn;
    private ImageButton quitBtn;
    private ImageButton musicBtn;

    public MainMenuButtons(GameMain game) {
        this.game = game;
        gameViewPort = new FillViewport(W_WIDTH, W_HEIGHT, new OrthographicCamera());

        stage = new Stage(gameViewPort, game.batch);

        Gdx.input.setInputProcessor(stage);

        playBtn = new ImageButton(new SpriteDrawable(new Sprite(new Texture("btns/start_game.png"))));
        highscoreBtn = new ImageButton(new SpriteDrawable(new Sprite(new Texture("btns/highscore.png"))));
        optionsBtn = new ImageButton(new SpriteDrawable(new Sprite(new Texture("btns/options.png"))));
        quitBtn = new ImageButton(new SpriteDrawable(new Sprite(new Texture("btns/quit.png"))));
        musicBtn = new ImageButton(new SpriteDrawable(new Sprite(new Texture("btns/music_on.png"))));

        playBtn.setPosition(W_WIDTH / 2f, W_HEIGHT / 2f + 50, Align.center);
        highscoreBtn.setPosition(W_WIDTH / 2f, W_HEIGHT / 2f - 20, Align.center);
        optionsBtn.setPosition(W_WIDTH / 2f, W_HEIGHT / 2f - 90, Align.center);
        quitBtn.setPosition(W_WIDTH / 2f, W_HEIGHT / 2f - 160, Align.center);
        musicBtn.setPosition(W_WIDTH - 13, 13, Align.bottomRight);

        stage.addActor(playBtn);
        stage.addActor(highscoreBtn);
        stage.addActor(optionsBtn);
        stage.addActor(quitBtn);
        stage.addActor(musicBtn);


        playBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                GameManager.getInstance().gameStartedFromMainMenu = true;
                game.setScreen(new Gameplay(game));
            }
        });

        highscoreBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new Highscore(game));
            }
        });

        optionsBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new OptionsScreen(game));
            }
        });

        musicBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {

                if (GameManager.getInstance().gameData().isMusicOn()) {
                    GameManager.getInstance().gameData().setMusicOn(false);
                    GameManager.getInstance().stopMusic();
                } else {
                    GameManager.getInstance().gameData().setMusicOn(true);
                    GameManager.getInstance().playMusic();
                }

                GameManager.getInstance().saveData();
            }
        });

        checkMusic();
    }

    public void checkMusic() {
        if (GameManager.getInstance().gameData().isMusicOn()) {
            GameManager.getInstance().playMusic();
        }
    }

    public Stage getStage() {
        return stage;
    }
}
