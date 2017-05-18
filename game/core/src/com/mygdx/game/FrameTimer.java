package com.mygdx.game;

/**
 * Created by ryan v on 2/14/2017.
 */
public class FrameTimer {

    private int currentFrame;
    private int framesToReach;

    public FrameTimer(int frames){
        currentFrame = 0;
        framesToReach = frames;
    }

    public void changeFrames(int newFrameTime){ //if you want to change how long the timer needs to reach
        framesToReach = newFrameTime;
    }
    public void resetTimer(){ //if you want to set the progress to 0
        currentFrame = 0;
    }

    public void incrementFrame(){ //run this every single frame
        currentFrame++;
    }

    public int getCurrentFrame() { return currentFrame; }

    public boolean timerDone(boolean reset){ //returns true if the current frame is past the frame to reach
        boolean lol = reset; //i messed up on this so now it literally means nothing because its useless anyways
        return (currentFrame >= framesToReach) ? true : false;
    }


}