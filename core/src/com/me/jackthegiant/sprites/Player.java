package com.me.jackthegiant.sprites;


import static com.me.jackthegiant.scenes.Gameplay.COIN_BIT;
import static com.me.jackthegiant.scenes.Gameplay.DARK_CLOUD_BIT;
import static com.me.jackthegiant.scenes.Gameplay.DEFAULT_BIT;
import static com.me.jackthegiant.scenes.Gameplay.LIFE_BIT;
import static com.me.jackthegiant.scenes.Gameplay.PLAYER_BIT;
import static com.me.jackthegiant.scenes.Gameplay.PPM;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.me.jackthegiant.GameManager;

public class Player extends Sprite {
    private World world;
    private Body body;
    private TextureAtlas playerAtlas;
    private Animation<TextureRegion> animation;
    private float elapsedTime;
    private boolean isWalking;
    private boolean dead;

    public Player(World world, String name, float x, float y) {
        super(new Texture(name));
        this.world = world;

        setPosition((x - getWidth() / 2) / PPM, (y - getHeight() / 2) / PPM);
        defineBody();

        playerAtlas = new TextureAtlas("player_f/player_animation.atlas");
    }

    private void defineBody() {
        BodyDef bdef = new BodyDef();
        bdef.type = BodyDef.BodyType.DynamicBody;

        bdef.position.set(getX() + getWidth() / 2f / PPM, getY() + getHeight() / 2f / PPM);
        body = world.createBody(bdef);
        body.setFixedRotation(true);

//        PolygonShape shape = new PolygonShape();
//        shape.setAsBox(getWidth() / 2f / PPM, getHeight() / 2f / PPM);

        CircleShape shape = new CircleShape();
        shape.setRadius(3);
        FixtureDef fdef = new FixtureDef();
        fdef.density = 90000f; // mass of the body
        fdef.friction = 30f; // will make player not slide on surfaces
        fdef.shape = shape;
        fdef.filter.categoryBits = PLAYER_BIT;
        fdef.filter.maskBits = DEFAULT_BIT | COIN_BIT | LIFE_BIT | DARK_CLOUD_BIT;

        body.createFixture(fdef).setUserData(this);
    }

    public void update(float delta) {
        setPosition(body.getPosition().x - getWidth() / 2 / PPM, body.getPosition().y - getHeight() / 4 / PPM);
    }

    public void drawPlayerIdle(SpriteBatch batch) {
        if (!isWalking) {
            batch.draw(this, getX(), getY(), getWidth() / PPM, getHeight() / PPM);
        } else {
            elapsedTime += Gdx.graphics.getDeltaTime();

            Array<TextureAtlas.AtlasRegion> frames = playerAtlas.getRegions();
            for (TextureAtlas.AtlasRegion frame : frames) {
                if (body.getLinearVelocity().x < 0 && !frame.isFlipX()) {
                    frame.flip(true, false);
                } else if (body.getLinearVelocity().x > 0 && frame.isFlipX()) {
                    frame.flip(true, false);
                }
            }
            animation = new Animation<TextureRegion>(1f / 9f, playerAtlas.getRegions());

            batch.draw(animation.getKeyFrame(elapsedTime, true), getX(), getY(), getWidth() / PPM, getHeight() / PPM);
        }
    }

    public void moveLeft() {
        if (!isFlipX()) {
            flip(true, false);
        }
        isWalking = true;
        body.setLinearVelocity(-15, body.getLinearVelocity().y);
    }

    public void moveRight() {
        if (isFlipX()) {
            flip(true, false);
        }
        isWalking = true;
        body.setLinearVelocity(15, body.getLinearVelocity().y);
    }

    public void setWalking(boolean walking) {
        isWalking = walking;
    }

    public void setDead(boolean dead) {
        this.dead = dead;
    }

    public boolean isDead() {
        return dead;
    }
}
