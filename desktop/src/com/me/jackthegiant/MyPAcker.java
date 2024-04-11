package com.me.jackthegiant;

import com.badlogic.gdx.tools.texturepacker.TexturePacker;

public class MyPAcker {
    public static void main(String[] args) {
        TexturePacker.process("assets/player", "assets/player_f", "player_animation");
    }
}
