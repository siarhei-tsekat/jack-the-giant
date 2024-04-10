package com.me.jackthegiant.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.me.jackthegiant.GameMain;
import com.me.jackthegiant.sprites.CloudsController;
import com.me.jackthegiant.sprites.Player;

public class Gameplay implements Screen {

    public static final int W_WIDTH = 400;
    public static final int W_HEIGHT = 700;
    public static final int PPM = 10;

    private World world;
    private GameMain game;
    private Sprite[] bg;
    private OrthographicCamera mainCamera;
    private Box2DDebugRenderer b2dr;
    private Viewport gameViewPort;
    private float lastYPosition;
    private CloudsController cloudsController;
    private Player player;

    public Gameplay(GameMain gameMain) {
        game = gameMain;
        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();

        mainCamera = new OrthographicCamera(40, 40 * (h / w));
        gameViewPort = new FillViewport(W_WIDTH / PPM, W_HEIGHT / PPM, mainCamera);
        mainCamera.position.set(mainCamera.viewportWidth / 2f, mainCamera.viewportHeight / 2f, 0);

        world = new World(new Vector2(0, -10), true);
        b2dr = new Box2DDebugRenderer();

        createBackgrounds();
        cloudsController = new CloudsController(world);
        player = new Player(world, "player.png", W_WIDTH / 2f, W_HEIGHT / 2f + 100);
    }

    private void createBackgrounds() {
        bg = new Sprite[3];
        for (int i = 0; i < bg.length; i++) {
            bg[i] = new Sprite(new Texture("background.png"));
            bg[i].setSize(40, 70);
            bg[i].setPosition(0, -(i * bg[i].getHeight()));
        }
        lastYPosition = Math.abs(bg[bg.length - 1].getY());
    }

    private void drawBackgrounds() {
        for (int i = 0; i < bg.length; i++) {
            bg[i].draw(game.batch);
        }
    }

    private void checkBackgroundPosition() {
        for (int i = 0; i < bg.length; i++) {
            if (bg[i].getY() - bg[i].getHeight() / 2f > mainCamera.position.y) {
                float newPosition = bg[i].getHeight() + lastYPosition;
                bg[i].setPosition(0, -newPosition);
                lastYPosition = newPosition;
            }
        }
    }

    @Override
    public void show() {

    }

    private void moveCamera() {
        mainCamera.position.y -= 0.08;
    }

    @Override
    public void render(float delta) {
        handleInput();

        moveCamera();
        checkBackgroundPosition();
        mainCamera.update();
        game.batch.setProjectionMatrix(mainCamera.combined);


        cloudsController.setCameraY(mainCamera.position.y);
        cloudsController.createAndArrangeNewClouds();

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.batch.begin();
        drawBackgrounds();
        cloudsController.drawClouds(game.batch);
        player.draw(game.batch);
        game.batch.end();

        b2dr.render(world, mainCamera.combined);

        player.update(delta);

        world.step(1 / 60f, 6, 2);
    }

    @Override
    public void resize(int width, int height) {
        gameViewPort.update(width, height);
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
        world.dispose();
        b2dr.dispose();
    }

    public void handleInput() {
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            player.moveLeft();
        } else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            player.moveRight();
        }
    }
}
