package com.mygdx.game;

/**
 * created by ryan v on 2/13/2017
 **/
public class FpsHandler {

    private long curTime;
    private int sleepTime;

    private int frames;
    private int fps;
    private long frameTime;


    public FpsHandler(int goalFps){
        sleepTime = (int) Math.ceil(1000 / goalFps);
        curTime = 0;
        frames = 0;
        fps = 0;
        frameTime = 0;
    }

    public void startFrame(){
        curTime = System.currentTimeMillis();

        if (frameTime == 0)
            frameTime = System.currentTimeMillis();
        frames++;
    }
    public int endFrame(){
        long totalTime = System.currentTimeMillis() - curTime;
        if (sleepTime - totalTime > 0) {
            try {
                Thread.sleep((long) sleepTime - totalTime);
            }
            catch (Exception e){}
        }

        if (System.currentTimeMillis() - frameTime >= 1000) {
            fps = frames;
            frames = 0;
            frameTime = 0;
        }

        return fps;
    }

}
