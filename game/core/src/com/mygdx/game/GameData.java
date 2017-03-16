package com.mygdx.game;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

/**
 * Created by 236040 on 2/16/2017.
 */
//database for values found in code: https://docs.google.com/a/allenisd.org/spreadsheets/d/1sMrRwi33BDu2Ee2jXgkjOK2JmH-mKdtxONRRPkdPZQc/edit?usp=sharing
public final class GameData { //this class has other static classes that are purely for storing data and nothing else. no objects of this class should be created.

    public enum Player {
        PLAYER1,
        PLAYER2
    }


    public static class AttackData{

        public static Vector2 getPosition(Body body, float bodyWidth, float bodyHeight, Character.Attack attackType, boolean direction){
            int d = (direction) ? 1 : -1;
            switch (attackType){
                case JAB:
                    return new Vector2(body.getPosition().x + (bodyWidth * d) + (.51f * d), body.getPosition().y);
                case S_TILT:
                    return new Vector2(body.getPosition().x + (bodyWidth * d) + (.51f * d), (body.getPosition().y + bodyHeight) - getDimension(Character.Attack.S_TILT).y);
                case D_TILT:
                case U_TILT:
                default:
                    return new Vector2(0, 0);
                //add more cases as we add more attacks

            }
        }

        public static Vector2 getDimension(Character.Attack attackType){
            switch (attackType){
                case JAB:
                    return new Vector2(.5f, .33f);
                case S_TILT:
                    return new Vector2(.75f, .25f);
                case D_TILT:
                case U_TILT:
                default:
                    return new Vector2(.5f, .5f);
            }
        }

        public static int getFrames(Character.Attack attackType){
            switch (attackType){
                case JAB:
                    return 5;
                case S_TILT:
                    return 15;
                case D_TILT:
                case U_TILT:
                default:
                    return 30;
            }
        }

        public static Vector2 getKnockback(Character.Attack attackType, boolean direction){
            int d = (direction) ? 1 : -1;
            switch (attackType){
                case JAB:
                    return new Vector2(20f * d, 20f);
                case S_TILT:
                    return new Vector2(50f * d, 35f);
                case D_TILT:
                    return new Vector2(15f * d, 50f);
                case U_TILT:
                    return new Vector2(10f, 55f);
                default:
                    return new Vector2(1f * d, 1f);
            }
        }



    }

}