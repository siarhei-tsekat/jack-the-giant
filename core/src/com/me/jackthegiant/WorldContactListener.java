package com.me.jackthegiant;

import static com.me.jackthegiant.scenes.Gameplay.COIN_BIT;
import static com.me.jackthegiant.scenes.Gameplay.DARK_CLOUD_BIT;
import static com.me.jackthegiant.scenes.Gameplay.LIFE_BIT;
import static com.me.jackthegiant.scenes.Gameplay.PLAYER_BIT;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.me.jackthegiant.collecctables.Coin;
import com.me.jackthegiant.collecctables.Life;
import com.me.jackthegiant.scenes.Gameplay;

public class WorldContactListener implements ContactListener {

    private Gameplay gameplay;

    public WorldContactListener(Gameplay gameplay) {
        this.gameplay = gameplay;
    }

    @Override
    public void beginContact(Contact contact) {

        Fixture fixA = contact.getFixtureA();
        Fixture fixB = contact.getFixtureB();

        int cDef = fixA.getFilterData().categoryBits | fixB.getFilterData().categoryBits;

        switch (cDef) {
            case PLAYER_BIT | COIN_BIT:
                if (fixA.getFilterData().categoryBits == PLAYER_BIT) {
                    ((Coin) fixB.getUserData()).remove();
                } else {
                    ((Coin) fixA.getUserData()).remove();
                }
                gameplay.getCoinSound().play();
                gameplay.getHud().incrementCoins();
                break;
            case PLAYER_BIT | LIFE_BIT:
                if (fixA.getFilterData().categoryBits == PLAYER_BIT) {
                    ((Life) fixB.getUserData()).remove();
                } else {
                    ((Life) fixA.getUserData()).remove();
                }
                gameplay.getLifeSound().play();
                gameplay.getHud().incrementLife();
                break;
            case PLAYER_BIT | DARK_CLOUD_BIT:
                gameplay.playerDied();
                break;
            default:
        }

    }

    @Override
    public void endContact(Contact contact) {

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
