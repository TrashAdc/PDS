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
        ATTACK,
        SPECIAL,
        LEDGE,
        HITSTUN,
        STUNNED,
        DEAD,
        SUPERPOSITIONED
    }

    public enum Attack { //attacks include tilts, (sm/tr)ash attacks, and jabs (dash attacks if we implement running)
        JAB,               //numpad1 + any direction
        S_TILT, U_TILT, D_TILT,
        S_SMASH, U_SMASH, D_SMASH;

        public enum Special {
            N_SPECIAL, S_SPECIAL, U_SPECIAL, D_SPECIAL //specials are attacks that differ very greatly from other characters, but there are only 4 of them
        }                                              //numpad2 + any direction
    }

    //maybe aerial attacks will come in the future but for now use basic attacks in the air

    //<editor-fold desc="keys">
    protected boolean left = false;
    protected boolean right = false;
    protected boolean up = false;
    protected boolean down = false;
    protected boolean attack = false;
    protected boolean special = false;
    protected boolean jump = false;
    //</editor-fold>

    private State currentState; //current state of character
    protected boolean state_new; //is true on only the first loop of the state method

    private Attack currentAttack;
    protected Attack.Special currentSpecial;
    protected GameData.Player player, opponent;

    private Sprite playerSprite, indicator; //the image of the character, may not need to be here with animation class
    private BodyDef bodyDef;   //body define for box2d
    protected Body body;         //actual box2d body
    protected float bodyWidth, bodyHeight; //height and width of body

    protected float knockbackMultiplier, damageMultiplier, damageTakenMultiplier;

    protected boolean direction; // 0 is left, 1 is right
    protected float maxSpeed; //max speed at which the character can move
    private boolean hasJump; //if the character has a double jump
    private boolean canJump; //if the character can use the double jump atm
    private boolean jumpCD; //if the character can double jump

    private Hitbox hitbox;

    private FrameTimer animationTimer, hitstunTimer;
    protected FrameTimer stunTimer;

    public Character(GameData.Player p){

        bodyWidth = GameData.CharacterData.getBodySize(this).x;                 //set width and height
        bodyHeight = GameData.CharacterData.getBodySize(this).y;

        objInit(); //does box2d stuff

        playerSprite = GameData.CharacterData.getSprite(this);  //gets/sets image
        playerSprite.setPosition(body.getPosition().x - bodyWidth, body.getPosition().y - bodyHeight); //sets initial position = body pos
        playerSprite.setSize(bodyWidth * 2f, bodyHeight * 2f); //sets size of the sprite = body size

        indicator = (p == GameData.Player.PLAYER1) ? new Sprite(new Texture("core/assets/image/indicator1.png")) : new Sprite(new Texture("core/assets/image/indicator2.png"));
        indicator.setSize(2f, 4f * Window.yConst);

        player = p; //sets what player the character is for score and controls
        opponent = (p == GameData.Player.PLAYER1) ? GameData.Player.PLAYER2 : GameData.Player.PLAYER1; //sets the opposite player

        body.setUserData(player); //links the class with the body

        knockbackMultiplier = 1.0f;

        direction = true;
        maxSpeed = GameData.CharacterData.getMaxSpeed(this);
        hasJump = true;
        canJump = true;
        jumpCD = true;

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
        fixDef.density = GameData.CharacterData.getDensity(this);
        fixDef.restitution = 0.0f; //bounciness
        fixDef.friction = .8f;
        fixDef.filter.categoryBits = 0x0002;
        fixDef.filter.maskBits = 0x0004;


        body.createFixture(fixDef); //puts the fixture on the body

    }

    public Sprite getSprite(){ //returns the sprite (this contains position as well) and updates the body
        runFrame();
        executeState();
        indicator.setPosition(body.getPosition().x - indicator.getWidth() / 2, body.getPosition().y + playerSprite.getHeight() / 2);
        indicator.draw(Window.batch);
        //System.out.println(body.getPosition());
        return playerSprite;
    }
    protected void runFrame(){
        //this method will run every single frame.
        //should mainly be used for timers or specials such as knight's fortify.
    }

    private void executeState(){ //this executes the state that the character is currently in

        playerSprite.setPosition(body.getPosition().x - bodyWidth, body.getPosition().y - bodyHeight); //set sprite equal to body
        updateKeys(); //update what keys are pressed

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
            case ATTACK:
                St_Attack();
                break;
            case SPECIAL:
                St_Special();
                break;
            case HITSTUN:
                St_Hitstun();
                break;
            case STUNNED:
                St_Stunned();
                break;
            default:
                St_Idle();
                break;
        }

    }

    private void updateKeys(){ //updates keys that are active for multiple players

        if (player == GameData.Player.PLAYER1){
            left = Window.key.A;
            right = Window.key.D;
            up = Window.key.W;
            down = Window.key.S;
            attack = Window.key.V;
            special = Window.key.B;
            jump = Window.key.N;
        }

        else {
            left = Window.key.left;
            right = Window.key.right;
            up = Window.key.up;
            down = Window.key.down;
            attack = Window.key.numpad1;
            special = Window.key.numpad2;
            jump = Window.key.numpad3;
        }
    }

    public void St_Idle(){ //the character is not moving
        state_new = false;
        //body.setLinearVelocity(0f, body.getLinearVelocity().y);
        attackInput();

        if (jump)   //jump
            switchState(State.AIR);

        if (left && right) //switch to walking state if left OR right
            switchState(State.IDLE);             //if left and right remain idle
        else if (left ||right)
            switchState(State.WALKING);

    }

    public void St_Walking(){ //the character is moving on the ground
        state_new = false;

        horizontalMovement(); //moves character left or right
        attackInput();

        if (jump) //jump if pressed
            switchState(State.AIR);

        if (left && right)  //if neither or both l & r are pressed stop moving
            switchState(State.IDLE);
        else if (!left && !right)
            switchState(State.IDLE);

    }

    public void St_Air(){ //if the character is in the air
        if (state_new){ //if this is the first step of the state
            if (canJump) {
                body.setLinearVelocity(body.getLinearVelocity().x, .01f); //give the character a little push
                body.applyLinearImpulse(0f, 125f, bodyWidth / 2, bodyHeight / 2, false); //push harder for the jump
            }

            jumpCD = true;
            canJump = false;
            state_new = false;
        }
        else if (jump && hasJump && !jumpCD){
            body.setLinearVelocity(body.getLinearVelocity().x, .01f);
            body.applyLinearImpulse(0f, 125f, bodyWidth / 2, bodyHeight / 2, false);
            hasJump = false;
        }


        if (!jump) //can use the double jump if player has let go of the jump key
            jumpCD = false;

        horizontalMovement(); //move horizontally
        attackInput(); //aerial attacks

        if (body.getLinearVelocity().y == 0.0f){ //if the body hits the ground
            hasJump = true;
            canJump = true;

            if (left || right) //goes to a different state depending on if keys are pressed
                switchState(State.WALKING);
            else
                switchState(State.IDLE);
        }


    }

    public void St_Attack(){
        if (state_new) {


            animationTimer = new FrameTimer(GameData.AttackData.getFrames(currentAttack, this));

            Vector2 position = GameData.AttackData.getPosition(body, bodyWidth, bodyHeight, currentAttack, direction, this);
            Vector2 dimension = GameData.AttackData.getDimension(currentAttack, this);
            Vector2 knockback = calculateKnockback();
            int damage = GameData.AttackData.getDamage(currentAttack, this);

            hitbox = new Hitbox(dimension.x, dimension.y * Window.yConst, position.x, position.y, knockback, damage, player); //creates the hitbox object
            hitbox.spawnHitbox(); //puts the hitbox in the world
            state_new = false;
        }

        //the hitbox exists here
        animationTimer.incrementFrame();
        //if this is a basic attack, move it with the character
        hitbox.getHitboxBody().setLinearVelocity(body.getLinearVelocity());

        if (animationTimer.timerDone(false)) {
            animationTimer = null;
            hitbox.destroyHitbox(); //remove the hitbox from the world
            hitbox = null;
            switchState(State.IDLE);
        }
    }

    public void St_Special(){
        //implement in child classes
        switchState(State.IDLE);
    }

    public void St_Hitstun(){
        if (state_new) {
            hitstunTimer = new FrameTimer(15);
            state_new = false;
        }

        hitstunTimer.incrementFrame();

        if (hitstunTimer.timerDone(false))
            switchState(State.IDLE);
    }

    public void St_Stunned(){
        if (stunTimer != null && stunTimer.timerDone(false)) {
            //System.out.println(stunTimer.getCurrentFrame());
            stunTimer = null;
            switchState(State.IDLE);
        }
        else
            stunTimer.incrementFrame();

    }

    public void inflictHitstun(){
        switchState(State.HITSTUN);
    }

    protected void switchState(State newState){ //this method just changes which method
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
            case ATTACK:
                return "attacking";
            case SPECIAL:
                return "special";
            case HITSTUN:
                return "hitstun";
            case STUNNED:
                return "stunned";
            case DEAD:
                return "dead";
            case LEDGE:
                return "ledge";
            default:
                return "not set";
        }
    }

    protected void horizontalMovement(){ //checks for and gives horizontal movement
        if (left) {

            if (body.getLinearVelocity().x == 0)
                body.setLinearVelocity(-.01f, body.getLinearVelocity().y);

            if (direction){
                playerSprite.flip(true, false);
                direction = false;
            }
            if (!overMaxSpeed(maxSpeed, false))
                body.applyLinearImpulse(-10f, 0f, bodyWidth / 2, 0, false);

        }

        if (right) {
            if (body.getLinearVelocity().x == 0)
                body.setLinearVelocity(.01f, body.getLinearVelocity().y);

            if (!direction){
                playerSprite.flip(true, false);
                direction = true;
            }
            if (!overMaxSpeed(maxSpeed, true))
                body.applyLinearImpulse(10f, 0f, bodyWidth / 2, 0, false);
        }


    }

    private void attackInput(){
        if (attack) {
            if (up)
                currentAttack = Attack.U_TILT;
            else if (down)
                currentAttack = Attack.D_TILT;
            else if (left || right)
                currentAttack = Attack.S_TILT;
            else
                currentAttack = Attack.JAB;
            switchState(State.ATTACK);
        }
        else if (special) {
            if (up)
                currentSpecial = Attack.Special.U_SPECIAL;
            else if (down)
                currentSpecial = Attack.Special.D_SPECIAL;
            else if (left || right)
                currentSpecial = Attack.Special.S_SPECIAL;
            else
                currentSpecial = Attack.Special.N_SPECIAL;
            switchState(State.SPECIAL);
        }

    }


    private Vector2 calculateKnockback(){
        int p = Window.scoreData.getDamage(opponent); //opponent's damage
        int d = GameData.AttackData.getDamage(currentAttack, this); //damage of attack
        float w = 2.5f; //weight of all characters
        int dir = (direction) ? 1 : -1;
        Vector2 b = GameData.AttackData.getKnockback(currentAttack, direction, this); //base knockback

        double knockback = ((((p / 10) + ((p * d) / 20)) * w) + 18);
        //System.out.println("((((" + p + " / 10) + ((" + p + " + " + d + ") / 20)) * " + w + ") + 18) +" + b.x + " = " + knockback + b.x);
        return new Vector2(((float)knockback + Math.abs(b.x)) * dir, ((float)knockback + b.y));
    } //assumed values (mainly for regular attack)
    protected Vector2 calculateKnockback(int damage, float baseKnockbackX, float baseKnockbackY, boolean useDirection){
        int p = Window.scoreData.getDamage(opponent); //opponent's damage
        int d = damage; //damage of attack
        float w = 2.5f; //weight of all characters
        int dir = (direction) ? 1 : -1;
        if (!useDirection)
            dir = 1;
        Vector2 b = new Vector2(baseKnockbackX, baseKnockbackY); //base knockback

        double knockback = ((((p / 10) + ((p * d) / 20)) * w) + 18);
        //System.out.println("((((" + p + " / 10) + ((" + p + " + " + d + ") / 20)) * " + w + ") + 18) +" + b.x + " = " + knockback + b.x);
        return new Vector2((((float)knockback + Math.abs(b.x)) * dir), ((float)knockback + b.y));
    } //explicit values (mainly for specials)

    public float getKnockbackMultiplier(){
        return knockbackMultiplier;
    }

    public Vector2 getPosition(){
        return body.getPosition();
    }

    private boolean overMaxSpeed(float maxV, boolean positive){ //this method changes the speed to the maximum speed defined if it goes over
        if (positive) {
            if (body.getLinearVelocity().x > maxV)
                return true;
            //body.setLinearVelocity(maxV, body.getLinearVelocity().y);
        }
        else if (!positive) {
            if (body.getLinearVelocity().x < -maxV)
                return true;
            //body.setLinearVelocity(-maxV, body.getLinearVelocity().y);
        }
        return false;
    } //returns true if the character is over the max speed



}