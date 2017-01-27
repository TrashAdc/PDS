package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

/**
 * created by ryan v on 1/26/2017
 **/
public class Character {
    private Sprite playerSprite; //the image of the character, may not need to be here with animation class
    private int speed; //speed at which the character moves

    public Character(){

        playerSprite = new Sprite(new Texture("core/assets/image/spr_parent.png"));  //gets image
        playerSprite.setPosition(Window.SIZE.width / 2, Window.SIZE.height / 2); //sets initial position
        speed = 20;
    }

    public Sprite getSprite(){ //returns the sprite (this contains position as well)
        movement();
        return playerSprite;
    }

    private void movement(){
        if (Window.key.left)
            playerSprite.translateX(-speed); //moves the character based on what key is pressed
        if (Window.key.right)
            playerSprite.translateX(speed);
    }


//do not worry my friend there will be more to come






}
