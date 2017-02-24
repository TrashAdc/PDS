package com.mygdx.game;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

import java.util.Map;
import java.util.Random;

/**
 * Created by 236040 on 2/10/2017.
 */
public class Hitbox {

    private BodyDef hitboxDef;
    private Body hitbox;
    private PolygonShape boxShape;
    private Vector2 knockback;
    private String userData;

    public static Map<String, Vector2> hitboxMap;


    public Hitbox(float width, float height, float posx, float posy, Vector2 force){

        hitboxDef = new BodyDef();
        hitboxDef.type = BodyDef.BodyType.KinematicBody;
        hitboxDef.position.set(posx, posy);
        hitboxDef.fixedRotation = true;

        boxShape = new PolygonShape();
        boxShape.setAsBox(width, height);

        knockback = force;

        userData = generateUserData();

        hitboxMap.put(userData, knockback);


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

    public void destroyHitbox(){ //get rid of it
        hitboxMap.remove(userData);
        Window.bDestroy.addBody(hitbox);
    }

    private String generateUserData(){ //generates random key to be stored in the map
        String key;
        Random rand = new Random();

        do {
            key = "";
            for (int i = 0; i < 8; i++){
                key += (char)rand.nextInt(62) + 60;
            }
        } while (hitboxMap.containsKey(key));

        return key;
    }

    public void changeKnockback(Vector2 newKnockback){ //update knockback
        hitboxMap.put(userData, newKnockback);
    }

    public String getKey(){
        return userData;
    }
}
