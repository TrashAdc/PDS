package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

/**
 * created by ryan v on 4/23/2017
 **/
public class Assassin extends Character {
    private boolean katanaActive;
    private boolean critAdded;
    private Hitbox katanaHitbox;
    private FrameTimer specialTimer;
    private float critChance;

    public Assassin(GameData.Player player){
        super(player);
        critChance = 0.0f;
        critAdded = false;

        katanaActive = false;


    }

    @Override
    public void St_Special(){
        if (state_new) {
        }

        switch(currentSpecial){
            case N_SPECIAL:
                shurikenThrow();
                break;
            case S_SPECIAL:
                katanaSlash();
                break;
            case D_SPECIAL:
                ambush();
                break;
            case U_SPECIAL:
                airBlade();
                break;
        }


        state_new = false;
    }

    /*neutral special-
      the assassin throws 3 shurikens in a horizontal direction,
      dealing small damage and knockback.
     */
    private void shurikenThrow(){

    }

    /*side special-
      the assassin uses his katana to slash through the opponent,
      dealing damage to the area around and slightly above him.
     */
    private void katanaSlash(){
        if (state_new){
            int d = (direction) ? 1 : -1;
            katanaHitbox = new Hitbox(2.5f, .5f, body.getPosition().x  + (bodyWidth * d), body.getPosition().y, calculateKnockback(9, 50f, 25f, true), 9, player);
            katanaHitbox.spawnHitbox();
            int angle = (direction) ? 60 : 120;
            katanaHitbox.getHitboxBody().setTransform(katanaHitbox.getHitboxBody().getPosition().x, katanaHitbox.getHitboxBody().getPosition().y, angle); //set initial angle
            float angleV = (direction) ? -10 : 10;
            katanaHitbox.getHitboxBody().setAngularVelocity(angleV); //set turning velocity

            specialTimer = new FrameTimer(30);
        }

        katanaHitbox.getHitboxBody().setLinearVelocity(body.getLinearVelocity());

        specialTimer.incrementFrame();

        if (player == GameData.Player.PLAYER1 && ListenerClass.p1Hit && !critAdded) {
            critChance += 4.5;
            critAdded = true;
        }
        else if (player == GameData.Player.PLAYER2 && ListenerClass.p2Hit && !critAdded) {
            critChance += 4.5;
            critAdded = true;
        }


        if (specialTimer.timerDone(false)){
            katanaHitbox.destroyHitbox();
            katanaHitbox = null;
            specialTimer = null;
            switchState(State.IDLE);
        }




    }

    /*down special-
      the assassin goes invisible for a short period, preparing for an attack.
      after a short time, the assassin reappears and dashes in a horizontal direction.
     */
    private void ambush(){

    }

    /*up special-
      the assassin grabs his blade and dashes diagonally in to the air, dealing damage and knockback
      to anything in his way.
     */
    private void airBlade(){

    }




}