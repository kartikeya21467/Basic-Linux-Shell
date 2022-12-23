package com.mygdx.tankstars;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class Spectre extends Tank{

    public Spectre(){
        setTanktexture(new Texture(Gdx.files.internal("Spectre_HD.png")));
        setPosition(0,250);
        setHeight(50);
        setWidth(80);
        setName("SPECTRE");
        setFuel(100);
    }
}
