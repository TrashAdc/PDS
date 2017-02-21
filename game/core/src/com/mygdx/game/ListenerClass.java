package com.mygdx.game;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;

/**
 * Created by 236040 on 2/21/2017.
 */
public class ListenerClass implements ContactListener {

    @Override
    public void beginContact(Contact contact) {
        Character c = contact.getFixtureA().getUserData(); //http://stackoverflow.com/questions/30464421/how-to-get-a-variable-from-another-class-without-calling-a-constructor

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

}
