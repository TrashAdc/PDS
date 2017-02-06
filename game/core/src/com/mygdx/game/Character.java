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
        RUNNING,
        JUMPING,
        FALLING,
        HITSTUN,
        DEAD
    }


    private State currentState;

    private Sprite playerSprite; //the image of the character, may not need to be here with animation class
    private BodyDef bodyDef;
    private Body body;
    private float bodyWidth, bodyHeight;

    private int speed; //speed at which the character moves

    public Character(){

        bodyWidth = 1f;
        bodyHeight = 1f * Window.yConst;

        objInit();

        playerSprite = new Sprite(new Texture("core/assets/image/spr_parent.png"));  //gets image
        playerSprite.setPosition(body.getPosition().x - bodyWidth, body.getPosition().y - bodyHeight); //sets initial position
        playerSprite.setSize(bodyWidth * 2f, bodyHeight * 2f);

        body.setUserData(playerSprite);

        speed = 20;



        currentState = State.IDLE;

    }

    private void objInit(){
        bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(Window.camera.viewportWidth / 2 , Window.camera.viewportHeight / 2);

        body = Window.world.createBody(bodyDef);

        PolygonShape bodyShape = new PolygonShape();
        bodyShape.setAsBox(bodyWidth, bodyHeight);

        FixtureDef fixDef = new FixtureDef();
        fixDef.shape = bodyShape;
        fixDef.density = 0.5f;
        fixDef.restitution = 1.0f;
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
            case RUNNING:
                St_Walking();
                break;
            default:
                St_Idle();
                break;
        }
    }


    public void St_Idle(){
        body.setLinearVelocity(0f, body.getLinearVelocity().y);

        if (Window.key.left && Window.key.right)
            switchState(State.IDLE);
        else if (Window.key.left || Window.key.right)
            switchState(State.RUNNING);
    }

    public void St_Walking(){

        if (Window.key.left)
            body.setLinearVelocity(-10f, body.getLinearVelocity().y);
        if (Window.key.right)
            body.setLinearVelocity(10f, body.getLinearVelocity().y);
        //playerSprite.translateX(speed);



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


//do not worry my friend there will be more to come






}