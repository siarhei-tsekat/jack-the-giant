package com.me.jackthegiant.collecctables;

import static com.me.jackthegiant.scenes.Gameplay.COIN_BIT;
import static com.me.jackthegiant.scenes.Gameplay.DESTROYED_BIT;
import static com.me.jackthegiant.scenes.Gameplay.PPM;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.me.jackthegiant.sprites.CloudsController;

public class Coin extends Collectable{

    private World world;
    private String name;
    private Fixture fixture;
    private Body body;
    private CloudsController cloudsController;

    public Coin(World world, String name, float x, float y, CloudsController cloudsController) {
        super(new Texture(name));
        this.world = world;
        this.name = name;
        this.cloudsController = cloudsController;

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
        fdef.filter.categoryBits = COIN_BIT;
        fdef.isSensor = true;

        fixture = body.createFixture(fdef);
        fixture.setUserData(this);

    }

    public void update() {
        setPosition(body.getPosition().x - getWidth() / 2 / PPM, body.getPosition().y - getHeight() / 2 / PPM);
    }

    public void remove() {
        setCategoryFilter(DESTROYED_BIT);
        getTexture().dispose();
        cloudsController.remove(this);
    }

    public void setCategoryFilter(short filterBit) {
        Filter filter = new Filter();
        filter.categoryBits = filterBit;
        fixture.setFilterData(filter);
    }
}
