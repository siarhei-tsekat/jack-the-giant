package com.me.jackthegiant.scenes;

import static com.me.jackthegiant.scenes.Gameplay.W_HEIGHT;
import static com.me.jackthegiant.scenes.Gameplay.W_WIDTH;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.me.jackthegiant.GameMain;
import com.me.jackthegiant.huds.MainMenuButtons;

public class MainMenu implements Screen {

    private GameMain game;
    private OrthographicCamera mainCamera;
    private Viewport gameViewport;
    private Texture bg;
    private MainMenuButtons btns;

    public MainMenu(GameMain game) {
        this.game = game;
        mainCamera = new OrthographicCamera(W_WIDTH, W_HEIGHT);
        mainCamera.position.set(W_WIDTH / 2f, W_HEIGHT / 2f, 0);
        gameViewport = new StretchViewport(W_WIDTH, W_HEIGHT, mainCamera);

        bg = new Texture("menu_bg.png");
        btns = new MainMenuButtons(game);

    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.batch.begin();
        game.batch.draw(bg, 0, 0);
        game.batch.end();

        game.batch.setProjectionMatrix(btns.getStage().getCamera().combined);
        btns.getStage().draw();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        bg.dispose();
        btns.getStage().dispose();
    }
}
