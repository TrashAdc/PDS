package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by Devin Popock on 1/17/2017.
**/
public class Score {
    private int P1Kill = 0;
    private int P2Kill = 0;
    private int stockP1 = 3, stockP2 = 3;;
    private float chargeP1, chargeP2;
    private int damageP1, damageP2;
    private boolean stockmodeplaceholder;
    private Sprite p1Stock, p2Stock;

    public Score(int stocks){
        chargeP1 = 0f;
        chargeP2 = 0f;

        damageP1 = 0;
        damageP2 = 0;

        stockP1 = stocks;
        stockP2 = stocks;

        p1Stock = new Sprite(new Texture("core/assets/image/p1stock.png"));
        p2Stock = new Sprite(new Texture("core/assets/image/p2stock.png"));
    }

    // Returns value of percent as a String
    public String scoreConverter(int inputScore) {
        String converted = Integer.toString(inputScore);
        return converted;

    }
    /*public float chargecalc(){
        basicAstat/10=percentmodifier;
        percentmodifier2=10/100;;
        DamageD*percentmodifier=chargepart1;
        DamageR*percentmodifier2=chargepart2;

        return charge=chargepart2+chargepart1;
    }*/
    private void attackCharge(int baseAtk, GameData.Player player){ //gives player ultimate charge based on damage dealt
        float c = baseAtk / 2;
        if (player == GameData.Player.PLAYER1)
            chargeP1 += c;
        else
            chargeP2 += c;
    }
    private void hitCharge(int baseAtk, GameData.Player player){ //gives player ultimate charge based on damage taken
        float c = baseAtk / 10;
        if (player == GameData.Player.PLAYER1)
            chargeP1 += c;
        else
            chargeP2 += c;
    }

    public int getDamage(GameData.Player player){
        return (player == GameData.Player.PLAYER1) ? damageP1 : damageP2;
    }
    public void addDamage(GameData.Player player, int damage){
        if (player == GameData.Player.PLAYER1) {
            damageP1 += damage; //deal damage
            hitCharge(damage, player); //give charge to the player being hit
            hitCharge(damage, GameData.Player.PLAYER2); //give charge to the attacking player
        }
        else {
            damageP2 += damage;
            hitCharge(damage, player);
            hitCharge(damage, GameData.Player.PLAYER1);
        }
    }

    public int getStock(GameData.Player player){
        return (player == GameData.Player.PLAYER1) ? stockP1 : stockP2;
    }
    private void takeStock(GameData.Player player, int stocks){
        if (player == GameData.Player.PLAYER1)
            stockP1 -= stocks;
        else
            stockP2 -= stocks;
    }

    // Stores amount of kills by Player1/Player2 as an int
    public String playerKilled(GameData.Player playerThatDied){
        if (playerThatDied == GameData.Player.PLAYER1){
            takeStock(playerThatDied, 1);
            damageP1 = 0;
            P2Kill++;
            return scoreConverter(P2Kill);
        }
        if(playerThatDied == GameData.Player.PLAYER2) {
            takeStock(GameData.Player.PLAYER2, 1);
            damageP2 = 0;
            P1Kill++;
            return scoreConverter(P1Kill);
        }
        return "";
    }

    public void drawStock(SpriteBatch batch){
        int stock1 = getStock(GameData.Player.PLAYER1);
        int stock2 = getStock(GameData.Player.PLAYER2);
        float posX1 = (Window.SIZE.width / 10) * 3;
        float posX2 = 4f;
        float posY = Window.SIZE.height / 6 + 128;
        //todo finish this

        if (stock1 == 3)
            p1Stock.setPosition(posX1 - 128, posY);
            p1Stock.draw(batch);
           // else if (stock1 == 2)
           //     else if (stock1 == 1)


    }


    /*public boolean gameState() {
        while (gameon) {
            if (stockmodeplaceholder == true) {
                if (stockCount1 == 0 || stockCount2 == 0) {
                    gameover = true;
                    gameon = false;
                }
            } else {
                if (timephold == 0) {
                    gameover = true;
                    gameon = false;
                }
            }
        }
        return gameover;
    }

    public boolean gameResults() {
        if (gameover) {
            if (stockmodeplaceholder) {
                if (stockCount1 == 0) {
                    //winner2=true
                    //return winner2
                }
                else if(stockCount2==0){
                    //winner1=true
                    //return winner1
                }

            }
            else{
                if(P1Kill>P2Kill){
                    //winner1=true
                    //return winner1
                }
                else if(P2Kill>P1Kill){
                    //winner2=true
                    //return winner2 q
                }
            }
        }
        return false;
    }*/
}
