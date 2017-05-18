package com.mygdx.game;


import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * created by ryan v on 4/23/2017
 **/
public class Assassin extends Character {
    private boolean dashed;
    private boolean critAdded;
    private Hitbox katanaHitbox, dashHitbox, ambushHitbox;
    private FrameTimer specialTimer, shurikenTimer;
    private float critChance;

    private List<MovingHitbox> projectileList; //list of lances thrown
    private int shurikensThrown;
    private boolean canThrow;

    private int ambushCharge;
    private boolean ambushNow;

    private int mD;
    private float mK;

    public Assassin(GameData.Player player){
        super(player);
        critChance = 0.0f;
        critAdded = false;
        dashed = false;

        projectileList = new ArrayList<MovingHitbox>();
        shurikensThrown = 0;
        canThrow = true;

        ambushCharge = 0;
        ambushNow = false;

        mD = 1;
        mK = 1f;


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
                if (!dashed)
                    airBlade();
                else
                    switchState(State.IDLE);
                break;
        }


        state_new = false;
    }

    /*neutral special-
      the assassin throws 3 shurikens in a horizontal direction,
      dealing small damage and knockback.
     */
    private void shurikenThrow(){
        if (shurikensThrown < 3 && canThrow) { //if less than 3 shurikens have been thrown in this special state
            critMult();

            Vector2 shurikenPos = GameData.AttackData.getPosition(body, GameData.CharacterData.getBodySize(this).x, GameData.CharacterData.getBodySize(this).y, Attack.S_TILT, direction, this); //establish initial position
            String filepath = "core/assets/image/shuriken.png";
            projectileList.add(new MovingHitbox(.5f, .5f * Window.yConst, shurikenPos.x, shurikenPos.y, calculateKnockback(3 * mD, 0 * mK, 7f * mK, true), 3 * mD, player, direction, false, 180, filepath)); //create the hitbox
            projectileList.get(projectileList.size() - 1).spawnHitbox(); //spawn it
            projectileList.get(projectileList.size() - 1).getHitboxBody().setLinearVelocity(30f * projectileList.get(projectileList.size() - 1).getHDirection(), 0f); //set the velocity

            if (shurikensThrown == 0) //if this is the first shuriken, initiate the timer (for time between each shuriken)
                shurikenTimer = new FrameTimer(15);
            shurikensThrown++; //add a shuriken to the count.
            canThrow = false;
        }

        else if (shurikensThrown >= 3){ //if the three shurikens have been thrown
            if (specialTimer == null)
                specialTimer = new FrameTimer(60); //start the 'lag' timer
            specialTimer.incrementFrame();

            if (specialTimer.timerDone(false)){ //'destruct' everything and stuff
                shurikenTimer = null;
                specialTimer = null;
                canThrow = true;
                shurikensThrown = 0;
                resetMult();
                switchState(State.IDLE);
            }
        }

        addCritChance(3f); //add critical chance

        //increment timers if they are active
        if (shurikenTimer != null)
            shurikenTimer.incrementFrame();

        if (shurikenTimer != null && shurikenTimer.timerDone(false) && shurikensThrown < 3){
            canThrow = true;
            shurikenTimer.resetTimer();
        }

    }

    /*side special-
      the assassin uses his katana to slash through the opponent,
      dealing damage to the area around and slightly above him.
     */
    private void katanaSlash(){
        if (state_new){
            critMult();

            int d = (direction) ? 1 : -1; //get the direction the player is facing
            katanaHitbox = new Hitbox(2.5f, .5f, body.getPosition().x  + (bodyWidth * d), body.getPosition().y, calculateKnockback(9 * mD, 50f  * mK, 25f * mK, true), 9 * mD, player); //make the hitbox
            katanaHitbox.spawnHitbox(); //spawn it
            int angle = (direction) ? 60 : 120;
            katanaHitbox.getHitboxBody().setTransform(katanaHitbox.getHitboxBody().getPosition().x, katanaHitbox.getHitboxBody().getPosition().y, angle); //set initial angle
            float angleV = (direction) ? -10 : 10;
            katanaHitbox.getHitboxBody().setAngularVelocity(angleV); //set turning velocity

            specialTimer = new FrameTimer(30);
        }

        katanaHitbox.getHitboxBody().setLinearVelocity(body.getLinearVelocity());

        specialTimer.incrementFrame();

        addCritChance(9); //add critical chance

        state_new = false;

        if (specialTimer.timerDone(false)){
            katanaHitbox.destroyHitbox();
            katanaHitbox = null;
            specialTimer = null;
            critAdded = false;
            resetMult();
            switchState(State.IDLE);
            System.out.println(critChance);
        }




    }

    /*down special-
      the assassin goes invisible for a short period, preparing for an attack.
      after a short time, the assassin reappears and dashes in a horizontal direction.
     */
    private void ambush(){
        if (special && specialTimer == null){ //the player can channel the move for up to 1 second
            if (ambushCharge++ >= 60)
                ambushNow = true;
        }
        else if (specialTimer == null) //but can let go early if so desired
            ambushNow = true;

        if (ambushNow){ //create the hitbox and move the player if ready
            critMult();

            int d = (direction) ? 1 : -1;
            body.setTransform(getPosition().x + (((float)ambushCharge / 4f) * d), getPosition().y, 0);
            specialTimer = new FrameTimer(20);
            ambushHitbox = new Hitbox(bodyWidth * 1.5f, bodyHeight * 1.5f, body.getPosition().x, body.getPosition().y, calculateKnockback(11 * mD, 30f * mK, 30f * mK, true), 11 * mD, player);
            ambushHitbox.spawnHitbox();
            ambushNow = false;
        }

        addCritChance(11f); //add critical chance

        if (specialTimer != null) { //if the timer is done, clean up
            specialTimer.incrementFrame();
            if (specialTimer.timerDone(false)){
                specialTimer = null;
                ambushHitbox.destroyHitbox();
                ambushHitbox = null;

                ambushCharge = 0;

                resetMult();

                stunTimer = new FrameTimer(20);
                switchState(State.STUNNED);
            }
        }
    }

    /*up special-
      the assassin grabs his blade and dashes diagonally in to the air, dealing damage and knockback
      to anything in his way.
     */
    private void airBlade(){
        if (state_new){

            critMult();

            specialTimer = new FrameTimer(10);
            dashHitbox = new Hitbox(bodyWidth * 1.5f, bodyHeight * 1.5f, body.getPosition().x, body.getPosition().y, calculateKnockback(7 * mD, 18f * mK, 18f * mK, true), 7 * mD, player);
            dashHitbox.spawnHitbox();
        }

        int d = (direction) ? 1 : -1;
        body.setLinearVelocity(200f * d, 275f);
        dashHitbox.getHitboxBody().setLinearVelocity(body.getLinearVelocity());

        addCritChance(7);

        specialTimer.incrementFrame();
        //System.out.println(specialTimer.getCurrentFrame());



        if (specialTimer.timerDone(false)){
            body.setLinearVelocity(0f, 0f);
            dashHitbox.destroyHitbox();
            dashHitbox = null;
            specialTimer = null;
            critAdded = false;
            resetMult();
            switchState(State.IDLE);
            dashed = true;
            //System.out.println(critChance);
            int thisintistokeepitfromtellingmeihavedupicatecodeiknowihavedupiclatecodebutidontcareatthispoint;
        }


    }

    /*unique passive-
      after attacking his enemy multiple times, the assassin eventually finds a weak spot and takes advantage of it.
      using and connecting any special will increase the critical chance of the assassin's
      next special attack by a fourth of the move's damage.
      if the critical strike lands, the special move deals 1.5x damage, 1.5x knockback, and the critical strike chance drops back to 0.
     */
    private boolean criticalStrike(){
        Random rand = new Random();
        float luckyNumber = rand.nextInt(1000) / 10; //return true if the lucky number is within the crit chance range.
        System.out.println(luckyNumber);
        if (luckyNumber <= critChance) {
            System.out.println("CRITICAL!/n crit chance - " + critChance + "/nnumber generated - " + luckyNumber);
            critChance = 0f;
            return true;
        }
        else
            return false;
    }

    private void critMult(){
        if (criticalStrike()){
            mD = 2;
            mK = 1.5f;
        }
        else{
            mD = 1;
            mK = 1f;
        }
    }
    private void resetMult(){
        mD = 1;
        mK = 1f;
    }

    private void addCritChance(float dmg){
        if (player == GameData.Player.PLAYER1 && ListenerClass.p2Hit  ) {
            System.out.println(critChance);
            critChance += dmg/4f;
            critAdded = true;
        }
        else if (player == GameData.Player.PLAYER2 && ListenerClass.p1Hit ) {
            System.out.println(critChance);
            critChance += dmg/4f;
            critAdded = true;
        }
    }

    protected void runFrame(){

        if (dashed){
            //if (player == GameData.Player.PLAYER1 && ListenerClass.p1Hit)
            //    dashed = false;
            //else if (player == GameData.Player.PLAYER2 && ListenerClass.p2Hit)
            //    dashed = false;
            if (player == GameData.Player.PLAYER1 && ListenerClass.p1Stage) {
                stunTimer = new FrameTimer(12);
                switchState(State.STUNNED);
                dashed = false;
            }
            else if (player == GameData.Player.PLAYER2 && ListenerClass.p2Stage) {
                stunTimer = new FrameTimer(12);
                switchState(State.STUNNED);
                dashed = false;
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