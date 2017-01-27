package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.awt.*;

public class Window extends ApplicationAdapter {

    public static Dimension SIZE; //size of window
    public static final ActiveKeys key = new ActiveKeys(); //key listener
    SpriteBatch batch; //u need this dont ask me why
    private Character dood; //this is an object in the game
    private Shader shader;
    private float y;


    @Override
    public void create(){

        SIZE = new Dimension(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        dood = new Character();
        shader = new Shader(); //random shader
        batch = new SpriteBatch();
        Gdx.input.setInputProcessor(key);
        y = 0.0f;

    }

    @Override
    public void render() {
        y += .01f;
        Gdx.gl.glClearColor(.5f, 0f, 1f, 1f); //bg color
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();

        //draw stuff here
        //System.out.println(shader.getShader().isCompiled());
        shader.getShader().setUniformf("time", y);
        shader.getShader().setUniformf("resolution", SIZE.width, SIZE.height);
        batch.setShader(shader.getShader()); //shaders!
        batch.getProjectionMatrix();

        dood.getSprite().draw(batch); //draw the sprite

        batch.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
    }

}