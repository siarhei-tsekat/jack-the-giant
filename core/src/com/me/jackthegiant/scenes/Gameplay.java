package com.me.jackthegiant.scenes;

import static com.me.jackthegiant.GameMain.W_HEIGHT;
import static com.me.jackthegiant.GameMain.W_WIDTH;

import com.badlogic.gdx.Gdx;
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
import com.me.jackthegiant.sprites.Cloud;
import com.me.jackthegiant.sprites.CloudsController;

public class Gameplay implements Screen {

    private World world;
    private GameMain game;
    private Sprite[] bg;
    private OrthographicCamera mainCamera;
    private Box2DDebugRenderer b2dr;
    private Viewport gameViewPort;
    private float lastYPosition;
    private CloudsController cloudsController;

    public Gameplay(GameMain gameMain) {
        game = gameMain;
        mainCamera = new OrthographicCamera();
        gameViewPort = new FillViewport(W_WIDTH, W_HEIGHT, mainCamera);

        mainCamera.position.set(gameViewPort.getWorldWidth() / 2, gameViewPort.getWorldHeight() / 2, 0);

        world = new World(new Vector2(0, -10), true);
        b2dr = new Box2DDebugRenderer();

        createBackgrounds();
        cloudsController = new CloudsController(world);
//        cloud = new Cloud(world, W_WIDTH / 2f, W_HEIGHT / 2f, "cloud_0.png");
    }

    private void createBackgrounds() {
        bg = new Sprite[3];
        for (int i = 0; i < bg.length; i++) {
            bg[i] = new Sprite(new Texture("background.png"));
            bg[i].setPosition(0, -(i * bg[i].getHeight()));
        }
        lastYPosition = Math.abs(bg[bg.length - 1].getY());
    }

    private void drawBackgrounds() {
        for (int i = 0; i < bg.length; i++) {
            game.batch.draw(bg[i], bg[i].getX(), bg[i].getY());
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
        mainCamera.position.y -= 3;
    }

    @Override
    public void render(float delta) {
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
//        game.batch.draw(cloud, cloud.getX(), cloud.getY());
        game.batch.end();

        b2dr.render(world, mainCamera.combined);

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
}
