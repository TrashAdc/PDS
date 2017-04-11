package com.mygdx.game;

/**
 * created by ryan v on 4/9/2017
 **/
public class Score_ {
    private int stockP1, stockP2;
    private int damageP1, damageP2;

    public Score_(int stocks){
        stockP1 = stocks;
        stockP2 = stocks;

        damageP1 = 0;
        damageP2 = 0;
    }
    // Returns value of percent as a String
    public String scoreConverter(int inputScore) {
        String converted = Integer.toString(inputScore);
        return converted;

    }

    //returns stock/damage of a player
    public int getStock(GameData.Player player){
        return (player == GameData.Player.PLAYER1) ? stockP1 : stockP2;
    }
    public int getDamage(GameData.Player player){
        return (player == GameData.Player.PLAYER1) ? damageP1 : damageP2;
    }

    //sets stock/damage of a player
    public void setStock(GameData.Player player, int stock){
        if (player == GameData.Player.PLAYER1)
            stockP1 = stock;
        else
            stockP2 = stock;
    }
    public void setDamage(GameData.Player player, int damage){
        if (player == GameData.Player.PLAYER1)
            damageP1 = damage;
        else
            damageP2 = damage;
    }
}
