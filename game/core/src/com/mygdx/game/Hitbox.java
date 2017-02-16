package com.mygdx.game;

import com.badlogic.gdx.physics.box2d.*;

/**
 * Created by 236040 on 2/10/2017.
 */
public class Hitbox {

    private BodyDef hitboxDef;
    private Body hitbox;
    private PolygonShape boxShape;

    public Hitbox(float width, float height, float posx, float posy){

        hitboxDef = new BodyDef();
        hitboxDef.type = BodyDef.BodyType.KinematicBody;
        hitboxDef.position.set(posx, posy);
        hitboxDef.fixedRotation = true;

        boxShape = new PolygonShape();
        boxShape.setAsBox(width, height);

    }

    public Body getHitboxBody(){
        return hitbox;
    }

    public void spawnHitbox(){
        hitbox = Window.world.createBody(hitboxDef);
        hitbox.createFixture(boxShape, 1.0f);


    }

    public void destroyHitbox(){
        Window.world.destroyBody(hitbox);
    }

}
