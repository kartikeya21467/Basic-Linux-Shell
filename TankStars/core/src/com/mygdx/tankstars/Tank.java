package com.mygdx.tankstars;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class Tank {
    private Vector2 position;
    private Texture tanktexture;

    public Image tankimage;
    private int height;
    private int width;

    private String name;

    private int fuel;

//    public void setPosition(Vector2 position) {
//        this.position = position;
//    }

    public void setPosition(Vector2 position) {
        this.position = position;
    }

    public void setTankimage(Image tankimage) {
        this.tankimage = tankimage;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }


    void updateX(int x){
        position.x=x;
    }

    void updateY(int y){
        position.y=y;
    }

    public Image getTankimage(){
        return tankimage;
    }

    public int getFuel() {
        return fuel;
    }

    public void setFuel(int fuel) {
        this.fuel = fuel;
    }

    void setTanktexture(Texture tank){

        tanktexture=tank;
        tankimage = new Image(tank);
    }

    public Texture getTanktexture(){
        return tanktexture;
    }

    void setPosition(int x, int y){
        position = new Vector2(x,y);
    }

    Vector2 getPosition(){
        return position;
    }

    void updatePosition(int x, int y){
        position.add(x,y);
    }
    void moveleft(){
        position.x-=200* Gdx.graphics.getDeltaTime();
        if(position.x<0) position.x=0;
//        System.out.println(position.x);
    }

    void moveright(){
        position.x+=200*Gdx.graphics.getDeltaTime();
        if(position.x>1600-width) position.x = 1600-width;
//        System.out.println(position.x);
    }


}
