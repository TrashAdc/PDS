package com.mygdx.game;

import java.math.BigInteger;
/**Created by ryan v on 1/4/2017.**/

public class Timer {

    public enum timeType { //enums for type of time
        NANOSECONDS,
        MILLISECONDS,
        SECONDS,
        MINUTES,
        HOURS,
    }

    private BigInteger startTime;
    private BigInteger time;
    private boolean started;

    public Timer(timeType type, int timeAmount){ //constructor
        started = false;

        time = BigInteger.valueOf(timeAmount);
        switch (type) {                             //this assigns how much time in nanoseconds will have to pass
            case NANOSECONDS:                       //in order to make the timerFinish() method true
                time = BigInteger.valueOf(timeAmount);
                break;
            case MILLISECONDS:
                time = time.multiply(BigInteger.valueOf(1000000));
                break;
            case SECONDS:
                time = time.multiply(BigInteger.valueOf(1000000));
                time = time.multiply(BigInteger.valueOf(1000));
                break;
            case MINUTES:
                time = time.multiply(BigInteger.valueOf(1000000));
                time = time.multiply(BigInteger.valueOf(1000));
                time = time.multiply(BigInteger.valueOf(60));
                break;
            case HOURS:
                time = time.multiply(BigInteger.valueOf(1000000));
                time = time.multiply(BigInteger.valueOf(1000));
                time = time.multiply(BigInteger.valueOf(60));
                time = time.multiply(BigInteger.valueOf(60));
                break;
            default:
                time = BigInteger.valueOf(0);
                break;
        }
    }

    public void initTimer(boolean reset){ //this starts the timer. only use this when you are ready to start
        if (!started)
            startTime = BigInteger.valueOf(System.nanoTime());
        else if (reset && started)
            startTime = BigInteger.valueOf(System.nanoTime());

        started = true;
    }


    public Boolean timerFinish(){
        if (!started)       //if the timer has not started yet
            return false;   //return false

        BigInteger cur = BigInteger.valueOf(System.nanoTime()); //this method checks to see whether the given time has passed.
        cur = cur.subtract(startTime);                          //you must be checking this method constantly for the most
        int finished = cur.compareTo(time);                     //accurate results.

        if (finished == 0 || finished == 1)
            return true;
        else
            return false;
    }
}