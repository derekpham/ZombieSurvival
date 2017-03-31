import java.util.List;
import javalib.worldimages.Posn;
import java.math.*;

/**
 * Created by derek on 3/17/17.
 */
public class Utils {
  static boolean isPosnValid(Posn newPosn, int hitCircle, List<Obstacle> obstacles) {
    boolean isValid = true;
    for (Obstacle obstacle : obstacles) {
      isValid = isValid && !obstacle.collides(newPosn, hitCircle);
    }
    return isValid;
  }

  static boolean isWithinBoundary(Posn posn, Posn topLeft, Posn botRight) {
    return posn.x > topLeft.x && posn.y >topLeft.y && posn.x < botRight.x && posn.y < botRight.y;
  }

  static boolean isEntityValid(Entity e, List<Obstacle> obstacles) {
    boolean isValid = true;
    for (Obstacle obstacle : obstacles) {
      isValid = isValid && !obstacle.collides(e);
    }
    return isValid;
  }

  static boolean checkCollision(Posn loc1, int size1, Posn loc2, int size2) {
    double totalSize = size1 + size2;
    double distance = Math.sqrt((loc2.x-loc1.x)^2 + (loc2.y - loc1.y)^2);

    return Math.abs(distance - totalSize) < .01;
  }

  static double getDegreeDir(Posn from, Posn to) {
    return Math.toDegrees(Math.atan2(to.y - from.y, to.x - from.x));
  }
}
