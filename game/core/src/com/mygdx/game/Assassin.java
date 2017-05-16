package com.mygdx.game;


/**
 * created by ryan v on 4/23/2017
 **/
public class Assassin extends Character {
    private boolean dashed;
    private boolean critAdded;
    private Hitbox katanaHitbox, dashHitbox;
    private FrameTimer specialTimer;
    private float critChance;

    public Assassin(GameData.Player player){
        super(player);
        critChance = 0.0f;
        critAdded = false;
        dashed = false;


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
        switchState(State.IDLE);
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

        addCritChance(9);


        state_new = false;

        if (specialTimer.timerDone(false)){
            katanaHitbox.destroyHitbox();
            katanaHitbox = null;
            specialTimer = null;
            critAdded = false;
            switchState(State.IDLE);
            System.out.println(critChance);
        }




    }

    /*down special-
      the assassin goes invisible for a short period, preparing for an attack.
      after a short time, the assassin reappears and dashes in a horizontal direction.
     */
    private void ambush(){
        switchState(State.IDLE);
    }

    /*up special-
      the assassin grabs his blade and dashes diagonally in to the air, dealing damage and knockback
      to anything in his way.
     */
    private void airBlade(){
        if (state_new){
            specialTimer = new FrameTimer(10);
            dashHitbox = new Hitbox(bodyWidth * 1.5f, bodyHeight * 1.5f, body.getPosition().x, body.getPosition().y, calculateKnockback(7, 20f, 20f, true), 7, player);
            dashHitbox.spawnHitbox();
            state_new = false;
        }

        int d = (direction) ? 1 : -1;
        body.setLinearVelocity(200f * d, 200f);
        dashHitbox.getHitboxBody().setLinearVelocity(body.getLinearVelocity());

        addCritChance(7);

        specialTimer.incrementFrame();
        System.out.println(specialTimer.getCurrentFrame());



        if (specialTimer.timerDone(false)){
            body.setLinearVelocity(0f, 0f);
            dashHitbox.destroyHitbox();
            dashHitbox = null;
            specialTimer = null;
            critAdded = false;
            switchState(State.IDLE);
            dashed = true;
            //System.out.println(critChance);
            int thisintistokeepitfromtellingmeihavedupicatecodeiknowihavedupiclatecodebutidontcareatthispoint;
        }


    }

    private void addCritChance(float dmg){
        if (player == GameData.Player.PLAYER1 && ListenerClass.p1Hit && !critAdded) {
            critChance += dmg/4f;
            critAdded = true;
        }
        else if (player == GameData.Player.PLAYER2 && ListenerClass.p2Hit && !critAdded) {
            critChance += dmg/4f;
            critAdded = true;
        }
    }

    protected void runFrame(){
        System.out.println(dashed);
        if (dashed){
            //if (player == GameData.Player.PLAYER1 && ListenerClass.p1Hit)
            //    dashed = false;
            //else if (player == GameData.Player.PLAYER2 && ListenerClass.p2Hit)
            //    dashed = false;
            if (player == GameData.Player.PLAYER1 && ListenerClass.p1Stage)
                dashed = false;
            else if (player == GameData.Player.PLAYER2 && ListenerClass.p2Stage)
                dashed = false;
        }
    }




}