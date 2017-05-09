package com.mygdx.game;

import com.badlogic.gdx.math.Vector2;

/**
 * Created by ryan vanek on 4/20/2017.
 */
public class Knight extends Character {

    private int lanceCharge; //charge for lanceStrike special

    private boolean slammedUp; //first stage of slamjam special (going up)
    private boolean slammedDown; //second stage of slamjam special (going down)

    private boolean fortified; //whether down special is active

    private FrameTimer timer, fortifyTimer; //timer for special attacks
    private Hitbox hitbox, hitbox2; //hitbox of special attack
    private boolean specialReady; //use the special move!!!
    private boolean specialOver; //prevents more specials from being cast at the same time

    public Knight(GameData.Player player){
        super(player);
        specialReady = false;

        lanceCharge = 0;

        slammedUp = false;
        slammedDown = false;
    }
    @Override
    public void St_Special(){ //is run when a special is used
        if (state_new){
            lanceCharge = 0;
            specialReady = false;

            state_new = false;
        }

        if (!specialOver) {
            switch (currentSpecial) {
                case N_SPECIAL:
                    lanceStrike();
                    break;
                case S_SPECIAL:
                    lanceThrow();
                    break;
                case D_SPECIAL:
                    fortify();
                    break;
                case U_SPECIAL:
                    sheildSlamJam();
                    break;
            }
        }

        if (timer != null){
            timer.incrementFrame();
            hitbox.getHitboxBody().setLinearVelocity(body.getLinearVelocity());
            //System.out.println(timer.getCurrentFrame());

            if (timer.timerDone(false)) {
                hitbox.destroyHitbox(); //remove the hitbox from the world
                hitbox = null;
                timer = null;
                switchState(State.IDLE);
                specialOver = false;
            }
        }

    }

    //here are the methods for the special moves

    /*neutral special
      the knight draws back his lance to release a powerful blow*/
    private void lanceStrike(){
        if (special){
            if (lanceCharge++ >= 100)  //if the charge is more than max (100), then release otherwise keep charging
                specialReady = true;

        }

        else //release early if the key is released
            specialReady = true;

        if (specialReady) {
            Vector2 lancePos = GameData.AttackData.getPosition(body, GameData.CharacterData.getBodySize(this).x, GameData.CharacterData.getBodySize(this).y, Attack.S_TILT, direction, this);
            int damage = 10 + (lanceCharge / 10);
            hitbox = new Hitbox(2f, .5f * Window.yConst, lancePos.x, lancePos.y, calculateKnockback(damage, 50f, 30f, true), damage, player); //creates the hitbox object
            hitbox.spawnHitbox(); //puts the hitbox in the world
            timer = new FrameTimer(45);
            specialOver = true;
        }


    }


    /*side special
      the knight throws his lance as a projectile until it hits something*/
    private void lanceThrow(){
        lanceStrike();
    }


    /*down special special
      the knight braces himself for impact and gains defense against attacks but
      loses some movement speed
      this effect lasts for 3 seconds, and becomes stunned for .5 seconds
      after the effect wears off.*/
    private void fortify(){
        if (!fortified) {
            knockbackMultiplier = .75f;
            maxSpeed = 5f;
            fortified = true;
            fortifyTimer = new FrameTimer(180);
            System.out.println("fortified");
        }
        switchState(State.IDLE);
    }

    /*up special
      the knight leaps into the air and dives down with considerable force, knocking up and damaging opponents.*/
    private void sheildSlamJam(){
        float spikeKb;
        if (Window.getCharacter(opponent).body.getLinearVelocity().y == 0f)
            spikeKb = 50f;
        else
            spikeKb = -1000;

        if (!slammedUp) {
            body.setLinearVelocity(0f, 65f);
            slammedUp = true;
            hitbox = new Hitbox(bodyWidth, .25f * Window.yConst, body.getPosition().x, body.getPosition().y + ((bodyHeight / 1.5f) * Window.yConst), calculateKnockback(7, 0f, 35f, true), 7, player); //creates the hitbox object
            hitbox.spawnHitbox(); //puts the hitbox in the world
        }
        else if (slammedUp && !slammedDown && body.getLinearVelocity().y <= 0f) {
            hitbox.destroyHitbox();
            hitbox = null;
            hitbox = new Hitbox(bodyWidth * 1.5f, .5f * Window.yConst, body.getPosition().x, body.getPosition().y - ((bodyHeight / 1.5f) * Window.yConst), calculateKnockback(9, 0f, spikeKb, true), 7, player);
            hitbox.spawnHitbox();
            body.setLinearVelocity(0f, -100f);
            slammedDown = true;
        }
        else if (body.getLinearVelocity().y == 0f && slammedDown && slammedUp) {
            hitbox.destroyHitbox();
            hitbox = null;
            switchState(State.IDLE);
            slammedUp = false;
            slammedDown = false;
        }
        else {
            hitbox.getHitboxBody().setLinearVelocity(body.getLinearVelocity());
            horizontalMovement();
        }


    }

    @Override
    protected void runFrame(){ //this method will run every single frame.
                             // should mainly be used for timers or specials such as knight's fortify.
        if (fortifyTimer != null) {
            fortifyTimer.incrementFrame();

            if (fortifyTimer.timerDone(false) && fortified) {
                stunTimer = new FrameTimer(30);
                switchState(State.STUNNED);
                fortifyTimer = null;
                fortified = false;
                maxSpeed = GameData.CharacterData.getMaxSpeed(this);
                knockbackMultiplier = 1.0f;
                System.out.println("unfortified");
            }
        }

    }




}