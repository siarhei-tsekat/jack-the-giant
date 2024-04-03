package com.me.jackthegiant.scenes;

import static com.me.jackthegiant.GameMain.W_HEIGHT;
import static com.me.jackthegiant.GameMain.W_WIDTH;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.me.jackthegiant.GameMain;

public class Gameplay implements Screen {

    private GameMain game;
    private Sprite[] bg;
    private OrthographicCamera camera;
    private Viewport gameViewPort;

    public Gameplay(GameMain game) {
        this.game = game;
        createBackgrounds();
        camera = new OrthographicCamera(W_WIDTH, W_HEIGHT);
        camera.position.set(W_WIDTH / 2f, W_HEIGHT / 2f, 0);
        gameViewPort = new FillViewport(W_WIDTH, W_HEIGHT, camera);
    }

    private void createBackgrounds() {
        bg = new Sprite[3];
        for (int i = 0; i < bg.length; i++) {
            bg[i] = new Sprite(new Texture("background.png"));
            bg[i].setPosition(0, -(i * bg[i].getHeight()));
        }
    }

    private void drawBackgrounds() {
        for (int i = 0; i < bg.length; i++) {
            game.batch.draw(bg[i], bg[i].getX(), bg[i].getY());
        }
    }

    @Override
    public void show() {

    }

    public void update(float dt) {
        moveCamera();
    }

    private void moveCamera() {
        camera.position.y -= 1;
    }

    @Override
    public void render(float delta) {

        update(delta);

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.batch.begin();

        drawBackgrounds();

        game.batch.end();

        game.batch.setProjectionMatrix(camera.combined);
        camera.update();
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

    }
}
