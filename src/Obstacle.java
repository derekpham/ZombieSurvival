import javalib.worldimages.Posn;
import javalib.worldimages.WorldImage;

/**
 * Created by derek on 3/17/17.
 */
public abstract class Obstacle {
  Posn topLeft;
  Posn botRight;

  Obstacle(Posn topLeft, Posn botRight) {
    this.topLeft = topLeft;
    this.botRight = botRight;
  }

  boolean checkCollision(Entity e) {
    return this.topLeft.x < e.pos.x
            && this.botRight.x > e.pos.x
            && this.topLeft.y > e.pos.y
            && this.botRight.y < e.pos.y;
  }

  abstract WorldImage render();
}