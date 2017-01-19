/**
 * Created by 250065 on 1/17/2017.
 */
public class math {
    //works with score class and percentage
    private int  gravity;
    private int velocity;
    private int initial_speed;

    public int Gravity(){
        gravity=100;
        velocity=1080;
        initial_speed=0;
        for(int i=30;i<=30;i--) {
            //velocity=modifier
            velocity=initial_speed+(gravity*i);

        }
     return velocity;

    }
}
