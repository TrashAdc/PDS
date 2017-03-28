package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
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

        stageWidth = 20f;
        stageHeight = 2f * Window.yConst;

        stageBodyInit(); //box2d stuff

        stageSprite = new Sprite(new Texture("core/assets/image/stageTemp.png"));
        stageSprite.setSize(stageWidth * 2f, stageHeight * 2f);
        stageSprite.setPosition(getStageCenter().x - stageWidth, getStageCenter().y - stageHeight);



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

    public Vector2 getStageCenter(){
        return new Vector2(stageBody.getPosition().x, stageBody.getPosition().y);
    }

    public Sprite getStageSprite(){
        return stageSprite;
    }


}