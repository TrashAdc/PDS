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

    public enum Player {
        PLAYER1,
        PLAYER2
    }

    public static class AttackData{ //accessor class for attack data
        public static Vector2 getPosition(Body body, float bodyWidth, float bodyHeight, Character.Attack attackType, boolean direction, Character charType){
            if (charType instanceof Knight)
                return KnightAttackData.getPosition(body, bodyWidth, bodyHeight, attackType, direction);
            if (charType instanceof Assassin)
                return AssassinAttackData.getPosition(body, bodyWidth, bodyHeight, attackType, direction);
            else
                return ParentAttackData.getPosition(body, bodyWidth, bodyHeight, attackType, direction);
        }
        public static Vector2 getDimension(Character.Attack attackType, Character charType){
            if (charType instanceof Knight)
                return KnightAttackData.getDimension(attackType);
            if (charType instanceof Assassin)
                return AssassinAttackData.getDimension(attackType);
            else
                return ParentAttackData.getDimension(attackType);
        }
        public static int getFrames(Character.Attack attackType, Character charType){
            if (charType instanceof Knight)
                return KnightAttackData.getFrames(attackType);
            if (charType instanceof Assassin)
                return AssassinAttackData.getFrames(attackType);
            else
                return ParentAttackData.getFrames(attackType);
        }
        public static Vector2 getKnockback(Character.Attack attackType, boolean direction, Character charType){
            if (charType instanceof Knight)
                return KnightAttackData.getKnockback(attackType, direction);
            if (charType instanceof Assassin)
                return AssassinAttackData.getKnockback(attackType, direction);
            else
                return ParentAttackData.getKnockback(attackType, direction);
        }
        public static int getDamage(Character.Attack attackType, Character charType){
            if (charType instanceof Knight)
                return KnightAttackData.getDamage(attackType);
            if (charType instanceof Assassin)
                return AssassinAttackData.getDamage(attackType);
            else
                return ParentAttackData.getDamage(attackType);
        }
    }


    //class that stores data for attacks such as power, knockback, frame data, etc
    //for parent class
    private static class ParentAttackData {

        public static Vector2 getPosition(Body body, float bodyWidth, float bodyHeight, Character.Attack attackType, boolean direction) {
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

        public static Vector2 getDimension(Character.Attack attackType) {
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

        public static int getFrames(Character.Attack attackType) {
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

        public static Vector2 getKnockback(Character.Attack attackType, boolean direction) {
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

        public static int getDamage(Character.Attack attackType) {
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

    //class that stores data for attacks such as power, knockback, frame data, etc
    //for knight
    private static class AssassinAttackData{
        public static Vector2 getPosition(Body body, float bodyWidth, float bodyHeight, Character.Attack attackType, boolean direction) {
            int d = (direction) ? 1 : -1;
            switch (attackType) {
                case JAB:
                    return new Vector2(body.getPosition().x + (bodyWidth * d) + (.5f * d), body.getPosition().y);
                case S_TILT:
                    return new Vector2(body.getPosition().x + (bodyWidth * d) + (getDimension(Character.Attack.S_TILT).x * d), body.getPosition().y - getDimension(Character.Attack.S_TILT).y);
                case D_TILT:
                    return new Vector2(body.getPosition().x + (bodyWidth * d) + (getDimension(Character.Attack.S_TILT).x * d), (body.getPosition().y - bodyHeight) + getDimension(Character.Attack.S_TILT).y);
                case U_TILT:
                    return new Vector2(body.getPosition().x, body.getPosition().y + bodyHeight * 1.5f + getDimension(Character.Attack.D_TILT).y);
                default:
                    return new Vector2(0, 0);
                //add more cases as we add more attacks

            }
        }

        public static Vector2 getDimension(Character.Attack attackType) {
            switch (attackType) {
                case JAB:
                    return new Vector2(1f, .5f);
                case S_TILT:
                    return new Vector2(1.5f, .5f);
                case D_TILT:
                    return new Vector2(1.25f, .5f);
                case U_TILT:
                    return new Vector2(.75f, 2.5f);
                default:
                    return new Vector2(.5f, 1f);
            }
        }

        public static int getFrames(Character.Attack attackType) {
            switch (attackType) {
                case JAB:
                    return 30;
                case S_TILT:
                    return 45;
                case D_TILT:
                    return 30;
                case U_TILT:
                    return 40;
                default:
                    return 30;
            }
        }

        public static Vector2 getKnockback(Character.Attack attackType, boolean direction) {
            int d = (direction) ? 1 : -1;
            switch (attackType) {
                case JAB:
                    return new Vector2(20f * d, 20f);
                case S_TILT:
                    return new Vector2(40f * d, 25f);
                case D_TILT:
                    return new Vector2(50f * d, 5f);
                case U_TILT:
                    return new Vector2(5f * d, 45f);
                default:
                    return new Vector2(10f * d, 10f);
            }
        }

        public static int getDamage(Character.Attack attackType) {
            switch (attackType) {
                case JAB:
                    return 4;
                case S_TILT:
                    return 8;
                case D_TILT:
                    return 6;
                case U_TILT:
                    return 9;
                default:
                    return 1;
            }
        }

    }

    private static class KnightAttackData{
        public static Vector2 getPosition(Body body, float bodyWidth, float bodyHeight, Character.Attack attackType, boolean direction) {
            int d = (direction) ? 1 : -1;
            switch (attackType) {
                case JAB:
                    return new Vector2(body.getPosition().x + (bodyWidth * d) + (.5f * d), body.getPosition().y);
                case S_TILT:
                    return new Vector2(body.getPosition().x + (bodyWidth * d) + (getDimension(Character.Attack.S_TILT).x * d), body.getPosition().y - getDimension(Character.Attack.S_TILT).y);
                case D_TILT:
                    return new Vector2(body.getPosition().x + (bodyWidth * d) + (getDimension(Character.Attack.S_TILT).x * d), (body.getPosition().y - bodyHeight) + getDimension(Character.Attack.S_TILT).y);
                case U_TILT:
                    return new Vector2(body.getPosition().x, body.getPosition().y + bodyHeight * 1.5f + getDimension(Character.Attack.D_TILT).y);
                default:
                    return new Vector2(0, 0);
                //add more cases as we add more attacks

            }
        }

        public static Vector2 getDimension(Character.Attack attackType) {
            switch (attackType) {
                case JAB:
                    return new Vector2(1f, .5f);
                case S_TILT:
                    return new Vector2(1.5f, .5f);
                case D_TILT:
                    return new Vector2(1.25f, .5f);
                case U_TILT:
                    return new Vector2(.75f, 2.5f);
                default:
                    return new Vector2(.5f, 1f);
            }
        }

        public static int getFrames(Character.Attack attackType) {
            switch (attackType) {
                case JAB:
                    return 30;
                case S_TILT:
                    return 45;
                case D_TILT:
                    return 30;
                case U_TILT:
                    return 40;
                default:
                    return 30;
            }
        }

        public static Vector2 getKnockback(Character.Attack attackType, boolean direction) {
            int d = (direction) ? 1 : -1;
            switch (attackType) {
                case JAB:
                    return new Vector2(20f * d, 20f);
                case S_TILT:
                    return new Vector2(40f * d, 25f);
                case D_TILT:
                    return new Vector2(50f * d, 5f);
                case U_TILT:
                    return new Vector2(5f * d, 45f);
                default:
                    return new Vector2(10f * d, 10f);
            }
        }

        public static int getDamage(Character.Attack attackType) {
            switch (attackType) {
                case JAB:
                    return 4;
                case S_TILT:
                    return 8;
                case D_TILT:
                    return 6;
                case U_TILT:
                    return 9;
                default:
                    return 1;
            }
        }

    }


    //class that stores data about characters such as size, density, sprites, etc
    public static class CharacterData{
        public static Vector2 getBodySize(Character characterType){
            if (characterType instanceof Knight)
                return new Vector2(2f, 3f * Window.yConst);
            if (characterType instanceof Assassin)
                return new Vector2(2f, 2.5f * Window.yConst);
            else
                return new Vector2(1f, 1f * Window.yConst);
        }
        public static float getDensity(Character characterType){
            if (characterType instanceof Knight)
                return .08f;
            if (characterType instanceof Assassin)
                return .09f;
            else
                return .5f;
        }
        public static Sprite getSprite(Character characterType){
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

    public static class TextStuff {


    }

}