package com.mygdx.game;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;

/**
 * Created by 250065 on 3/14/2017.
 */
public class LedgeBox {
    private BodyDef ledgeboxDef;
    private Body ledgebox;
    private PolygonShape boxShape;
    private Vector2 knockback;
    private String userData;
    private String userDataPrefix;
    private float LWidth, LHeight;



    public LedgeBox(float LWidth,float LHeight,float posx2,float posy2,float posx3,float posy3, Vector2 force, GameData.Player player){
        LWidth= 1f;
        LHeight = 1f;
        ledgeboxDef= new BodyDef();
        ledgeboxDef.type= BodyDef.BodyType.KinematicBody;
        ledgeboxDef.position.set(posx2,posy2);
        ledgeboxDef.position.set(posx3,posy3);

        boxShape=new PolygonShape();
        boxShape.setAsBox(LWidth,LHeight);


        userDataPrefix = (player == GameData.Player.PLAYER1) ? "p1" : "p2";

    }
    public Body getLedgebox(){return ledgebox;};

    public void spawnLedgebox(){
        ledgebox= Window.world.createBody(ledgeboxDef);

        FixtureDef Lfixdef= new FixtureDef();
        Lfixdef.shape=boxShape;
        Lfixdef.isSensor=true;

        ledgebox.createFixture(Lfixdef);



    }
    public void destroyLedgebox(){
        Window.bDestroy.addBody(ledgebox);

    }


}
