package com.mygdx.tankstars;

public class Player1 {
    private Tank tank;

    private int health;

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public Player1(){
        tank = new Tiger();
        this.setHealth(100);
    }

    public Tank getTank() {
        return tank;
    }

    public void setTank(Tank tank) {
        this.tank = tank;
    }
}
