package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.*;


/**
 * created by ryan v on 1/26/2017
 **/

public class Character implements CharacterStates {

    public enum State { //states of the character
        IDLE,           //the character will always be in a state, and only be in one state at a time
        ANIMATION,      //the method corresponding with the state the character is in
        STUNNED,        //these methods will be listed in an interface
        WALKING,
        AIR,
        HITSTUN,
        DEAD
    }


    private State currentState; //current state of character
    private boolean state_new; //is true on only the first loop of the state method

    private Sprite playerSprite; //the image of the character, may not need to be here with animation class
    private BodyDef bodyDef;
    private Body body;
    private float bodyWidth, bodyHeight;

    private float maxSpeed; //speed at which the character moves
    private boolean hasJump; //if the character can double jump
    private boolean canJump;
    private Timer jumpTimer;

    public Character(){

        bodyWidth = 1f;
        bodyHeight = 1f * Window.yConst;

        objInit();

        playerSprite = new Sprite(new Texture("core/assets/image/spr_parent.png"));  //gets image
        playerSprite.setPosition(body.getPosition().x - bodyWidth, body.getPosition().y - bodyHeight); //sets initial position
        playerSprite.setSize(bodyWidth * 2f, bodyHeight * 2f);

        body.setUserData(playerSprite);

        maxSpeed = 10;
        hasJump = false;
        canJump = false;
        jumpTimer = new Timer(Timer.timeType.MILLISECONDS, 85);

        currentState = State.IDLE;
        state_new = true;

    }

    private void objInit(){
        bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(Window.camera.viewportWidth / 2 , Window.camera.viewportHeight / 2);
        bodyDef.fixedRotation = true;

        body = Window.world.createBody(bodyDef);

        PolygonShape bodyShape = new PolygonShape();
        bodyShape.setAsBox(bodyWidth, bodyHeight);

        FixtureDef fixDef = new FixtureDef();
        fixDef.shape = bodyShape;
        fixDef.density = 0.5f;
        fixDef.restitution = 0.05f;
        fixDef.friction = 0.5f;

        body.createFixture(fixDef);


    }

    public Sprite getSprite(){ //returns the sprite (this contains position as well)
        executeState();
        return playerSprite;
    }

    private void executeState(){ //this executes the state that the character is currently in
        playerSprite.setPosition(body.getPosition().x - bodyWidth, body.getPosition().y - bodyHeight); //set sprite equal to body

        switch (currentState) {
            case IDLE:
                St_Idle();
                break;
            case WALKING:
                St_Walking();
                break;
            case AIR:
                St_Air();
            //default:
             //   St_Idle();
            //    break;
        }

    }


    public void St_Idle(){
        state_new = false;
        //body.setLinearVelocity(0f, body.getLinearVelocity().y);

        if (Window.key.numpad3)
            switchState(State.AIR);

        if (Window.key.left && Window.key.right)
            switchState(State.IDLE);
        else if (Window.key.left || Window.key.right)
            switchState(State.WALKING);
    }

    public void St_Walking(){
        state_new = false;

        horizontalMovement();

        if (Window.key.numpad3)
            switchState(State.AIR);

        if (Window.key.left && Window.key.right)
            switchState(State.IDLE);
        else if (!Window.key.left && !Window.key.right)
            switchState(State.IDLE);
    }

    public void St_Air(){
        System.out.println(hasJump);
        if (state_new){
            body.setLinearVelocity(body.getLinearVelocity().x, .01f);
            body.applyLinearImpulse(0f, 125f, bodyWidth / 2, bodyHeight / 2, false);

            hasJump = true;
            state_new = false;
        }
        else if (Window.key.numpad3 && hasJump && canJump){
            body.setLinearVelocity(body.getLinearVelocity().x, .01f);
            body.applyLinearImpulse(0f, 125f, bodyWidth / 2, bodyHeight / 2, false);
            hasJump = false;
        }

        if (!Window.key.numpad3)
            canJump = true;

        horizontalMovement();

        if (body.getLinearVelocity().y <= 0.1f && body.getLinearVelocity().y >= -0.1f){
            hasJump = false;

            if (Window.key.left || Window.key.right)
                switchState(State.WALKING);
            else
                switchState(State.IDLE);
        }


    }

   private void switchState(State newState){
        state_new = true;
        currentState = newState;
        //System.out.println(stateToString());
    }

    public String stateToString(){
        switch (currentState){
            case IDLE:
                return "idle";
            case WALKING:
                return "walking";
            case AIR:
                return "air";
            default:
                return "not set";
        }
    }

    private void horizontalMovement(){
        if (Window.key.left) {

            if (body.getLinearVelocity().x == 0)
                body.setLinearVelocity(-.01f, body.getLinearVelocity().y);

            body.applyLinearImpulse(-5f, 0f, bodyWidth / 2, bodyHeight / 2, false);
        }

        if (Window.key.right) {
            if (body.getLinearVelocity().x == 0)
                body.setLinearVelocity(.01f, body.getLinearVelocity().y);

            body.applyLinearImpulse(5f, 0f, bodyWidth / 2, bodyHeight / 2, false);
        }

        setMaxSpeed(maxSpeed);
    }

    private void setMaxSpeed(float maxV){
        if(body.getLinearVelocity().x > maxV)
            body.setLinearVelocity(maxV, body.getLinearVelocity().y);
        else if (body.getLinearVelocity().x < -maxV)
            body.setLinearVelocity(-maxV, body.getLinearVelocity().y);
    }


//do not worry my friend there will be more to come






}