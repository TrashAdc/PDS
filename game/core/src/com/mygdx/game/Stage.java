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
        //WindowView();


        putBlastZone(-30, Window.camera.viewportHeight / 2, 5f, Window.camera.viewportHeight, "KOLeft");
        putBlastZone(90, Window.camera.viewportHeight / 2, 5f, Window.camera.viewportHeight, "KORight");
        putBlastZone(Window.camera.viewportHeight / 2, Window.camera.viewportHeight * 1.5f, Window.camera.viewportWidth, 5f, "KOUp");
        putBlastZone(Window.camera.viewportHeight / 2, -Window.camera.viewportHeight * .6f, Window.camera.viewportWidth, 5f, "KOUp");


        stageWidth = 20f;
        stageHeight = 2f * Window.yConst;

        stageBodyInit(); //box2d stuff

        stageSprite = new Sprite(new Texture("core/assets/image/stageTemp.png"));
        stageSprite.setSize(stageWidth * 2f, stageHeight * 2f);
        stageSprite.setPosition(getStageCenter().x - stageWidth, getStageCenter().y - stageHeight);



    }

    private void putBlastZone(float posx, float posy, float width, float height, String userdata){
        BodyDef bd = new BodyDef();
        bd.type = BodyDef.BodyType.StaticBody;
        bd.position.set(posx, posy);
        bd.fixedRotation = true;

        PolygonShape bShape = new PolygonShape();
        bShape.setAsBox(width, height);

        Body bod = Window.world.createBody(bd);
        bod.setUserData(userdata);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = bShape;
        fixtureDef.isSensor = true;

        bod.createFixture(fixtureDef);
    }

    private void WindowView(){
        BodyDef bd = new BodyDef();
        bd.type = BodyDef.BodyType.StaticBody;
        bd.position.set(Window.camera.viewportWidth / 2, Window.camera.viewportHeight / 4);
        bd.fixedRotation = true;

        PolygonShape bShape = new PolygonShape();
        bShape.setAsBox(30, 30);

        Body bod = Window.world.createBody(bd);
        bod.setUserData("view");

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = bShape;
        fixtureDef.isSensor = true;

        bod.createFixture(fixtureDef);
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