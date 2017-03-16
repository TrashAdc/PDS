package com.mygdx.game;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

import java.util.HashMap;
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
    private String userDataPrefix;

    public static Map<String, Vector2> hitboxMap;


    public Hitbox(float width, float height, float posx, float posy, Vector2 force, GameData.Player player){

        hitboxDef = new BodyDef();
        hitboxDef.type = BodyDef.BodyType.KinematicBody;
        hitboxDef.position.set(posx, posy);
        hitboxDef.fixedRotation = true;

        boxShape = new PolygonShape();
        boxShape.setAsBox(width, height);

        knockback = force;

        userDataPrefix = (player == GameData.Player.PLAYER1) ? "p1" : "p2";

        hitboxMap = new HashMap();
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
            key = userDataPrefix;
            for (int i = 0; i < 6; i++){

                char l = (char)(rand.nextInt(62) + 60);
                key += l;
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