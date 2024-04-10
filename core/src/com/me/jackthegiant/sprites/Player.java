package com.me.jackthegiant.sprites;


import static com.me.jackthegiant.scenes.Gameplay.PPM;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class Player extends Sprite {
    private World world;
    private Body body;

    public Player(World world, String name, float x, float y) {
        super(new Texture(name));
        this.world = world;

        setPosition((x - getWidth() / 2) / PPM, (y - getHeight() / 2) / PPM);
        defineBody();
    }

    private void defineBody() {
        BodyDef bdef = new BodyDef();
        bdef.type = BodyDef.BodyType.DynamicBody;

        bdef.position.set(getX() + getWidth() / 2f / PPM, getY() + getHeight() / 2f / PPM);
        body = world.createBody(bdef);
        body.setFixedRotation(true);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(getWidth() / 2f / PPM, getHeight() / 2f / PPM);

        FixtureDef fdef = new FixtureDef();
        fdef.density = 4f; // mass of the body
        fdef.friction = 2f; // will make player not slide on surfaces
        fdef.shape = shape;

        body.createFixture(fdef);
    }

    public void update(float delta) {
        setPosition(body.getPosition().x - getWidth() / 2 / PPM, body.getPosition().y - getHeight() / 2 / PPM);
    }

    public void draw(SpriteBatch batch) {
        batch.draw(this, getX(), getY(), getWidth() / PPM, getHeight() / PPM);
    }

    public void moveLeft() {
        body.setLinearVelocity(-10, body.getLinearVelocity().y);
    }

    public void moveRight() {
        body.setLinearVelocity(10, body.getLinearVelocity().y);
    }
}
