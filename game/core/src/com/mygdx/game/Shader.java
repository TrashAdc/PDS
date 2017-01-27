package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;

/**
 * created by ryan v on 1/26/2017
 **/
public class Shader {
    private ShaderProgram shader;

    public Shader(){
        ShaderProgram.pedantic = false;
        shader = new ShaderProgram(Gdx.files.internal("core/assets/shaders/passthrough.vsh"), Gdx.files.internal("core/assets/shaders/passthrough.fsh"));
    }

    public ShaderProgram getShader(){
        return shader;
    }
}
