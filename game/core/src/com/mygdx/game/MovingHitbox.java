package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 236040 on 5/10/2017.
 */
public class MovingHitbox extends Hitbox{

    private boolean right, up;
    private FrameTimer deathTimer;
    private Sprite hitboxSprite;
    private float width, height;


    public MovingHitbox(float width, float height, float posx, float posy, Vector2 force, int damage, GameData.Player player, boolean directionH, boolean directionV, int frames){
        super(width, height, posx, posy, force, damage, player);
        right = directionH;
        up = directionV;
        this.width = width;
        this.height = height;
        deathTimer = new FrameTimer(frames);
        hitboxSprite = new Sprite(new Texture("core/assets/image/pichu.jpg"));
        hitboxSprite.setSize(width * 2, height * 2);

    }
    public MovingHitbox(float width, float height, float posx, float posy, Vector2 force, int damage, GameData.Player player, boolean directionH, boolean directionV, int frames, String spritePath){
        super(width, height, posx, posy, force, damage, player);
        right = directionH;
        up = directionV;
        deathTimer = new FrameTimer(frames);
        hitboxSprite = new Sprite(new Texture(Gdx.files.internal(spritePath)));
        hitboxSprite.setSize(width, height);

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
        hitboxSprite.setPosition(hitbox.getPosition().x - width, hitbox.getPosition().y - height);
        hitboxSprite.draw(Window.batch);

    }
    public boolean timerCheck(){
        return deathTimer.timerDone(false);
    }

    public void destroyHitbox(){
        hitboxMap.remove(userData);
        Window.bDestroy.addBody(hitbox);
    }




}