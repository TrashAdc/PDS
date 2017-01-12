import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by taylor hudson on 1/5/2017.
 */
class Graphics implements Runnable, KeyListener, WindowListener, MouseListener {
    public final String TITLE = "oh god please help me";
    public final Dimension SIZE = new Dimension(600, 950);
    public JFrame frame;
    private boolean isRunning, isDone;
    private Image imgBuffer;
    private BufferedImage stone, grass, pig, dirt;
    private TexturePaint stoneOcta, grassOcta, dirty;
    private boolean change;
    @SuppressWarnings("unused")
    private Color BROWN;
    @SuppressWarnings("unused")
    private boolean AITurn, UserTurn;
    private Rectangle myRect;
    private Point current;

    private int spd = 5;
    private boolean left = false, right = false, up = false, down = false;

    private int frameCount = 0, fps= 0;
    private long currentSecond;


    public void setChange(boolean change) {
        this.change = change;
    }

    private void loadImages() {

        try {


            stone = ImageIO.read(this.getClass().getResource("java.png"));
            grass = ImageIO.read(this.getClass().getResource("grass.png"));
            pig = ImageIO.read(this.getClass().getResource("16.png"));
            dirt = ImageIO.read(this.getClass().getResource("dirt.png"));
            grassOcta = new TexturePaint(grass, new Rectangle(0, 0, 90, 60));
            stoneOcta = new TexturePaint(stone, new Rectangle(0, 0, 90, 60));
            dirty = new TexturePaint(dirt, new Rectangle(0, 0, 50, 50));


        } catch (IOException ex) {

            Logger.getLogger(Driver.class.getName()).log(Level.SEVERE,null, ex);
        }
    }

    public Graphics(){

        loadImages();
        setChange(true);
        current = new Point(35,70);
        myRect = new Rectangle((int)current.getX(), (int)current.getY(), 23, 80); // x,y,h,w to move just change x and y
        BROWN = new Color(139,69,19);
        frame = new JFrame();
        frame.addKeyListener(this);
        frame.addWindowListener(this);
        frame.addMouseListener(this);
        frame.setSize(SIZE);
        frame.setTitle(TITLE);
        isRunning = true;
        isDone = false;
        frame.setVisible(true);
        frame.setLayout(null);
        imgBuffer = frame.createImage(SIZE.width, SIZE.height);

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int Key;
        Key = e.getKeyCode();

        switch(Key) {
            case KeyEvent.VK_UP:
                up = true;
                break;
            case KeyEvent.VK_LEFT:
                left = true;
                break;
            case KeyEvent.VK_DOWN:
                down = true;
                break;
            case KeyEvent.VK_RIGHT:
                right = true;
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int Key;
        Key = e.getKeyCode();

        switch(Key) {
            case KeyEvent.VK_UP:
                up = false;
                break;
            case KeyEvent.VK_LEFT:
                left = false;
                break;
            case KeyEvent.VK_DOWN:
                down = false;
                break;
            case KeyEvent.VK_RIGHT:
                right = false;
                break;
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void windowOpened(WindowEvent e) {

    }

    @Override
    public void windowClosing(WindowEvent e) {
        frame.setVisible(false);
        frame.dispose();
        isRunning = false;
    }

    @Override
    public void windowClosed(WindowEvent e) {
        while(true){

            if(isDone){
                System.exit(0);
            }
            try{
                Thread.sleep(100);
            }
            catch(InterruptedException ie){
                ie.printStackTrace();
            }
        }
    }

    @Override
    public void windowIconified(WindowEvent e) {

    }

    @Override
    public void windowDeiconified(WindowEvent e) {

    }

    @Override
    public void windowActivated(WindowEvent e) {

    }

    @Override
    public void windowDeactivated(WindowEvent e) {

    }

    @Override
    public void run() {
        currentSecond = System.currentTimeMillis() / 1000;
        while(isRunning){

            draw();
            frameCount++;
            if (currentSecond < System.currentTimeMillis() / 1000){
                currentSecond = System.currentTimeMillis() / 1000;
                fps = frameCount;
                frameCount = 0;
            }
            //System.out.println(frameCount);


            if(change){
                setChange(false);

            }
            try{Thread.sleep(5);}
            catch(InterruptedException ie){
                ie.printStackTrace();
            }
        }
        isDone = true;
    }

    private void draw() {

        if (down)
            myRect.setLocation((int) myRect.getX(), (int) myRect.getY() + spd);
        if (up)
            myRect.setLocation((int) myRect.getX(), (int) myRect.getY() - spd);
        if (right)
            myRect.setLocation((int) myRect.getX() + spd, (int) myRect.getY());
        if (left)
            myRect.setLocation((int) myRect.getX() - spd, (int) myRect.getY());


        // TODO Auto-generated method stub
        Graphics2D g2d = (Graphics2D) imgBuffer.getGraphics();
        g2d.setPaint(dirty);
        g2d.fillRect(0, 0, SIZE.width, SIZE.height);
        g2d.setPaint(stoneOcta);
        g2d.fillRect((int)myRect.getX(), (int)myRect.getY(), (int)myRect.getWidth(), (int)myRect.getHeight());
        g2d.setColor(Color.PINK);
        Stroke old = g2d.getStroke();
        g2d.setStroke(new BasicStroke(3));
        g2d.draw(myRect);
        g2d.setStroke(old);
        g2d.drawString("fps: " + Integer.toString(fps), SIZE.width / 2, SIZE.height / 2);
        if(isRunning)
            g2d = (Graphics2D) frame.getGraphics();
        g2d.drawImage(imgBuffer, 0,  0, SIZE.width, SIZE.height, 0, 0, SIZE.width, SIZE.height, null);
        g2d.dispose();
    }
}
