package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.BoundingBox;
import org.w3c.dom.css.Rect;

import java.awt.*;

/**
 * created by ryan v on 1/26/2017
 **/

public class Character implements CharacterStates {

    public enum State { //states of the character
        IDLE,           //the character will always be in a state, and only be in one state at a time
        ANIMATION,      //the method corresponding with the state the character is in
        STUNNED,        //these methods will be listed in an interface
        RUNNING,
        JUMPING,
        FALLING,
        HITSTUN,
        DEAD
    }


    private State currentState;

    private Sprite playerSprite; //the image of the character, may not need to be here with animation class

    private int speed; //speed at which the character moves
    private int weight;

    Rectangle bb1 = new Rectangle(-Window.SIZE.width / 8, 0, -Window.SIZE.width / 8, Window.SIZE.height);

    public Character(){

        playerSprite = new Sprite(new Texture("core/assets/image/spr_parent.png"));  //gets image
        playerSprite.setPosition(Window.SIZE.width / 2, Window.SIZE.height / 2); //sets initial position
        playerSprite.setSize(playerSprite.getWidth() / 2, playerSprite.getHeight() / 2);

        speed = 20;



        currentState = State.IDLE;

    }

    public Sprite getSprite(){ //returns the sprite (this contains position as well)
        executeState();
        return playerSprite;
    }

    private void executeState(){ //this executes the state that the character is currently in
         switch (currentState) {
             case IDLE:
                 St_Idle();
                 break;
             case RUNNING:
                 St_Walking();
                 break;
             default:
                 St_Idle();
                 break;
         }
    }


    public void St_Idle(){
        if (Window.key.left && Window.key.right)
            switchState(State.IDLE);
        else if (Window.key.left || Window.key.right)
            switchState(State.RUNNING);
    }

    public void St_Walking(){

        if (Window.key.left)
            playerSprite.translateX(-speed);
        if (Window.key.right)
            playerSprite.translateX(speed);

        if (Window.key.left && Window.key.right)
            switchState(State.IDLE);
        else if (!Window.key.left && !Window.key.right)
            switchState(State.IDLE);
    }

    public void switchState(State newState){
        currentState = newState;
    }
    public String stateToString(){
        switch (currentState){
            case IDLE:
                return "idle";
            case RUNNING:
                return "running";
            default:
                return "not set";
        }
    }

    public boolean cCheck(){
        if (playerSprite.getBoundingRectangle().contains(new Vector2(1f, playerSprite.getOriginY())))
            return true;
        else if (playerSprite.getBoundingRectangle().contains(new Vector2(Window.SIZE.width, playerSprite.getOriginY())))
            return true;
        else
            return false;
    }

//do not worry my friend there will be more to come






}