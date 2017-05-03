package com.mygdx.game;

import com.badlogic.gdx.math.Vector2;

/**
 * Created by ryan vanek on 4/20/2017.
 */
public class Knight extends Character {

    private int lanceCharge; //charge for lanceStrike special

    private boolean slammedUp; //first stage of slamjam special (going up)
    private boolean slammedDown; //second stage of slamjam special (going down)

    private FrameTimer timer; //timer for special attacks
    private Hitbox hitbox; //hitbox of special attack
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
            hitbox = new Hitbox(2f, .5f * Window.yConst, lancePos.x, lancePos.y, calculateKnockback(damage, 50f, 30f), damage, player); //creates the hitbox object
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
      loses some movement speed*/
    private void fortify(){
        lanceStrike();
    }

    /*up special
      the knight leaps into the air and dives down with considerable force, knocking up and damaging opponents.*/
    private void sheildSlamJam(){
        if (!slammedUp) {
            body.setLinearVelocity(0f, 65f);
            slammedUp = true;
            hitbox = new Hitbox(bodyWidth, .25f * Window.yConst, body.getPosition().x, body.getPosition().y, calculateKnockback(7, 35f, 10f), 7, player); //creates the hitbox object
            hitbox.spawnHitbox(); //puts the hitbox in the world
        }
        else if (!slammedDown && body.getLinearVelocity().y <= 0f) {
            hitbox.destroyHitbox();
            hitbox = null;
            hitbox = new Hitbox(bodyWidth * 1.5f, .25f * Window.yConst, body.getPosition().x, -body.getPosition().y, calculateKnockback(9, 25f, -20f), 7, player);
            body.setLinearVelocity(0f, -6500f);
            slammedDown = true;
        }
        else if (body.getLinearVelocity().y <= 0f) {
            hitbox.destroyHitbox();
            hitbox = null;
            switchState(State.IDLE);
        }
        else {
            hitbox.getHitboxBody().setLinearVelocity(body.getLinearVelocity());
            horizontalMovement();
        }


    }




}