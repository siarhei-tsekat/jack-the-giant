package com.me.jackthegiant.sprites;

import static com.me.jackthegiant.scenes.Gameplay.PPM;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class Cloud extends Sprite {

    private World world;
    private Body body;

    public Cloud(World world, float x, float y, String name) {
        super(new Texture(name));

        this.world = world;
        setPosition((x - getWidth() / 2) / PPM, (y - getHeight() / 2) / PPM);

        defineBody();
    }

    private void defineBody() {
        BodyDef bdef = new BodyDef();
        bdef.type = BodyDef.BodyType.StaticBody;
        bdef.position.set(getX() + getWidth() / 2f / PPM, getY() + getHeight() / 2f / PPM);

        body = world.createBody(bdef);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(getWidth() / 2f / PPM - 1.5f, getHeight() / 2f / PPM - 0.8f);

        FixtureDef fdef = new FixtureDef();
        fdef.shape = shape;

        body.createFixture(fdef);
    }
}
