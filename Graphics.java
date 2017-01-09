/**created by ryan v on 1/9/2017**/

import java.awt.event.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Graphics implements Runnable, KeyListener, WindowListener, MouseListener {
    public final String TITLE = "im dying inside";
    public final Dimension SIZE = new Dimension(400, 950);
    public JFrame frame;
    private boolean isRunning, isDone, change;
    private Image imgBuffer;
    private BufferedImage image1, image2, image3;
    private TexturePaint texture;
    private Color BROWN;
    private Point current;

    try {
        //implement stuff here xd
    }
    catch (IOException ex){
        //more stuff here
    }

}


