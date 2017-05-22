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
    private Sprite titleSprite;
    private Sprite contSprite;
    private Sprite memeSprite;
    private Sprite instructSprite;
    private Sprite thingSprite;
    private Sprite winSprite1, winSprite2;
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

        stageSprite = new Sprite(new Texture("core/assets/image/stagetemp.png"));
        titleSprite = new Sprite(new Texture("core/assets/image/title.png"));
        contSprite= new Sprite (new Texture("core/assets/image/xdxx.png"));
        memeSprite= new Sprite (new Texture("core/assets/image/Wizard.png"));
        instructSprite= new Sprite(new Texture("core/assets/image/LL.png"));
        thingSprite= new Sprite(new Texture("core/assets/image/meh.png"));
        winSprite1 = new Sprite(new Texture("core/assets/image/p1Win.png"));
        winSprite2 = new Sprite(new Texture("core/assets/image/p2Win.png"));
        titleSprite.setPosition(Window.camera.viewportWidth / 14, Window.camera.viewportHeight / 12);
        titleSprite.setSize(50f, 50f);
        contSprite.setPosition(Window.camera.viewportWidth/4f, Window.camera.viewportHeight/48);
        contSprite.setSize(30f,30f);
        memeSprite.setPosition(Window.camera.viewportWidth/14,Window.camera.viewportHeight/12);
        memeSprite.setSize(50f,50f);
        instructSprite.setPosition(Window.camera.viewportWidth/14, Window.camera.viewportHeight/12);
        instructSprite.setSize(50f,50f);
        thingSprite.setPosition(0, 43);
        thingSprite.setSize(25f,25f);
        stageSprite.setSize(stageWidth * 2f, stageHeight * 2f);
        stageSprite.setPosition(getStageCenter().x - stageWidth, getStageCenter().y - stageHeight);
        winSprite1.setSize(25f, 50f);
        winSprite2.setSize(25f, 50f);

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
        fixtureDef.filter.categoryBits = 0x0004;
        fixtureDef.filter.maskBits = 0x0002;

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
        stageDef.type = BodyDef.BodyType.StaticBody;
        stageDef.position.set(Window.camera.viewportWidth / 2f, Window.camera.viewportHeight / 7f);

        stageBody = Window.world.createBody(stageDef);
        stageDef.fixedRotation = true;

        PolygonShape stageShape = new PolygonShape();
        stageShape.setAsBox(stageWidth, stageHeight);

        stageBody.createFixture(stageShape, 1.0f);
        stageBody = Window.world.createBody(stageDef);
        stageBody.setUserData("stage");

        FixtureDef fd = new FixtureDef();
        fd.shape = stageShape;
        fd.filter.categoryBits = 0x0004;
        fd.filter.maskBits = 0x0002;

        stageBody.createFixture(fd);


    }

    public Vector2 getStageCenter(){
        return new Vector2(stageBody.getPosition().x, stageBody.getPosition().y);
    }

    public Sprite getStageSprite(){
        return stageSprite;
    }

    public Sprite getStageSprite2() {
        return titleSprite;
    }

    public Sprite getStageSprite3(){return contSprite;}

    public Sprite getStageSprite4(){return memeSprite;}

    public Sprite getStageSprite5(){return instructSprite;}

    public Sprite getStageSprite6(){return thingSprite;}

    public Sprite getWinSprite1(){ return winSprite1; }
    public Sprite getWinSprite2(){ return winSprite2; }
}