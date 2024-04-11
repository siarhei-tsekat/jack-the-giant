package com.me.jackthegiant.collecctables;

import static com.me.jackthegiant.scenes.Gameplay.PPM;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class Collectable extends Sprite {

    private World world;
    private String name;
    private Fixture fixture;
    private Body body;

    public Collectable(World world, String name, float x, float y) {
        super(new Texture(name));
        this.world = world;
        this.name = name;

//        setPosition((x - getWidth() / 2) / PPM, (y - getHeight() / 2) / PPM);
        setPosition(x, y);
        createBody();
    }

    private void createBody() {
        BodyDef bdef = new BodyDef();
        bdef.type = BodyDef.BodyType.StaticBody;
        bdef.position.set(getX() + getWidth() / 2f / PPM, getY() + getHeight() / 2f / PPM);

        body = world.createBody(bdef);
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(getWidth() / 2f / PPM, getHeight() / 2f / PPM);

        FixtureDef fdef = new FixtureDef();
        fdef.shape = shape;

        fixture = body.createFixture(fdef);

    }

    public void update() {
        setPosition(body.getPosition().x - getWidth() / 2 / PPM, body.getPosition().y - getHeight() / 2 / PPM);
    }
}
