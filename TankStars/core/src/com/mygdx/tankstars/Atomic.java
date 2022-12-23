package com.mygdx.tankstars;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class Atomic extends Tank{

    public Atomic(){
        setTanktexture(new Texture(Gdx.files.internal("Atomic.png")));
        setPosition(0,250);
        setHeight(50);
        setWidth(80);
        setName("ATOMIC");
        setFuel(100);
    }
}
