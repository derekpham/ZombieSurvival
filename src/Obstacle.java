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

  Posn getPos() {
    return new Posn((this.botRight.x + this.topLeft.x) / 2, (this.botRight.y + this.topLeft.x) / 2);
  }

  boolean collides(Entity e) {
    return this.topLeft.x < e.pos.x
            && this.botRight.x > e.pos.x
            && this.topLeft.y > e.pos.y
            && this.botRight.y < e.pos.y;
  }

  boolean collides(Posn pos, int hitRadius) {
    return this.topLeft.x < pos.x
            && this.botRight.x > pos.x
            && this.topLeft.y > pos.y
            && this.botRight.y < pos.y;
  }

  abstract WorldImage render();
}