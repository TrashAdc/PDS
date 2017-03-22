package com.mygdx.game;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;

import java.util.Random;

/**
 * Created by 236040 on 3/16/2017.
 */
public class DummyBox {
    public DummyBox(){
        BodyDef bd = new BodyDef();
        bd.type = BodyDef.BodyType.DynamicBody;
        bd.position.set(Window.camera.viewportWidth / 2, Window.camera.viewportHeight);
        bd.fixedRotation = true;

        Body b = Window.world.createBody(bd);
        b.setUserData("BigDumDum");

        PolygonShape bs = new PolygonShape();
        bs.setAsBox(2f, 2f * Window.yConst);

        FixtureDef fixDef = new FixtureDef(); //the boxes fixture
        fixDef.shape = bs; //sets fixture to shape of the body
        fixDef.density = 0.1f;
        fixDef.restitution = 0f; //bounciness
        fixDef.friction = 0.5f;

        b.createFixture(fixDef);
    }
}
