package com.me.jackthegiant.huds;

import static com.me.jackthegiant.scenes.Gameplay.W_HEIGHT;
import static com.me.jackthegiant.scenes.Gameplay.W_WIDTH;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.me.jackthegiant.GameMain;
import com.me.jackthegiant.scenes.MainMenu;

public class UIHud {
    private GameMain game;
    private Stage stage;
    private Viewport gameViewPort;

    private Image coinImg;
    private Image lifeImg;
    private Image scoreImg;

    private Label coinLabel;
    private Label lifeLabel;
    private Label scoreLabel;

    private ImageButton pauseBtn;
    private ImageButton resumeBtn;
    private ImageButton quitBtn;

    private Image pausePanel;

    public UIHud(GameMain game) {
        this.game = game;

        gameViewPort = new FitViewport(W_WIDTH, W_HEIGHT, new OrthographicCamera());
        stage = new Stage(gameViewPort, game.batch);

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("blow.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 40;

        BitmapFont font = generator.generateFont(parameter);

        coinLabel = new Label("x0", new Label.LabelStyle(font, Color.WHITE));
        lifeLabel = new Label("x2", new Label.LabelStyle(font, Color.WHITE));
        scoreLabel = new Label("x100", new Label.LabelStyle(font, Color.WHITE));

        coinImg = new Image(new Texture("coin.png"));
        lifeImg = new Image(new Texture("life.png"));
        scoreImg = new Image(new Texture("score.png"));

        pauseBtn = new ImageButton(new SpriteDrawable(new Sprite(new Texture("pause_btn.png"))));
        pauseBtn.setPosition(470, 17, Align.bottomRight);

        pauseBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                createPausePanel();
            }
        });


        Table table = new Table();
        table.top().left();
        table.setFillParent(true);
        table.add(lifeImg).padLeft(10).padTop(10);
        table.add(lifeLabel).padLeft(10).padTop(10);
        table.row();
        table.add(coinImg).padLeft(10).padTop(10);
        table.add(coinLabel).padLeft(10).padTop(10);

        Table scoreTable = new Table();
        scoreTable.top().right();
        scoreTable.setFillParent(true);
        scoreTable.add(scoreImg).padLeft(20).padTop(15);
        scoreTable.row();
        scoreTable.add(scoreLabel).padLeft(20).padTop(15);

        stage.addActor(table);
        stage.addActor(scoreTable);
        stage.addActor(pauseBtn);

        Gdx.input.setInputProcessor(stage);

    }

    private void createPausePanel() {
        pausePanel = new Image(new Texture("pause/Pause Panel.png"));
        resumeBtn = new ImageButton(new SpriteDrawable(new Sprite(new Texture("pause/Resume.png"))));
        quitBtn = new ImageButton(new SpriteDrawable(new Sprite(new Texture("pause/Quit 2.png"))));

        pausePanel.setPosition(W_WIDTH / 2f, W_HEIGHT / 2f, Align.center);
        resumeBtn.setPosition(W_WIDTH / 2f, W_HEIGHT / 2f + 50, Align.center);
        quitBtn.setPosition(W_WIDTH / 2f, W_HEIGHT / 2f - 80, Align.center);

        resumeBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                pausePanel.remove();
                resumeBtn.remove();
                quitBtn.remove();
            }
        });

        quitBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new MainMenu(game));
            }
        });

        stage.addActor(pausePanel);
        stage.addActor(resumeBtn);
        stage.addActor(quitBtn);
    }

    public Stage getStage() {
        return stage;
    }
}
