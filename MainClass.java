/**
 * created by ryan v on 1/17/2017
 **/
public class MainClass {
    public static void main(String[] args){
        Window gameWindow = new Window();
        new Thread(gameWindow).start();
    }
}
