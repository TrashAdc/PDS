package com.mygdx.game;

import com.badlogic.gdx.physics.box2d.Body;

import java.util.ArrayList;
import java.util.List;

/**
 * created by ryan v on 2/15/2017
 **/
//destroys bodies easily when possible
public class BodyDestroyer {
    private List<Body> bodyList;

    public BodyDestroyer(){
        bodyList = new ArrayList<Body>(); //inits list of bodies
    }

    public void addBody(Body bodyToBeDestroyed){
        bodyList.add(bodyToBeDestroyed); //adds a body to list
    }

    public void destroyBodies(){
        if (bodyList.size() > 0) {
            for (int i = 0; i < bodyList.size(); i++) {
                Window.world.destroyBody(bodyList.get(i)); //destroys bodies if there are any
                bodyList.remove(i);
            }
        }
    }
}
