package com.mygdx.game;

/**
 * created by ryan v on 1/29/2017
 **/
public interface CharacterStates { //states of character to be implemented in character classes

    void St_Walking();
    void St_Idle();
    void St_Air();
    void St_Attack();
    void St_Special();
    void St_Hitstun();
    void St_Stunned();

}

