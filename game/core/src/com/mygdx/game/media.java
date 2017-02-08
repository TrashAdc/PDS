package com.mygdx.game;
import com.badlogic.gdx.Audio;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
/**
 * Created by 253539 on 2/6/2017.
 */
public class media {

    public Music plskillme() {
        Music PLS = Gdx.audio.newMusic(Gdx.files.internal("assets/EndlessStarlight_FULL.mp3"));
plskillme().play();
        plskillme().dispose();
        return PLS;
    }
   
}
