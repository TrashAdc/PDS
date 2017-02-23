package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.*;

/**
 * created by ryan v on 2/5/2017
 **/
public class Stage { //parent stage class

    private Sprite stageSprite; //sprite of stage
    private BodyDef stageDef; //stage body def
    private Body stageBody; //stage body
    private float stageWidth, stageHeight; //stage w, h

    public Stage(){

        stageWidth = 10f;
        stageHeight = 2f * Window.yConst;

        stageBodyInit(); //box2d stuff


    }

    private void stageBodyInit(){ //this is a static body

        stageDef = new BodyDef();
        stageDef.position.set(Window.camera.viewportWidth / 2f, Window.camera.viewportHeight / 7f);

        stageBody = Window.world.createBody(stageDef);

        PolygonShape stageShape = new PolygonShape();
        stageShape.setAsBox(stageWidth, stageHeight);


        stageBody.createFixture(stageShape, 1.0f);
        stageBody.setUserData("stage");


    }


}