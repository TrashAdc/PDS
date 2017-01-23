import java.awt.*;
import javax.swing.*;
/**
 * created by ryan v on 1/17/2017
 **/

//this is the 'Graphics Class' that i made from scratch because the other one that was given to us was too messy and disorganized
//i'll try to comment everything that's not already self explanatory because its much easier to organize and modify that way - ryan

public class Window extends JPanel implements Runnable {

    public static JFrame frame; //the physical window
    public static String TITLE = "oh god what am i doing"; //title of window
    public static Dimension SIZE = new Dimension(960, 540); //dimensions of window
    private Drawer drawitplease = new Drawer(); //this can be changed or be added to; this object has its own draw method
                                                // which handles all the drawing, positioning, and graphical stuff (with a jpanel)
    public static final ActiveKeys key = new ActiveKeys(); //this keylistener is GLOBAL. access any key by typing Window.key.-



    public Window(){
        frame = new JFrame(TITLE);
        frame.add(this); //adds the actual frame to the frame i think all i know is that if you remove this you cant even have a window so
        frame.add(drawitplease); //adds the jpanel to the jframe (the drawing part)
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //tells the process to terminate when the frame is closed
        frame.setSize(SIZE); //sets size of the window
        frame.setVisible(true); //honestly have no idea what this does or if it makes a difference
        frame.setLayout(null); //same with this ^^^
        addKeyListener(key); //adds the key listener
    }



    @Override
    public void run(){ //this method runs constantly
        while(true){
            requestFocus();// focus for the key listener

            drawitplease.draw(); //see the 'Drawer' class
            try{Thread.sleep(10);} //pauses the thread for 10 ms
            catch(InterruptedException ie){ie.printStackTrace();}
        }

    }



}
