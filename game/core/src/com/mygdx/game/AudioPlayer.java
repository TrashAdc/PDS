import javazoom.jlme.util.Player;
/* JavaZoom via
http://www.javazoom.net/javalayer/sourcesme.html
 */
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;

/**
 * Created by Taylor on 2/12/2017.
 * Short ReadMe the library file contains a jar file that has the necessary library for .mp3 files
 */
public class AudioPlayer implements Runnable{
    private Player player;
    private static String input;
    public static boolean isRunning;
    public AudioPlayer(String fileName){
        isRunning = true;
        setInput(fileName);
        init();
    }
    /**
    Uses String input to setup an audioplayer.
     */
    private void init(){
        try{
            File file = new File(input);
            FileInputStream fis = new FileInputStream(file);
            BufferedInputStream bis = new BufferedInputStream(fis);

            player = new Player(bis);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    public void playOnce(){
        try {
            player.play();
            init();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    public static void setInput(String fileName){
        input = fileName;
    }

    @Override
    public void run() {
        while(isRunning){

            try{
                playOnce();
                Thread.sleep(10);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
