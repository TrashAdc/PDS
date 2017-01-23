import java.awt.Point;
import java.awt.Rectangle;
import java.util.*;

/**
 * Created by ryan v on 1/23/2017.
 */
public class Collision{

    private Map<String, Rectangle> hitboxMap = new HashMap<>();

    public void newCollision(String hitboxId, Rectangle hitbox){
        hitboxMap.put(hitboxId, hitbox);
    }

    public boolean isColliding(String hitbox1, String hitbox2){
        if (hitboxMap.containsKey(hitbox1) && hitboxMap.containsKey(hitbox2))
            return hitboxMap.get(hitbox1).intersects(hitboxMap.get(hitbox2));

        else
            return false;
    }

    public Point collidesAt(String hitbox1, String hitbox2){
        return new Point(0, 0); //this is not right
    }


}
