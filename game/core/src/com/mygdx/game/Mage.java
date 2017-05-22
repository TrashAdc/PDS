package com.mygdx.game;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.List;

/**
 * created by ryan v on 5/21/2017
 **/
public class Mage extends Character {

    private FrameTimer specialTimer, silenceTimer, fireballTimer, explosionTimer;

    private List<MovingHitbox> projectileList;


    private boolean silenceActive;
    private Hitbox silenceBox;
    private Sprite magicDust, explosionSpr1, explosionSpr2;

    private MovingHitbox cutterBox;
    private boolean recoveryUsed, buffer;

    private Hitbox explosionHitbox1, explosionHitbox2;


    public Mage(GameData.Player player) {
        super(player);
        projectileList = new ArrayList<MovingHitbox>();

        silenceActive = false;
        magicDust = new Sprite(new Texture("core/assets/image/magicdust.png"));
        magicDust.setSize(5f, 5f * Window.yConst);

        explosionSpr1 = new Sprite(new Texture("core/assets/image/Explosion.png"));
        explosionSpr1.setSize(5f, 5f * Window.yConst);
        explosionSpr2 = new Sprite(new Texture("core/assets/image/Explosion.png"));
        explosionSpr2.setSize(5f, 5f * Window.yConst);

        recoveryUsed = false;
        buffer = false; //used so the up special isnt usable twice
    }

    @Override
    public void St_Special(){
        if (state_new) {
        }

        switch(currentSpecial){
            case N_SPECIAL:
                explosion();
                break;
            case S_SPECIAL:
                fireball();
                break;
            case D_SPECIAL:
                if (!silenceActive || specialTimer != null)
                    silence();
                else
                    switchState(State.IDLE);
                break;
            case U_SPECIAL:
                if (!recoveryUsed)
                    airCutter();
                else
                    switchState(State.IDLE);
                break;
        }


        state_new = false;
    }

    /*neutral special-
    the mage channels her magic and creates explosions on both sides of her after a short time
    the channel cannot be stopped early.
     */
    private void explosion(){
        if (state_new)
            explosionTimer = new FrameTimer(60);

        if (explosionTimer != null){
            explosionTimer.incrementFrame();

            if (explosionTimer.timerDone(false)){
                explosionTimer = null;

                specialTimer = new FrameTimer(45);
                explosionHitbox1 = new Hitbox(3f, 2.5f * Window.yConst, body.getPosition().x - bodyWidth, body.getPosition().y, calculateKnockback(17, 30f, 30f, true), 17, player);
                explosionHitbox1.spawnHitbox();
                explosionHitbox2 = new Hitbox(3f, 2.5f * Window.yConst, body.getPosition().x + bodyWidth, body.getPosition().y, calculateKnockback(17, 30f, 30f, true), 17, player);
                explosionHitbox2.spawnHitbox();

            }
        }

        if (specialTimer != null){
            specialTimer.incrementFrame();

            int d = (direction) ? 1 : -1;

            explosionSpr1.setPosition(body.getPosition().x, body.getPosition().y - bodyHeight);
            explosionSpr1.draw(Window.batch);
            explosionSpr2.setPosition(body.getPosition().x - bodyWidth * 2.5f, body.getPosition().y - bodyHeight);
            explosionSpr2.draw(Window.batch);

            if (specialTimer.timerDone(false)){
                specialTimer = null;
                explosionHitbox1.destroyHitbox();
                explosionHitbox1 = null;
                explosionHitbox2.destroyHitbox();
                explosionHitbox2 = null;
                switchState(State.IDLE);
            }

        }
    }

    /*side special-
    the mage launches a ball of fire at her opponent.
    only one can be fired at a time.
     */
    private void fireball(){
        if (fireballTimer == null) {
            Vector2 lancePos = GameData.AttackData.getPosition(body, GameData.CharacterData.getBodySize(this).x, GameData.CharacterData.getBodySize(this).y, Attack.S_TILT, direction, this);
            String filepath = "core/assets/image/fireball.png";
            projectileList.add(new MovingHitbox(1f, 1f * Window.yConst, lancePos.x, lancePos.y, calculateKnockback(7, 10f, 10f, true), 7, player, direction, false, 180, filepath));
            projectileList.get(projectileList.size() - 1).spawnHitbox();
            projectileList.get(projectileList.size() - 1).getHitboxBody().setLinearVelocity(25f * projectileList.get(projectileList.size() - 1).getHDirection(), 0f);
            fireballTimer = new FrameTimer(75);
        }
        else {
            fireballTimer.incrementFrame();
            if (fireballTimer.timerDone(false)) {
                fireballTimer = null;
                switchState(State.IDLE);
            }
        }
    }

    /*up special-
     the mage uses projectile magic to propel herself upward
     leaves a projectile that travels downward for 1 second
     */
    private void airCutter() {
            String filepath = "core/assets/image/airCutter.png";
            cutterBox = new MovingHitbox(3f, 1.5f, body.getPosition().x - (bodyWidth / 2), body.getPosition().y - bodyHeight, calculateKnockback(10, 0, 25, false), 10, player, direction, false, 60, filepath);
            cutterBox.spawnHitbox();
            cutterBox.getHitboxBody().setLinearVelocity(0f, -25f);

            body.setLinearVelocity(0f, 65f);
            recoveryUsed = true;
            buffer = true;

            switchState(State.IDLE);
    }

    /*down special-
    the mage releases a swirl of magic powder that silences anything it touches.
    if it comes in contact with an opponent, they will not be able to use any moves for 1.5 seconds.
    cannot be used if the opponent is already silenced
     */
    private void silence() {
        int d = (direction) ? 1 : -1;
        if (state_new) {
            silenceBox = new Hitbox(2.5f, 2.5f * Window.yConst, body.getPosition().x + (bodyWidth * d), body.getPosition().y, new Vector2(0, 0), 3, player);
            silenceBox.spawnHitbox();
            specialTimer = new FrameTimer(25);
        }

        silenceBox.getHitboxBody().setLinearVelocity(body.getLinearVelocity());
        if (d == 1)
            magicDust.setPosition(body.getPosition().x * d, body.getPosition().y - bodyHeight);
        else if (d == -1)
            magicDust.setPosition(body.getPosition().x - bodyWidth * 2.25f, body.getPosition().y - bodyHeight);
        magicDust.draw(Window.batch);
        specialTimer.incrementFrame();

        if (hitDetect()){ //if the hit connects, silence the character
            silenceActive = true;
            silenceTimer = new FrameTimer(90);
            Window.getCharacter(opponent).changeSilence(true);
        }

        if (specialTimer.timerDone(false)){
            silenceBox.destroyHitbox();
            silenceBox = null;
            specialTimer = null;
            switchState(State.IDLE);
        }
    }

    private boolean hitDetect(){

            if (player == GameData.Player.PLAYER1 && ListenerClass.p1Hit) {
                return true;
            }
            else if (player == GameData.Player.PLAYER2 && ListenerClass.p2Hit ) {
                return true;
            }
            return false;
    }

    @Override
    protected void runFrame(){
        if (silenceTimer != null){
            silenceTimer.incrementFrame();

            if (silenceTimer.timerDone(false)) {
                silenceActive = false;
                silenceTimer = null;
                Window.getCharacter(opponent).changeSilence(false);
            }
        }

        if (recoveryUsed && !buffer){
            //if (player == GameData.Player.PLAYER1 && ListenerClass.p1Hit)
            //    dashed = false;
            //else if (player == GameData.Player.PLAYER2 && ListenerClass.p2Hit)
            //    dashed = false;
            if (player == GameData.Player.PLAYER1 && ListenerClass.p1Stage) {
                stunTimer = new FrameTimer(8);
                switchState(State.STUNNED);
                recoveryUsed = false;
            }
            else if (player == GameData.Player.PLAYER2 && ListenerClass.p2Stage) {
                stunTimer = new FrameTimer(15);
                switchState(State.STUNNED);
                recoveryUsed = false;
            }
        }
        if (buffer)
            buffer = false;

        if (cutterBox != null){
            cutterBox.incrementFrame();
            if(cutterBox.timerCheck()){
                cutterBox.destroyHitbox();
                cutterBox = null;
            }
        }

        if (!projectileList.isEmpty()){
            for (int i = 0; i < projectileList.size(); i++){
                //projectileList.get(i).getHitboxBody().setLinearVelocity(6f *  projectileList.get(projectileList.size() - 1).getHDirection(), -3f);
                projectileList.get(i).incrementFrame();

                if (projectileList.get(i).timerCheck()) {
                    //System.out.println(i + " has been destroyed.");
                    projectileList.get(i).destroyHitbox();
                    projectileList.remove(i);
                }

            }
        }


    }


}
