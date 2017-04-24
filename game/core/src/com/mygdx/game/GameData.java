package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

/**
 * Created by 236040 on 2/16/2017.
 */
//database for values found in code: https://docs.google.com/a/allenisd.org/spreadsheets/d/1sMrRwi33BDu2Ee2jXgkjOK2JmH-mKdtxONRRPkdPZQc/edit?usp=sharing
public final class GameData { //this class has other static classes that are purely for storing data and nothing else. no objects of this class should be created.

    public enum Player { //enum for which side of the keyboard for control schemes and such
        PLAYER1,
        PLAYER2
    }

    //class that stores data for attacks such as power, knockback, frame data, etc
    public static class AttackData {

        public static Vector2 getPosition(Body body, float bodyWidth, float bodyHeight, Character.Attack attackType, boolean direction) { //returns the starting position of the hitbox
            int d = (direction) ? 1 : -1;
            switch (attackType) {
                case JAB:
                    return new Vector2(body.getPosition().x + (bodyWidth * d) + (1f * d), body.getPosition().y);
                case S_TILT:
                    return new Vector2(body.getPosition().x + (bodyWidth * d) + (1f * d), (body.getPosition().y + bodyHeight) - getDimension(Character.Attack.S_TILT).y);
                case D_TILT:
                    return new Vector2(body.getPosition().x + (bodyWidth * d) + (1f * d), (body.getPosition().y - bodyHeight) + getDimension(Character.Attack.S_TILT).y);
                case U_TILT:
                    return new Vector2(body.getPosition().x, body.getPosition().y + bodyHeight + getDimension(Character.Attack.D_TILT).y);
                default:
                    return new Vector2(0, 0);
                //add more cases as we add more attacks

            }
        }

        public static Vector2 getDimension(Character.Attack attackType) { //returns the size of the hitbox
            switch (attackType) {
                case JAB:
                    return new Vector2(.5f, .33f);
                case S_TILT:
                    return new Vector2(.75f, .25f);
                case D_TILT:
                    return new Vector2(.75f, .25f);
                case U_TILT:
                    return new Vector2(.75f, 1.5f);
                default:
                    return new Vector2(.5f, 1f);
            }
        }

        public static int getFrames(Character.Attack attackType) { //returns how many frames the hitbox is active
            switch (attackType) {
                case JAB:
                    return 20;
                case S_TILT:
                    return 30;
                case D_TILT:
                    return 30;
                case U_TILT:
                    return 30;
                default:
                    return 30;
            }
        }

        public static Vector2 getKnockback(Character.Attack attackType, boolean direction) { //returns base knockback of the hitbox
            int d = (direction) ? 1 : -1;
            switch (attackType) {
                case JAB:
                    return new Vector2(20f * d, 20f);
                case S_TILT:
                    return new Vector2(50f * d, 35f);
                case D_TILT:
                    return new Vector2(35f * d, 75f);
                case U_TILT:
                    return new Vector2(50f * d, 75f);
                default:
                    return new Vector2(10f * d, 10f);
            }
        }

        public static int getDamage(Character.Attack attackType) { //returns how much damage the hitbox deals
            switch (attackType) {
                case JAB:
                    return 3;
                case S_TILT:
                    return 7;
                case D_TILT:
                    return 5;
                case U_TILT:
                    return 6;
                default:
                    return 1;
            }
        }




    }

    //class that stores data about characters such as size, density, sprites, etc
    public static class CharacterData{
        public static Vector2 getBodySize(Character characterType){ //returns height and width of character
            if (characterType instanceof Knight)
                return new Vector2(2f, 3f * Window.yConst);
            else if (characterType instanceof Assassin)
                return new Vector2(1.75f, 2.75f * Window.yConst);
            else
                return new Vector2(1f, 1f * Window.yConst);
        }
        public static float getDensity(Character characterType){ //returns density / weight of character
            if (characterType instanceof Knight)
                return .08f;
            else if (characterType instanceof Assassin)
                return .1f;
            else
                return .5f;
        }
        public static Sprite getSprite(Character characterType){ //returns the idle sprite of character
            if (characterType instanceof Knight)
                return new Sprite(new Texture("core/assets/image/spr_knight_idle.png"));
            else if (characterType instanceof Assassin)
                return new Sprite(new Texture("core/assets/image/spr_assassin_idle.png"));
            else
                return new Sprite(new Texture("core/assets/image/spr_parent.png"));
        }
        public static int getMaxSpeed(Character characterType){ //returns the fastest the character can run
            if (characterType instanceof Knight)
                return 9;
            else if (characterType instanceof Assassin)
                return 11;
            else
                return 10;
        }
    }

}