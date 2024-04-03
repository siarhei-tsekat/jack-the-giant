package com.me.jackthegiant;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.me.jackthegiant.scenes.Gameplay;

public class GameMain extends Game {

    public static final int W_WIDTH = 480;
    public static final int W_HEIGHT = 800;
    public static final float PPM = 100;

    public SpriteBatch batch;

    @Override
    public void create() {
        batch = new SpriteBatch();
        setScreen(new Gameplay(this));
    }

    @Override
    public void render() {
        super.render();
    }

    @Override
    public void dispose() {
        batch.dispose();
    }
}
