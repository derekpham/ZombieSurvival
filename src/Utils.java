import java.util.List;
import javalib.worldimages.Posn;
import java.math.*;

/**
 * Created by derek on 3/17/17.
 */
public class Utils {
  boolean isPosnValid(Posn newPosn, List<Obstacle> obstacles) {
    return true;
  }
  boolean checkCollision(Posn object1, int size1, Posn object2, int size2) {
    return false;
  }
  double getDegreeDir(Posn from, Posn to) {
    return Math.toDegrees(Math.atan2(to.y - from.y, to.x - from.x));
  }
}
