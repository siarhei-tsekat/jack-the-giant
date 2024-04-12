package com.me.jackthegiant.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.RunnableAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.me.jackthegiant.GameMain;
import com.me.jackthegiant.GameManager;
import com.me.jackthegiant.WorldContactListener;
import com.me.jackthegiant.huds.UIHud;
import com.me.jackthegiant.sprites.CloudsController;
import com.me.jackthegiant.sprites.Player;

public class Gameplay implements Screen {

    public static final int W_WIDTH = 480;
    public static final int W_HEIGHT = 800;
    public static final int PPM = 10;

    public static final short DEFAULT_BIT = 1;
    public static final short PLAYER_BIT = 2;
    public static final short COIN_BIT = 4;
    public static final short LIFE_BIT = 8;
    public static final short DESTROYED_BIT = 16;
    public static final short DARK_CLOUD_BIT = 32;

    private World world;
    private GameMain game;
    private Sprite[] bg;
    private OrthographicCamera mainCamera;
    private Box2DDebugRenderer b2dr;
    private Viewport gameViewPort;
    private float lastYPosition;
    private CloudsController cloudsController;
    private Player player;
    private UIHud uiHud;

    private boolean touchedFirstTime;
    private float lastPlayerY;

    private float cameraSpeed = 5;
    private float maxSpeed = 10;
    private float acceleration = 10;

    private Sound coinSound;
    private Sound lifeSound;

    public Gameplay(GameMain gameMain) {
        game = gameMain;
        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();

        mainCamera = new OrthographicCamera(40, 40 * (h / w));
        gameViewPort = new FillViewport(W_WIDTH / PPM, W_HEIGHT / PPM, mainCamera);
        mainCamera.position.set(mainCamera.viewportWidth / 2f + 4, mainCamera.viewportHeight / 2f, 0);

        world = new World(new Vector2(0, -10), true);
        world.setContactListener(new WorldContactListener(this));
        b2dr = new Box2DDebugRenderer();

        createBackgrounds();
        cloudsController = new CloudsController(world);
        player = new Player(world, "player.png", W_WIDTH / 2f, W_HEIGHT / 2f + 100);
        lastPlayerY = player.getY();
        uiHud = new UIHud(game);

        setCameraSpeed();

        coinSound = Gdx.audio.newSound(Gdx.files.internal("sounds/Coin Sound.wav"));
        lifeSound = Gdx.audio.newSound(Gdx.files.internal("sounds/Life Sound.wav"));
    }


    public Sound getCoinSound() {
        return coinSound;
    }

    public Sound getLifeSound() {
        return lifeSound;
    }

    private void createBackgrounds() {
        bg = new Sprite[3];
        for (int i = 0; i < bg.length; i++) {
            bg[i] = new Sprite(new Texture("background.png"));
            bg[i].setSize(48, 80);
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

    public void checkPlayerBounds() {
        if (player.getY() - W_HEIGHT / 2f / PPM - player.getHeight() / 2f / PPM > mainCamera.position.y) {
            if (!player.isDead()) {
                playerDied();
            }
        }

        if (player.getY() + W_HEIGHT / 2f / PPM + player.getHeight() / 2f / PPM < mainCamera.position.y) {
            if (!player.isDead()) {
                playerDied();
            }
        }

        if (player.getX() > W_WIDTH / PPM) {
            if (!player.isDead()) {
                playerDied();
            }
        } else if (player.getX() + player.getWidth() / 2f / PPM < 0) {
            if (!player.isDead()) {
                playerDied();
            }
        }
    }

    public void playerDied() {
        GameManager.getInstance().isPaused = true;
        uiHud.decrementLife();
        player.setDead(true);

        if (GameManager.getInstance().lifeScore < 0) {

            GameManager.getInstance().checkForNewHighScores();

            uiHud.createGameOverPanel();

            RunnableAction run = new RunnableAction();
            run.setRunnable(() -> {
                game.setScreen(new MainMenu(game));
            });
            SequenceAction sa = new SequenceAction();
            sa.addAction(Actions.delay(2f));
//            sa.addAction(Actions.fadeOut(1f));
            sa.addAction(run);

            uiHud.getStage().addAction(sa);

        } else {

            RunnableAction run = new RunnableAction();
            run.setRunnable(() -> {
                game.setScreen(new Gameplay(game));
            });
            SequenceAction sa = new SequenceAction();
            sa.addAction(Actions.delay(2f));
//            sa.addAction(Actions.fadeOut(1f));
            sa.addAction(run);

            uiHud.getStage().addAction(sa);
        }
    }

    @Override
    public void show() {

    }

    public void countScore() {
        if (lastPlayerY - 10 > player.getY()) {
            uiHud.incrementScore(1);
            lastPlayerY = player.getY();
        }
    }

    private void moveCamera(float delta) {
//        mainCamera.position.y -= 0.08f;
        mainCamera.position.y -= cameraSpeed * delta;
        cameraSpeed += acceleration * delta;

        if (cameraSpeed > maxSpeed) {
            cameraSpeed = maxSpeed;
        }
    }

    private void setCameraSpeed() {
        if (GameManager.getInstance().gameData().isEasyDifficulty()) {
            cameraSpeed = 1;
            maxSpeed = 3;
        }

        if (GameManager.getInstance().gameData().isMediumDifficulty()) {
            cameraSpeed = 3;
            maxSpeed = 6;
        }

        if (GameManager.getInstance().gameData().isHardDifficulty()) {
            cameraSpeed = 6;
            maxSpeed = 9;
        }
    }

    public void checkForFirstTouch() {
        if (!touchedFirstTime) {
            if (Gdx.input.justTouched()) {
                touchedFirstTime = true;
                GameManager.getInstance().isPaused = false;
            }
        }
    }

    private void update(float dt) {

        checkForFirstTouch();

        if (!GameManager.getInstance().isPaused) {
            handleInput();
            moveCamera(dt);
            checkBackgroundPosition();
            cloudsController.setCameraY(mainCamera.position.y);
            cloudsController.createAndArrangeNewClouds();
            cloudsController.removeOffScreenCollectables();
            checkPlayerBounds();
            countScore();
        }
    }

    @Override
    public void render(float delta) {

        update(delta);

        mainCamera.update();
        game.batch.setProjectionMatrix(mainCamera.combined);


        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.batch.begin();
        drawBackgrounds();
        cloudsController.drawClouds(game.batch);
        cloudsController.drawCollectable(game.batch);
        player.drawPlayerIdle(game.batch);
        game.batch.end();

        b2dr.render(world, mainCamera.combined);

        game.batch.setProjectionMatrix(uiHud.getStage().getCamera().combined);
        uiHud.getStage().draw();
        uiHud.getStage().act();

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
        player.getTexture().dispose();
        coinSound.dispose();
        lifeSound.dispose();
    }

    public void handleInput() {
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            player.moveLeft();
        } else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            player.moveRight();
        } else {
            player.setWalking(false);
        }
    }

    public UIHud getHud() {
        return uiHud;
    }
}
