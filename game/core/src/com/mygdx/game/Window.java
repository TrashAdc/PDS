package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;

import javax.xml.bind.Marshaller;
import java.awt.*;

public class Window extends ApplicationAdapter {

    public static Dimension SIZE; //size of window
    public static int WORLD_SPEED;
    public static float yConst; //this is to fix the clash between resolution and world height
    //whenever calculating height, multiply by this to get an accurate visual
    //width = 2f, height = 2f;                  <------------- incorrect
    //width = 2f, height = 2f * Window.yConst;  <------------- correct

    public static final ActiveKeys key = new ActiveKeys(); //key listener

    public static World world; //physics world

    public static BodyDestroyer bDestroy;

    Box2DDebugRenderer debugRenderer; //lets us see hitboxes for debugging

    public static OrthographicCamera camera; //camera for sizing things down i guess

    private SpriteBatch batch, textBatch; //u need this to draw

    private Stage testStage;

    private Character dood, dood2; //this is an object in the game

    public static Score_ scoreData;

    private Shader rainbowShader; //effects
    private Shader passthroughShader;
    private float time; //time for shader

    private TextWriter textWriter;



    @Override
    public void create(){
        Box2D.init(); //inits box2d

        SIZE = new Dimension(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()); //gets size of window into condensed variables
        WORLD_SPEED = Gdx.graphics.getFramesPerSecond();
        yConst = (float)SIZE.width / (float)SIZE.height;

        world = new World(new Vector2(0, -60), true); //sets gravity of axis

        world.setContactListener(new ListenerClass()); //collision stuff

        bDestroy = new BodyDestroyer();

        debugRenderer = new Box2DDebugRenderer();

        camera = new OrthographicCamera(60, 60); //sets view of camera
        camera.position.set(camera.viewportWidth / 2f, camera.viewportHeight / 2f, 0); //position
        camera.update();

        batch = new SpriteBatch();
        textBatch = new SpriteBatch(); //spritebatch for drawing text

        Gdx.input.setInputProcessor(key);

        dood = new Character(GameData.Player.PLAYER1); //creates character
        dood2 = new Character(GameData.Player.PLAYER2); //creates character
        testStage = new Stage(); //makes stage in world

        scoreData = new Score_(3); //sets initial score (stocks and damage)

        rainbowShader = new Shader("core/assets/shaders/passthrough.vsh", "core/assets/shaders/passthrough.fsh"); //random shader
        passthroughShader = new Shader("core/assets/shaders/normal.vsh", "core/assets/shaders/normal.fsh");

        time = 0.0f; //time for shaders

        textWriter = new TextWriter("core/assets/fonts/coders_crux.ttf", "hello", 96, Color.RED);
        textWriter.updateLayout(TextWriter.TextLayout.CENTER);

        //DummyBox d = new DummyBox();


    }

    @Override
    public void render() {

        camera.update(); //updates camera
        batch.setProjectionMatrix(camera.combined); //makes sure we're drawing on the scale of the camera

        time += .01f; //increments time for shader
        Gdx.gl.glClearColor(.5f, 0f, 1f, 1f); //bg color
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        debugRenderer.render(world, camera.combined);
        batch.begin();

        
        //draw stuff here

        //System.out.println(shader.getShader().isCompiled());
        //rainbowShader.getShader().setUniformf("time", time);
        //rainbowShader.getShader().setUniformf("resolution", SIZE.width, SIZE.height);
        //batch.setShader(passthroughShader.getShader()); //shaders!
        //batch.getProjectionMatrix();
        testStage.getStageSprite().draw(batch);


        dood.getSprite().draw(batch); //draw the sprite
        dood2.getSprite().draw(batch);


        batch.end();

        textBatch.begin(); //draw text here
        textWriter.drawText(textBatch, scoreData.scoreConverter(scoreData.getDamage(GameData.Player.PLAYER1)) + "%", (SIZE.width / 10) * 3, SIZE.height / 6);
        textWriter.drawText(textBatch, scoreData.scoreConverter(scoreData.getDamage(GameData.Player.PLAYER2)) + "%", (SIZE.width / 10) * 7, SIZE.height / 6);
        textBatch.end();

        world.step(1/60f, 6, 2); //step the physics world

        bDestroy.destroyBodies(); //destroy any bodies that should be despawned

        if (!ListenerClass.moveList.isEmpty()){ //move any characters that need to be repositioned
            for (int i = 0; i < ListenerClass.moveList.size(); i++) {
                ListenerClass.moveList.get(i).getBody().setLinearVelocity(0, 10f);
                ListenerClass.moveList.get(i).getBody().setTransform(camera.viewportWidth / 2, camera.viewportHeight / 2 + 15f, 0);
                ListenerClass.moveList.remove(i);
            }
        }

    }

    @Override
    public void dispose() {
        rainbowShader.getShader().dispose();
        passthroughShader.getShader().dispose();
        batch.dispose();

    }

}