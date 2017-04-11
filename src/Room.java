import java.awt.*;

import javalib.worldimages.*;

/**
 * Created by dhruv on 3/21/17.
 */
public class Room extends Obstacle {
  Room(Posn topLeft, Posn botRight) {
    super(topLeft, botRight);
  }

  public boolean collides(Entity e) {
    return this.topLeft.x > e.pos.x
        && this.botRight.x < e.pos.x
        && this.topLeft.y < e.pos.y
        && this.botRight.y > e.pos.y;
  }

  boolean collides(Posn pos, int hitRadius) {
    return !Utils.isWithinBoundary(pos, this.topLeft, this.botRight);// TODO add conrner collisions using pythagorean
  }

  WorldImage render() {
    return new EmptyImage();
  }
}
