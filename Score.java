/**
 * Created by Devin Popock on 1/17/2017.
 */
public class Score {
    private int P1Kill = 0;
    private int P2Kill = 0;
    private int stockCount1 = 3;
    private int stockCount2 = 3;
    private boolean KOP2;
    private boolean KOP1;
    private boolean stockmodeplaceholder;
    private boolean gameon = false;
    private int timephold;
    private boolean gameover = false;

    // Returns value of percent as a String
    public String scoreConverter(int inputScore) {
        String converted = new String.valueOf(inputScore);
        return converted;

    }

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

    public int statKillP2() {
        KOP1 = false;
        while (gameon) {
            if (KOP1 == true) {
                P2Kill++;
                KOP2 = false;
            }
        }
        return P2Kill;
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
                }
                else if(stockCount2==0){
                    //winner1=true
                }

            }

        }
    }
}
