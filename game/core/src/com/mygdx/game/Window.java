package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;

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

    private SpriteBatch batch; //u need this to draw

    private Character dood; //this is an object in the game

    private Shader shader; //effects
    private float time; //time for shader

    private BitmapFont font; //text


    @Override
    public void create(){
        Box2D.init(); //inits box2d

        SIZE = new Dimension(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()); //gets size of window into condensed variables
        WORLD_SPEED = Gdx.graphics.getFramesPerSecond();
        yConst = (float)SIZE.width / (float)SIZE.height;


        world = new World(new Vector2(0, -100), true); //sets gravity of axis

        world.setContactListener(new ListenerClass()); //collision stuff

        bDestroy = new BodyDestroyer();

        debugRenderer = new Box2DDebugRenderer();

        camera = new OrthographicCamera(30, 30); //sets view of camera
        camera.position.set(camera.viewportWidth / 2f, camera.viewportHeight / 2f, 0); //position
        camera.update();

        batch = new SpriteBatch();

        Gdx.input.setInputProcessor(key);

        dood = new Character(); //creates character
        Stage testStage = new Stage(); //makes stage in world

        shader = new Shader(); //random shader

        time = 0.0f; //time for shaders


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
        //shader.getShader().setUniformf("time", time);
        // shader.getShader().setUniformf("resolution", SIZE.width, SIZE.height);
        //batch.setShader(shader.getShader()); //shaders!
        //batch.getProjectionMatrix();

        dood.getSprite().draw(batch); //draw the sprite



        batch.end();
        world.step(1/60f, 6, 2);

        bDestroy.destroyBodies();

    }

    @Override
    public void dispose() {
        shader.getShader().dispose();
        batch.dispose();

    }

}