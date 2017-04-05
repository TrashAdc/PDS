package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;

/**
 * created by ryan v on 1/26/2017
 **/
public class Shader {
    private ShaderProgram shader;

    public Shader(String path1, String path2){
        ShaderProgram.pedantic = false;
        shader = new ShaderProgram(Gdx.files.internal(path1), Gdx.files.internal(path2));
    }

    public ShaderProgram getShader(){
        return shader;
    }
}
