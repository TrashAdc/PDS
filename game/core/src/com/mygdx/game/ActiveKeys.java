package com.mygdx.game;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;

/**
 * created by ryan v on 1/17/2017
 **/
//this class is basically just a key listener :P

public class ActiveKeys implements InputProcessor {
    public boolean right = false; //these variables turn true when the corresponding key is pressed
    public boolean left = false; //more keys other than left and right can be added by just copying what ive done but with other keys its really that simple
    public boolean up = false;
    public boolean down = false;
    public boolean numpad1 = false;
    public boolean numpad3 = false;

    @Override
    public boolean keyDown(int keycode) {
        switch(keycode){
            case Input.Keys.LEFT:
                left = true;
                break;
            case Input.Keys.RIGHT:
                right = true;
                break;
            case Input.Keys.UP:
                up = true;
                break;
            case Input.Keys.DOWN:
                down = true;
                break;
            case Input.Keys.NUMPAD_1:
                numpad1 = true;
                break;
            case Input.Keys.NUMPAD_3:
                numpad3 = true;
                break;
        }
        return true;
    }

    @Override
    public boolean keyUp(int keycode) {
        switch(keycode){
            case Input.Keys.LEFT:
                left = false;
                break;
            case Input.Keys.RIGHT:
                right = false;
                break;
            case Input.Keys.UP:
                up = false;
                break;
            case Input.Keys.DOWN:
                down = false;
                break;
            case Input.Keys.NUMPAD_1:
                numpad1 = false;
                break;
            case Input.Keys.NUMPAD_3:
                numpad3 = false;
                break;
        }
        return true;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}