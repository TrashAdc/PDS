package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.physics.box2d.*;

import java.util.*;


/**
 * Created by ryan v on 2/21/2017.
 */
public class ListenerClass implements ContactListener {

    private enum CollisionType{
        HIT_PLAYER,
        PLAYER_PLAYER,
        HIT_HIT,
        PLAYER_KO,
        STAGE,
        NONE
    }

    private Fixture f1, f2;
    private CollisionType cType;
    public static boolean p1Hit, p2Hit, p1Stage, p2Stage;

    public static List<Fixture> moveList;

    public ListenerClass(){
        moveList = new ArrayList<Fixture>();
        p1Hit = false; //if it has been hit
        p2Hit = false;
        p1Stage = false;
        p2Stage = false;
    }

    @Override
    public void beginContact(Contact contact) {

        f1 = contact.getFixtureA();
        f2 = contact.getFixtureB();
        //System.out.println(f1.getBody().getUserData().toString() + "-> " + f2.getBody().getUserData().toString());

        //if (f1.getBody().getUserData().toString().contains("proj_") || f2.getBody().getUserData().toString().contains("proj_"))

        cType = getCType(f1, f2);

        GameData.Player p;
        if (cType == CollisionType.HIT_PLAYER) {
            p = characterHit();
            if (p == GameData.Player.PLAYER1 && p != null)
                p1Hit = true;
            else if (p == GameData.Player.PLAYER2 && p != null)
                p2Hit = true;
            //System.out.println("BAM");
            //System.out.println("p1 hit - " + p1Hit + " p2 hit - " + p2Hit);
        }
        else if (cType == CollisionType.STAGE){
            if (f1.getBody().getUserData() == GameData.Player.PLAYER1 || f2.getBody().getUserData() == GameData.Player.PLAYER1)
                p1Stage = true;
            else if (f1.getBody().getUserData() == GameData.Player.PLAYER2 || f2.getBody().getUserData() == GameData.Player.PLAYER2)
                p2Stage = true;
        }
        else if (cType == CollisionType.PLAYER_KO)
            characterDeath();





    }

    @Override
    public void endContact(Contact contact) {
        p1Hit = false;
        p2Hit = false;
        p1Stage = false;
        p2Stage = false;
    }

    @Override
    public void preSolve (Contact contact, Manifold oldManifold){

    }

    @Override
    public void postSolve (Contact contact, ContactImpulse impulse){

    }

    private boolean easyCheck(String userData1, String userData2){
        if ((f1.getBody().getUserData().equals(userData1) && f2.getBody().getUserData().equals(userData2)) ||
                (f1.getBody().getUserData().equals(userData2) && f2.getBody().getUserData().equals(userData1)))
            return true;
        else
            return false;
    }
    private boolean easyCheck(String userData1, Character.Attack userData2){ //overloaded functions
        if ((f1.getBody().getUserData().equals(userData1) && f2.getBody().getUserData().equals(userData2)) ||
                (f1.getBody().getUserData().equals(userData2) && f2.getBody().getUserData().equals(userData1)))
            return true;
        else
            return false;
    }
    private boolean easyCheck(Character.Attack userData1, Character.Attack userData2){ //overloaded functions
        if ((f1.getBody().getUserData().equals(userData1) && f2.getBody().getUserData().equals(userData2)) ||
                (f1.getBody().getUserData().equals(userData2) && f2.getBody().getUserData().equals(userData1)))
            return true;
        else
            return false;
    }

    private CollisionType getCType(Fixture fix1, Fixture fix2){ //checks to see what types of boxes are colliding

        if (fix1.getBody().getUserData().toString().substring(0, 1).equals("p") && fix2.getBody().getUserData().toString().substring(0, 1).equals("p"))
            return CollisionType.HIT_HIT;
        else if ((fix1.getBody().getUserData() == GameData.Player.PLAYER1 || fix1.getBody().getUserData() == GameData.Player.PLAYER2) &&
                (fix2.getBody().getUserData() == GameData.Player.PLAYER1 || fix2.getBody().getUserData() == GameData.Player.PLAYER2))
            return CollisionType.PLAYER_PLAYER;
        else if (fix1.getBody().getUserData().toString().substring(0, 2).equals("KO") || fix2.getBody().getUserData().toString().substring(0, 2).equals("KO"))
            return CollisionType.PLAYER_KO;
        else if (fix1.getBody().getUserData().toString().equals("stage") || fix2.getBody().getUserData().toString().equals("stage"))
            return CollisionType.STAGE;
        else
            return CollisionType.HIT_PLAYER;





    }

    private GameData.Player characterHit(){
        float vx = 0, vy = 0;
        int damage = 0;
        Fixture hit = null;
        String hitUD = "", hitterUD = "";
        //hitUD is the body being hit
        //hitterUD is the hitbox dealing damage

        if (f1.getBody().getUserData().toString().substring(0, 1).equals("p")) {
            System.out.println("1yes");
            if (Hitbox.hitboxMap.containsKey(f1.getBody().getUserData())) {
                vx = Hitbox.hitboxMap.get(f1.getBody().getUserData()).x;
                vy = Hitbox.hitboxMap.get(f1.getBody().getUserData()).y;
                damage = Hitbox.damageMap.get(f1.getBody().getUserData());
                hit = f2;
                hitUD = f2.getBody().getUserData().toString().substring(6);
                hitterUD = f1.getBody().getUserData().toString().substring(1, 2);
            }

        }
        else if (f2.getBody().getUserData().toString().substring(0, 1).equals("p")) {
            //System.out.println("2yes");
            //System.out.println(f2.getBody().getUserData().toString());
           // System.out.println(Hitbox.hitboxMap.size());
            if (Hitbox.hitboxMap.containsKey(f2.getBody().getUserData())) {
                vx = Hitbox.hitboxMap.get(f2.getBody().getUserData()).x;
                vy = Hitbox.hitboxMap.get(f2.getBody().getUserData()).y;
                damage = Hitbox.damageMap.get(f2.getBody().getUserData());
                hit = f1;
                hitUD = f1.getBody().getUserData().toString().substring(6);
                hitterUD = f2.getBody().getUserData().toString().substring(1, 2);
            }
        }




        if (hit != null) {
            //System.out.println(hitUD + " " + hitterUD);
            if (!hitUD.equals(hitterUD)){
                //System.out.println("p" + hitterUD + " HIT " + "p" + hitUD);
                hit.getBody().setLinearVelocity(0f, .1f);
                float kbm = Window.getCharacter((GameData.Player) hit.getBody().getUserData()).getKnockbackMultiplier();
                hit.getBody().applyLinearImpulse(vx * kbm, vy * kbm, hit.getBody().getPosition().x, hit.getBody().getPosition().y, false); //knock the character back
                Window.scoreData.addDamage((GameData.Player) hit.getBody().getUserData(), damage); //deal damage
                Window.characterHitstun((GameData.Player) hit.getBody().getUserData());
                //System.out.println(hit.getBody().getUserData() + "damage: " + Window.scoreData.getDamage((GameData.Player) hit.getBody().getUserData()));
            }
            return (hit.getBody().getUserData() == GameData.Player.PLAYER1) ? GameData.Player.PLAYER2 : GameData.Player.PLAYER1;
        }
        return null;

    }

    private void characterDeath(){
        if (f1.getBody().getUserData().toString().substring(0, 1).equals("P")) {
            moveList.add(f1);
        }
        else {
            moveList.add(f2);
        }
    }

}