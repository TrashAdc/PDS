/**
 * Created by Devin Popock on 1/17/2017.
 */
public class Score {
    private int P1Kill;
    private int P2Kill;
    private int stockCount1 = 3;
    private int stockCount2 = 3;
    private boolean KOP2;
    private boolean KOP1;
    private boolean stockmodeplaceholder;

    // Returns value of percent as a String
    public String scoreConverter(int inputScore) {
        String converted = new String.valueOf(inputScore);
        return converted;

    }

    // Stores amount of kills by Player1/Player2 as an int
    public int statKillP1() {
        KOP2 = false;
        if (KOP2 == true) {
            P1Kill++;
            KOP2 = false;
        }
        return P1Kill;
    }

    public int statKillP2() {
        KOP1 = false;
        if (KOP1 == true) {
            P2Kill++;
            KOP2 = false;
        }
        return P2Kill;
    }

    // THIS METHOD IS FOR STOCK MODE ONLY
    public int stockHolder1() {
        if (stockmodeplaceholder == true) {

                if (KOP2 == true) {
                    stockCount1--;
                    KOP2 = false;
                }
            }
        return stockCount1;



    }
}