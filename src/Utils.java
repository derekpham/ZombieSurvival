import java.util.List;

import javalib.worldimages.Posn;

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
    return posn.x > topLeft.x && posn.y > topLeft.y
            && posn.x < botRight.x && posn.y < botRight.y;
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
    double distance = distanceBetween(loc1, loc2);

    return distance < totalSize;
  }

  // in radians
  static double getDir(Posn from, Posn to) {
    return Math.atan2(to.y - from.y, to.x - from.x);
  }

  static double distanceBetween(Posn pos1, Posn pos2) {
    return Math.sqrt(Math.pow(pos2.x - pos1.x, 2) + Math.pow(pos2.y - pos1.y, 2));
  }
}
