package com.me.jackthegiant.sprites;

import static com.me.jackthegiant.scenes.Gameplay.DARK_CLOUD_BIT;
import static com.me.jackthegiant.scenes.Gameplay.PPM;

import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class DarkCloud extends Cloud {

    public DarkCloud(World world, float x, float y, String name) {
        super(world, x, y, name);
    }


    protected void defineBody() {
        BodyDef bdef = new BodyDef();
        bdef.type = BodyDef.BodyType.StaticBody;
        bdef.position.set(getX() + getWidth() / 2f / PPM, getY() + getHeight() / 2f / PPM);

        body = world.createBody(bdef);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(getWidth() / 2f / PPM - 1.5f, getHeight() / 2f / PPM - 0.8f);

        FixtureDef fdef = new FixtureDef();
        fdef.shape = shape;
        fdef.filter.categoryBits = DARK_CLOUD_BIT;
        fdef.isSensor = true;

        body.createFixture(fdef);
    }
}
