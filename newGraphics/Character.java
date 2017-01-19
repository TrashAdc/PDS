import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
//import static com.sun.java.accessibility.util.AWTEventMonitor.addKeyListener;

/**
 * Created by 236040 on 1/19/2017.
 */
public class Character extends JPanel{
    private BufferedImage playerSprite;
    private ActiveKeys hi;
    private int pos_x, pos_y;
    private Point pos;
    private int speed;

    public Character(){

        playerSprite = new ImageLib().getImage("spr_parent.png");

        hi = new ActiveKeys();
        addKeyListener(hi);

        pos_x = Window.SIZE.width / 2;
        pos_y = Window.SIZE.height / 3;
        pos = new Point(pos_x, pos_y);

        speed = 20;
    }

    public BufferedImage getPlayerSprite(){
        return playerSprite;
    }

    public Point getPosition(){
        movement();
        return pos;
    }

    private Point movement(){
        requestFocus();
        if (hi.left)
            pos.setLocation(pos.getX() - speed, pos.getY());
        if (hi.right)
            pos.setLocation(pos.getX() + speed, pos.getY());

        return pos;
    }








}
