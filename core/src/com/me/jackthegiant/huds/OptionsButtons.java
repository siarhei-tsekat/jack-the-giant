package com.me.jackthegiant.huds;

import static com.me.jackthegiant.scenes.Gameplay.W_HEIGHT;
import static com.me.jackthegiant.scenes.Gameplay.W_WIDTH;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.me.jackthegiant.GameMain;
import com.me.jackthegiant.GameManager;
import com.me.jackthegiant.scenes.MainMenu;

public class OptionsButtons {

    private GameMain game;
    private Stage stage;
    private Viewport gameViewport;

    private ImageButton backBtn;
    private ImageButton easyBtn;
    private ImageButton mediumBtn;
    private ImageButton hardBtn;

    private Image sign;

    public OptionsButtons(GameMain game) {
        this.game = game;

        gameViewport = new FitViewport(W_WIDTH, W_HEIGHT, new OrthographicCamera());
        stage = new Stage(gameViewport, game.batch);

        Gdx.input.setInputProcessor(stage);

        easyBtn = new ImageButton(new SpriteDrawable(new Sprite(new Texture("btns/easy_btn.png"))));
        easyBtn.setPosition(W_WIDTH / 2f, W_HEIGHT / 2f + 40, Align.center);

        mediumBtn = new ImageButton(new SpriteDrawable(new Sprite(new Texture("btns/medium_btn.png"))));
        mediumBtn.setPosition(W_WIDTH / 2f, W_HEIGHT / 2f - 40, Align.center);

        hardBtn = new ImageButton(new SpriteDrawable(new Sprite(new Texture("btns/hard_btn.png"))));
        hardBtn.setPosition(W_WIDTH / 2f, W_HEIGHT / 2f - 120, Align.center);

        sign = new Image(new SpriteDrawable(new Sprite(new Texture("btns/check_sign.png"))));
        positionSign();


        backBtn = new ImageButton(new SpriteDrawable(new Sprite(new Texture("btns/back_btn.png"))));
        backBtn.setPosition(70, 17, Align.bottomRight);

        backBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new MainMenu(game));
            }
        });

        easyBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                changeDifficulty(0);
                sign.setPosition(W_WIDTH / 2f + 76, easyBtn.getY() + 13, Align.bottomLeft);
            }
        });

        mediumBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                changeDifficulty(1);
                sign.setPosition(W_WIDTH / 2f + 76, mediumBtn.getY() + 13, Align.bottomLeft);
            }
        });

        hardBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                changeDifficulty(2);
                sign.setPosition(W_WIDTH / 2f + 76, hardBtn.getY() + 13, Align.bottomLeft);
            }
        });

        stage.addActor(backBtn);
        stage.addActor(easyBtn);
        stage.addActor(mediumBtn);
        stage.addActor(hardBtn);
        stage.addActor(sign);
    }

    private void positionSign() {
        if (GameManager.getInstance().gameData().isEasyDifficulty()) {
            sign.setPosition(W_WIDTH / 2f + 76, easyBtn.getY() + 13, Align.bottomLeft);
        }
        if (GameManager.getInstance().gameData().isMediumDifficulty()) {
            sign.setPosition(W_WIDTH / 2f + 76, mediumBtn.getY() + 13, Align.bottomLeft);
        }

        if (GameManager.getInstance().gameData().isHardDifficulty()) {
            sign.setPosition(W_WIDTH / 2f + 76, hardBtn.getY() + 13, Align.bottomLeft);
        }
    }

    public void changeDifficulty(int difficulty) {
        switch (difficulty) {
            case 0:
                GameManager.getInstance().gameData().setEasyDifficulty(true);
                GameManager.getInstance().gameData().setMediumDifficulty(false);
                GameManager.getInstance().gameData().setHardDifficulty(false);
                break;
            case 1:
                GameManager.getInstance().gameData().setEasyDifficulty(false);
                GameManager.getInstance().gameData().setMediumDifficulty(true);
                GameManager.getInstance().gameData().setHardDifficulty(false);
                break;
            case 2:
                GameManager.getInstance().gameData().setEasyDifficulty(false);
                GameManager.getInstance().gameData().setMediumDifficulty(false);
                GameManager.getInstance().gameData().setHardDifficulty(true);
                break;
        }

        GameManager.getInstance().saveData();
    }

    public Stage getStage() {
        return stage;
    }
}
