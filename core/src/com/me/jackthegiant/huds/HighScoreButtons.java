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
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.me.jackthegiant.GameMain;
import com.me.jackthegiant.scenes.MainMenu;

public class HighScoreButtons {
    private GameMain game;
    private Stage stage;
    private Viewport gameViewport;

    private Label scoreLabel;
    private Label coinLabel;

    private ImageButton backBtn;

    public HighScoreButtons(GameMain game) {
        this.game = game;

        gameViewport = new FitViewport(W_WIDTH, W_HEIGHT, new OrthographicCamera());
        stage = new Stage(gameViewport, game.batch);

        Gdx.input.setInputProcessor(stage);

        backBtn = new ImageButton(new SpriteDrawable(new Sprite(new Texture("btns/back_btn.png"))));

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("blow.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 40;

        BitmapFont scoreFont = generator.generateFont(parameter);
        BitmapFont coinFont = generator.generateFont(parameter);

        scoreLabel = new Label("100", new Label.LabelStyle(scoreFont, Color.WHITE));
        coinLabel = new Label("100", new Label.LabelStyle(coinFont, Color.WHITE));

        backBtn.setPosition(70, 17, Align.bottomRight);
        scoreLabel.setPosition(W_WIDTH / 2f - 20, W_HEIGHT / 2 - 120);
        coinLabel.setPosition(W_WIDTH / 2f - 20, W_HEIGHT / 2 - 220);


        backBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new MainMenu(game));
            }
        });

        stage.addActor(backBtn);
        stage.addActor(scoreLabel);
        stage.addActor(coinLabel);
    }

    public Stage getStage() {
        return stage;
    }
}
