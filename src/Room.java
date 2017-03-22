import java.awt.*;

import javalib.worldimages.*;

/**
 * Created by dhruv on 3/21/17.
 */
public class Room extends Obstacle {
  Room(Posn topLeft, Posn botRight) {
    super(topLeft, botRight);
  }

  boolean checkCollision(Entity e) {
    return this.topLeft.x > e.pos.x
        && this.botRight.x < e.pos.x
        && this.topLeft.y < e.pos.y
        && this.botRight.y > e.pos.y;
  }

  WorldImage render() {
    return new EmptyImage();
  }
}
