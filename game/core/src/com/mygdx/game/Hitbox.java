package com.mygdx.game;

import com.badlogic.gdx.physics.box2d.*;

/**
 * Created by 236040 on 2/10/2017.
 */
public class Hitbox {

    private BodyDef hitboxDef;
    private Body hitbox;
    private PolygonShape boxShape;
    private Character.Attack userData;

    public Hitbox(float width, float height, float posx, float posy, Character.Attack userData){

        hitboxDef = new BodyDef();
        hitboxDef.type = BodyDef.BodyType.KinematicBody;
        hitboxDef.position.set(posx, posy);
        hitboxDef.fixedRotation = true;

        boxShape = new PolygonShape();
        boxShape.setAsBox(width, height);

        this.userData = userData;

    }

    public Body getHitboxBody(){
        return hitbox;
    }

    public void spawnHitbox(){
        hitbox = Window.world.createBody(hitboxDef);
        hitbox.setUserData(userData);

        FixtureDef fixDef = new FixtureDef(); //the boxes fixture
        fixDef.shape = boxShape; //sets fixture to shape of the body
        fixDef.isSensor = true;

        hitbox.createFixture(fixDef);

    }

    public void destroyHitbox(){
        Window.world.destroyBody(hitbox);
    }

}
