
/**
 * Created by Taylor on 2/12/2017.
 * Short ReadMe the library file contains a jar file that has the necessary library for .mp3 files
 */
public class AudioTest {
    public static void main(String [] args0){
       AudioPlayer example = new AudioPlayer("0477.mp3");

       // This is if you want a loop
       new Thread(example).start();
       // This is if you want the single go
       //example.playOnce();
      // example.playOnce();
    }
}
