package com.mygdx.tankstars;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class Tiger extends Tank{

    public Tiger(){
        setTanktexture(new Texture(Gdx.files.internal("Tiger_edited.png")));
        setPosition(0,250);
        setHeight(50);
        setWidth(80);
        setName("TIGER");
        setFuel(100);
    }

//    void moveleft(){
//
//    }


}
