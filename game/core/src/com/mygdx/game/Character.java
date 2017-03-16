package com.mygdx.game;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;


/**
 * created by ryan v on 1/26/2017
 **/

public class Character implements CharacterStates { //parent character class

    public enum State { //states of the character
        IDLE,           //the character will always be in a state, and only be in one state at a time
        WALKING,      //the method corresponding with the state the character is in
        AIR,        //these methods will be listed in an interface
        ANIMATION,
        LEDGE,
        HITSTUN,
        STUNNED,
        DEAD
    }

    public enum Attack { //attacks include tilts, (sm/tr)ash attacks, and jabs (dash attacks if we implement running)
        JAB,               //numpad1 + any direction
        S_TILT, U_TILT, D_TILT,
        S_SMASH, U_SMASH, D_SMASH,
    }

    private enum Special { //specials are attacks that differ very greatly from other characters, but there are only 4 of them
        N_SPECIAL,          //numpad2 + any direction
        S_SPECIAL,
        U_SPECIAL,
        D_SPECIAL
    }
    //maybe aerial attacks will come in the future but for now use basic attacks in the air


    private State currentState; //current state of character
    private boolean state_new; //is true on only the first loop of the state method

    private Attack currentAttack;
    private GameData.Player player;

    private Sprite playerSprite; //the image of the character, may not need to be here with animation class
    private BodyDef bodyDef;   //body define for box2d
    private Body body;         //actual box2d body
    private float bodyWidth, bodyHeight; //height and width of body

    private boolean direction; // 0 is left, 1 is right
    private float maxSpeed; //max speed at which the character can move
    private boolean hasJump; //if the character has a double jump
    private boolean canJump; //if the character can use the double jump atm

    private Hitbox hitbox;

    private FrameTimer animationTimer;

    public Character(){

        bodyWidth = 1f;                 //set width and height
        bodyHeight = 1f * Window.yConst;

        objInit(); //does box2d stuff

        playerSprite = new Sprite(new Texture("core/assets/image/spr_parent.png"));  //gets/sets image
        playerSprite.setPosition(body.getPosition().x - bodyWidth, body.getPosition().y - bodyHeight); //sets initial position = body pos
        playerSprite.setSize(bodyWidth * 2f, bodyHeight * 2f); //sets size of the sprite = body size

        player = GameData.Player.PLAYER1;

        body.setUserData(player); //links the class with the body


        direction = true;
        maxSpeed = 10;
        hasJump = false;
        canJump = false;


        currentState = State.IDLE;
        state_new = true;


    }

    private void objInit(){
        bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody; //makes the type dynamic (can move and be affected by forces /impulses)
        bodyDef.position.set(Window.camera.viewportWidth / 2 , Window.camera.viewportHeight / 2); //position of body
        bodyDef.fixedRotation = true; //makes sure the body doesn't rotate when it's hit

        body = Window.world.createBody(bodyDef); //puts the body in the world

        PolygonShape bodyShape = new PolygonShape(); //the shape of the body is a polygon
        bodyShape.setAsBox(bodyWidth, bodyHeight);   //aka a box aka a rectangle aka a square

        FixtureDef fixDef = new FixtureDef(); //the boxes fixture
        fixDef.shape = bodyShape; //sets fixture to shape of the body
        fixDef.density = 0.5f;
        fixDef.restitution = 0.0f; //bounciness
        fixDef.friction = 0.5f;



        body.createFixture(fixDef); //puts the fixture on the body




    }

    public Sprite getSprite(){ //returns the sprite (this contains position as well) and updates the body
        executeState();
        return playerSprite;
    }

    private void executeState(){ //this executes the state that the character is currently in

        playerSprite.setPosition(body.getPosition().x - bodyWidth, body.getPosition().y - bodyHeight); //set sprite equal to body

        switch (currentState) { //executes the state
            case IDLE:
                St_Idle();
                break;
            case WALKING:
                St_Walking();
                break;
            case AIR:
                St_Air();
                break;
            case ANIMATION:
                St_Animation();
                break;
            default:
                St_Idle();
                break;
        }

    }

    public void St_Idle(){ //the character is not moving
        state_new = false;
        //body.setLinearVelocity(0f, body.getLinearVelocity().y);

        if (Window.key.numpad1) {
            currentAttack = Attack.JAB;
            switchState(State.ANIMATION);
        }
        if (Window.key.numpad3)   //jump
            switchState(State.AIR);

        if (Window.key.left && Window.key.right) //switch to walking state if left OR right
            switchState(State.IDLE);             //if left and right remain idle
        else if (Window.key.left || Window.key.right)
            switchState(State.WALKING);

    }

    public void St_Walking(){ //the character is moving on the ground
        state_new = false;

        horizontalMovement(); //moves character left or right

        if (Window.key.numpad1) {
            currentAttack = Attack.S_TILT;
            switchState(State.ANIMATION);
        }
        if (Window.key.numpad3) //jump if pressed
            switchState(State.AIR);

        if (Window.key.left && Window.key.right)  //if neither or both l & r are pressed stop moving
            switchState(State.IDLE);
        else if (!Window.key.left && !Window.key.right)
            switchState(State.IDLE);

    }

    public void St_Air(){ //if the character is in the air
        if (state_new){ //if this is the first step of the state
            body.setLinearVelocity(body.getLinearVelocity().x, .01f); //give the character a little push
            body.applyLinearImpulse(0f, 125f, bodyWidth / 2, bodyHeight / 2, false); //push harder for the jump

            hasJump = true; //gives the character a double jump
            state_new = false;
        }
        else if (Window.key.numpad3 && hasJump && canJump){
            body.setLinearVelocity(body.getLinearVelocity().x, .01f);
            body.applyLinearImpulse(0f, 125f, bodyWidth / 2, bodyHeight / 2, false);
            hasJump = false;
            canJump = false;
        }

        if (!Window.key.numpad3) //can use the double jump if player has let go of the jump key
            canJump = true;

        horizontalMovement(); //move horizontally

        if (body.getLinearVelocity().y == 0.0f){ //if the body hits the ground
            hasJump = false;
            canJump = false;

            if (Window.key.left || Window.key.right) //goes to a different state depending on if keys are pressed
                switchState(State.WALKING);
            else
                switchState(State.IDLE);
        }


    }

    public void St_Animation(){
        if (state_new) {

            animationTimer = new FrameTimer(GameData.AttackData.getFrames(currentAttack));

            Vector2 position = GameData.AttackData.getPosition(body, bodyWidth, bodyHeight, currentAttack, direction);
            Vector2 dimension = GameData.AttackData.getDimension(currentAttack);
            Vector2 knockback = GameData.AttackData.getKnockback(currentAttack, direction);

            hitbox = new Hitbox(dimension.x, dimension.y * Window.yConst, position.x, position.y, knockback, player);
            hitbox.spawnHitbox();
            state_new = false;
        }

        //the hitbox exists here
        animationTimer.incrementFrame();

        if (animationTimer.timerDone(false)) {
            animationTimer = null;
            hitbox.destroyHitbox();
            hitbox = null;
            switchState(State.IDLE);
        }
    }

    private void switchState(State newState){ //this method just changes which method
        state_new = true;                    //will be run evey step by changing the state
        currentState = newState;
        //System.out.println(stateToString());
    }

    public String stateToString(){ //turns the state into a readable form for debugging
        switch (currentState){
            case IDLE:
                return "idle";
            case WALKING:
                return "walking";
            case AIR:
                return "air";
            case ANIMATION:
                return "animation - ";
            default:
                return "not set";
        }
    }

    private void horizontalMovement(){ //checks for and gives horizontal movement
        if (Window.key.left) {

            if (body.getLinearVelocity().x == 0)
                body.setLinearVelocity(-.01f, body.getLinearVelocity().y);

            if (direction){
                playerSprite.flip(true, false);
                direction = false;
            }

            body.applyLinearImpulse(-5f, 0f, bodyWidth / 2, bodyHeight / 2, false);
        }

        if (Window.key.right) {
            if (body.getLinearVelocity().x == 0)
                body.setLinearVelocity(.01f, body.getLinearVelocity().y);

            if (!direction){
                playerSprite.flip(true, false);
                direction = true;
            }

            body.applyLinearImpulse(5f, 0f, bodyWidth / 2, bodyHeight / 2, false);
        }

        setMaxSpeed(maxSpeed); //caps speed
    }

    private void setMaxSpeed(float maxV){ //this method changes the speed to the maximum speed defined if it goes over
        if(body.getLinearVelocity().x > maxV)
            body.setLinearVelocity(maxV, body.getLinearVelocity().y);
        else if (body.getLinearVelocity().x < -maxV)
            body.setLinearVelocity(-maxV, body.getLinearVelocity().y);
    }



}