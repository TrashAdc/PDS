import javax.swing.*;
import java.awt.*;

/**
 * created by ryan v on 1/17/2017
 **/
//this class draws whatever to the screen and also takes in key input as well
//key input may need to be moved to a different class that handles game logic and positioning in the future
public class Drawer extends JPanel {

    private int x = 0; //a position of the text that is being drawn to thescreen
    private ActiveKeys key = new ActiveKeys(); //this is the key listener

    public Drawer(){
        addKeyListener(key);
    }

    public void move(){
        requestFocus(); //requests focus so that the key listener doesn't get 'ignored'
                        //this must get called before taking key input (i think)
        if (key.left) //key.left and key.right are public variables in the ActiveKeys class that become true whenever a key is pressed down.
            x--;
        if (key.right)//more keys like these can be added very easily to the class when needed
            x++;

        if (x > Window.SIZE.width)
            x = -25;
        else if (x < -25)
            x = Window.SIZE.width; //random logic
    }

    @Override
    public void paint(Graphics g){
        super.paint(g); //this resets everything on the screen. otherwise it will become one huge psychedelic mess. maybe that's what you want. i wont judge
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.blue);
        g2d.drawString("help me", x, 100); //this is all self explanatory
    }

    public void draw(){
        move();
        repaint(); //have this at the end of every jpanel draw method; delete it and literally nothing will happen. as in nothing will move at all
    }


}
