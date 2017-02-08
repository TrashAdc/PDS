package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.*;

/**
 * created by ryan v on 2/5/2017
 **/
public class Stage {

    private Sprite stageSprite;
    private BodyDef stageDef;
    private Body stageBody;
    private float stageWidth, stageHeight;

    public Stage(){

        stageWidth = 10f;
        stageHeight = 2f * Window.yConst;

        stageBodyInit();


    }

    private void stageBodyInit(){

        stageDef = new BodyDef();
        stageDef.position.set(Window.camera.viewportWidth / 2f, Window.camera.viewportHeight / 7f);

        stageBody = Window.world.createBody(stageDef);

        PolygonShape stageShape = new PolygonShape();
        stageShape.setAsBox(stageWidth, stageHeight);


        stageBody.createFixture(stageShape, 1.0f);


    }


}