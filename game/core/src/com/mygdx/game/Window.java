package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeType;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;

import java.awt.*;

public class Window extends ApplicationAdapter {

    public static Dimension SIZE; //size of window

    public static final ActiveKeys key = new ActiveKeys(); //key listener

    public static final World world = new World(new Vector2(0, -10), true);
    Box2DDebugRenderer debugRenderer = new Box2DDebugRenderer();

    private SpriteBatch batch; //u need this dont ask me why

    private Character dood; //this is an object in the game

    private Shader shader; //effects
    private float time; //time for shader

    private BitmapFont font; //text




    @Override
    public void create(){

        SIZE = new Dimension(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        dood = new Character();
        shader = new Shader(); //random shader
        batch = new SpriteBatch();
        font = new BitmapFont();
        Gdx.input.setInputProcessor(key);
        time = 0.0f;

    }

    @Override
    public void render() {
        time += .01f;
        Gdx.gl.glClearColor(.5f, 0f, 1f, 1f); //bg color
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();

        System.out.println(Window.SIZE.height);
        font.draw(batch, "current state: " + dood.stateToString(), 50, 50); //test for states

        //draw stuff here

        //System.out.println(shader.getShader().isCompiled());
        //shader.getShader().setUniformf("time", time);
        //shader.getShader().setUniformf("resolution", SIZE.width, SIZE.height);
        //batch.setShader(shader.getShader()); //shaders!
        //batch.getProjectionMatrix();

        dood.getSprite().draw(batch); //draw the sprite







        batch.end();
        world.step(1/60f, 6, 2);
    }

    @Override
    public void dispose() {
        shader.getShader().dispose();
        batch.dispose();
    }

}