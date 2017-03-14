package com.mygdx.game;

import com.badlogic.gdx.physics.box2d.*;

/**
 * Created by ryan v on 2/21/2017.
 */
public class ListenerClass implements ContactListener {

    private Fixture f1, f2;



    @Override
    public void beginContact(Contact contact) {

        f1 = contact.getFixtureA();
        f2 = contact.getFixtureB();

        if (f2.getBody().getUserData() == "char_parent")
            System.out.println("helo my friend");



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

    private void filter(Contact contact){ //puts characters into f1, and hitboxes into f2
        String hit1 = (String) contact.getFixtureA().getBody().getUserData();
        String hit2 = (String) contact.getFixtureB().getBody().getUserData();

        if (hit1.substring(0, 1).equals("char") && (hit1.substring(0, 1).equals("char")))
            return;
        //add more stuff


    }

}
