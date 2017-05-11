package com.mygdx.game;

import com.badlogic.gdx.math.Vector2;

/**
 * Created by 236040 on 5/10/2017.
 */
public class MovingHitbox extends Hitbox{

    boolean right, up;
    FrameTimer deathTimer;
    int frame;

    public MovingHitbox(float width, float height, float posx, float posy, Vector2 force, int damage, GameData.Player player, boolean directionH, boolean directionV, int frames){
        super(width, height, posx, posy, force, damage, player);
        right = directionH;
        up = directionV;
        deathTimer = new FrameTimer(frames);
    }

    public void setHDirection(boolean dirH){
        right = dirH;
    }
    public void setVDirection(boolean dirV){
        up = dirV;
    }
    public int getHDirection(){
        return (right) ? 1 : -1;
    }
    public int getvDirection(){
        return (up) ? 1 : -1;
    }
    public void incrementFrame(){
        deathTimer.incrementFrame();
    }
    public boolean timerCheck(){
        return deathTimer.timerDone(false);
    }

}