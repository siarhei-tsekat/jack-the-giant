package com.me.jackthegiant.collecctables;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public abstract class Collectable extends Sprite {
    public Collectable(Texture texture) {
        super(texture);
    }

    public abstract void update();
}
