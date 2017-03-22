package com.mygdx.game;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;


/**
 * Created by ryan v on 2/21/2017.
 */
public class ListenerClass implements ContactListener {

    private enum CollisionType{
        HIT_PLAYER,
        PLAYER_PLAYER,
        HIT_HIT,
        NONE
    }

    private Fixture f1, f2;
    private CollisionType cType;



    @Override
    public void beginContact(Contact contact) {

        f1 = contact.getFixtureA();
        f2 = contact.getFixtureB();

        cType = getCType(f1, f2);

        if (cType == CollisionType.HIT_PLAYER)
            characterHit();






    }

    @Override
    public void endContact(Contact contact) {
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
        else
            return CollisionType.HIT_PLAYER;

    }

    private void characterHit(){
        float vx = 0, vy = 0;
        Fixture hit = null;
        String hitUD = "", hitterUD = "";


        if (f1.getBody().getUserData().toString().substring(0, 1).equals("p")) {
            vx = Hitbox.hitboxMap.get(f1.getBody().getUserData()).x;
            vy = Hitbox.hitboxMap.get(f1.getBody().getUserData()).y;
            hit = f2;
            hitUD = f2.getBody().getUserData().toString().substring(6);
            hitterUD = f1.getBody().getUserData().toString().substring(1, 2);

        }
        else if (f2.getBody().getUserData().toString().substring(0, 1).equals("p")) {
            vx = Hitbox.hitboxMap.get(f2.getBody().getUserData()).x;
            vy = Hitbox.hitboxMap.get(f2.getBody().getUserData()).y;
            hit = f1;
            hitUD = f1.getBody().getUserData().toString().substring(6);
            hitterUD = f2.getBody().getUserData().toString().substring(1, 2);
        }




        if (hit != null) {
            //System.out.println(hitUD + " " + hitterUD);
            if (!hitUD.equals(hitterUD)){
                System.out.println(hitUD + " " + hitterUD);
                hit.getBody().applyLinearImpulse(vx, vy, hit.getBody().getPosition().x, hit.getBody().getPosition().y, false);
            }
        }

    }

}