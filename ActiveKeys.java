import java.awt.event.*;

/**
 * created by ryan v on 1/17/2017
 **/
//this class is basically just a key listener :P

public class ActiveKeys implements KeyListener {
    public boolean right = false; //these variables turn true when the corresponding key is pressed
    public boolean left = false; //more keys other than left and right can be added by just copying what ive done but with other keys its really that simple

    @Override
    public void keyPressed(KeyEvent e){
        int key = e.getKeyCode();

        switch(key){
            case KeyEvent.VK_LEFT:
                left = true;
                break;
            case KeyEvent.VK_RIGHT:
                right = true;
                break;
        }
    }
    @Override
    public void keyReleased(KeyEvent e){
        int key = e.getKeyCode();

        switch(key){
            case KeyEvent.VK_LEFT:
                left = false;
                break;
            case KeyEvent.VK_RIGHT:
                right = false;
                break;
        }
    }
    @Override
    public void keyTyped(KeyEvent e){} //unused
}
