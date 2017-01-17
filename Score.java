/**
 * Created by Devin Popock on 1/17/2017.
 */
public class Score {
    // Returns value of percent as a String
    public String scoreConverter(int inputScore){
        String percent= new String.valueOf(inputScore);
        return percent;

    }
    // Stores amount of kills by each player as a String
    public int statKill(){
        int P1Kill=0;
        int P2Kill=0;
        boolean KOP1=false;
        boolean KOP2=false;
        if(KOP2==true){
            P1Kill++;
            KOP2=false;
        }
        if(KOP1==true){
            P2Kill++;
            KOP1=false;
        }

        return P1Kill;
    }
}
