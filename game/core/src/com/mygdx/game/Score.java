package com.mygdx.game;
/**
 * Created by Devin Popock on 1/17/2017.
**/
public class Score {
    private int P1Kill = 0;
    private int P2Kill = 0;
    private int stockCount1 = 3;
    private int stockCount2 = 3;
    private boolean KOP2;
    private boolean KOP1;
    private boolean stockmodeplaceholder;
    private boolean gameon = false;
    private boolean ultimateon= false;
    private float charge;
    private float chargepart1;
    private float chargepart2;
    private float percentmodifier;
    private float percentmodifier2;
    private float realcharge;
    private int timephold;
    private boolean gameover = false;
    private int ultimatecharge;

    // Returns value of percent as a String
    public String scoreConverter(int inputScore) {
        String converted = Integer.toString(inputScore);
        return converted;

    }
    /*public float chargecalc(){
        basicAstat/10=percentmodifier;
        percentmodifier2=5/100;;
        DamageD*percentmodifier=chargepart1;
        DamageR*percentmodifier2=chargepart2;

        return charge=chargepart2+chargepart1;
    }*/

    // Stores amount of kills by Player1/Player2 as an int
    public String statKillP1() {
        KOP2 = false;
        while (gameon) {
            if (KOP2 == true) {
                P1Kill++;
                KOP2 = false;
            }

        }
        return scoreConverter(P1Kill);
    }

    public String statKillP2() {
        KOP1 = false;
        while (gameon) {
            if (KOP1 == true) {
                P2Kill++;
                KOP2 = false;
            }
        }
        return scoreConverter(P2Kill);
    }

    // THIS METHOD IS FOR STOCK MODE ONLY
    public int stockHolder1() {
        while (gameon) {
            if (stockmodeplaceholder == true) {

                if (KOP2 == true) {
                    stockCount2--;
                    KOP2 = false;
                    return stockCount2;
                }

            }

        }
        return stockCount2;


    }

    public int stockHolder2() {
        while (gameon) {
            if (stockmodeplaceholder == true) {
                if (KOP1 == true) {
                    stockCount1--;
                    KOP1 = false;
                    return stockCount1;
                }

            }

        }
        return stockCount1;
    }
    /*public float p1UltimateCharge(){
        while(gameon){
            while(ultimateon==false) {
                for (int i = 0; realcharge <= maxcharge; realcharge += chargecalc()) {
                    realcharge += realcharge;
                    return realcharge;
                }
                return realcharge;

            }
        }
        return 0;
    }
*/
    public boolean gameState() {
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
    }
}
