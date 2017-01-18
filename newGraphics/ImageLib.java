import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
/**
 * created by ryan v on 1/18/2017
 **/

//this class loads all the images from a specified file into a map, which can be accessed from anywhere with the getImage() method.

public class ImageLib {
    private final String path; //the folder where the goods ( ͡° ͜ʖ ͡°) are located
    private File directory; //file object that uses the path to see the goods ( ͡° ͜ʖ ͡°) in all of their glory
    private Map<String, BufferedImage> imageMap = new HashMap<>(); //this is where the goods ( ͡° ͜ʖ ͡°) will be stored
                                                                   //the string is the file name, while the BufferedImage is the image itself

    public ImageLib(){
        path = "C:\\Users\\Ryan\\IdeaProjects\\testytest\\src\\image"; //make this the path to the folder where the goods ( ͡° ͜ʖ ͡°) are
        directory = new File(path);

        for (File file : directory.listFiles()){ //this loop goes through the file and puts all the goods ( ͡° ͜ʖ ͡°) into a map
            if(file.getName().toLowerCase().endsWith(".png")) { //BUT ONLY IF ITS AN ACTUAL IMAGE (.jpg can be added if needed)

            try { imageMap.put(file.getName(), ImageIO.read(file)); } //actually puts the goods ( ͡° ͜ʖ ͡°) in the map
            catch (IOException ex) { Logger.getLogger(ImageLib.class.getName()).log(Level.SEVERE,null, ex); }

            }
        }
    }

    public BufferedImage getImage(String filename){ //this method is used to obtain the goods ( ͡° ͜ʖ ͡°) it will return the BufferedImage itself
        if (imageMap.get(filename) != null)
            return imageMap.get(filename);

        else { //if the image file name does not exist, it will return an image of a question mark
            try {
                return ImageIO.read(this.getClass().getResource("missingimg.png"));}
                catch (IOException ex) {Logger.getLogger(ImageLib.class.getName()).log(Level.SEVERE, null, ex);}
        }
        return null;
    }

    public TexturePaint getTexture(String filename, Rectangle rect){ //same as getImage() but with texture paint
        if (imageMap.get(filename) != null)
            return new TexturePaint(imageMap.get(filename), rect);

        else { //if the image file name does not exist, it will return an image of a question mark
            try {
                return new TexturePaint(ImageIO.read(this.getClass().getResource("missingimg.png")), rect);}
            catch (IOException ex) {Logger.getLogger(ImageLib.class.getName()).log(Level.SEVERE, null, ex);}
        }
        return null;
    }
//( ͡° ͜ʖ ͡°) ( ͡° ͜ʖ ͡°) ( ͡° ͜ʖ ͡°) ( ͡° ͜ʖ ͡°) ( ͡° ͜ʖ ͡°) ( ͡° ͜ʖ ͡°) ( ͡° ͜ʖ ͡°) ( ͡° ͜ʖ ͡°) ( ͡° ͜ʖ ͡°) ( ͡° ͜ʖ ͡°) ( ͡° ͜ʖ ͡°) ( ͡° ͜ʖ ͡°) ( ͡° ͜ʖ ͡°) ( ͡° ͜ʖ ͡°) ( ͡° ͜ʖ ͡°)
}
