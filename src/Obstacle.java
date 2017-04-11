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
    return this.topLeft.x < (e.pos.x + e.hitCircle)        // checks line collisions
            && this.botRight.x > (e.pos.x - e.hitCircle)
            && this.topLeft.y > (e.pos.y - e.hitCircle)
            && this.botRight.y < (e.pos.y + e.hitCircle); // TODO add conrner collisions using pythagorean
  }

  boolean collides(Posn pos, int hitRadius) {
    return Utils.isWithinBoundary(pos, this.topLeft, this.botRight); // TODO add conrner collisions using pythagorean
  }

  abstract WorldImage render();
}