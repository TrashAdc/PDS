import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created ryan v on 1/19/2017.
 */

//this is the parent character class. there will be character classes that will be children of this one.
//TODO: add more stuff i guess idk lol

public class Character {
    private BufferedImage playerSprite; //the image of the character, may not need to be here with animation class
    private int pos_x, pos_y; //initial position i guess
    private Point pos; //position stored in coordinates
    private int speed; //speed at which the character moves

    public Character(){

        playerSprite = new ImageLib().getImage("spr_parent.png"); //gets image

        pos_x = Window.SIZE.width / 2;
        pos_y = Window.SIZE.height / 3;
        pos = new Point(pos_x, pos_y);

        speed = 20;
    }

    public BufferedImage getPlayerSprite(){
        return playerSprite;
    }

    public Point getPosition(){ //returns new position of character
        movement();
        return pos;
    }

    private Point movement(){
        if (Window.key.left)
            pos.setLocation(pos.getX() - speed, pos.getY()); //moves the character based on what key is pressed
        if (Window.key.right)
            pos.setLocation(pos.getX() + speed, pos.getY());

        return pos;
    }

//do not worry my friend there will be more to come






}
