import javalib.worldimages.Posn;
import javalib.worldimages.WorldImage;

/**
 * Created by derek on 4/12/17.
 */
public abstract class AbstractPowerup {
  Posn pos;

  AbstractPowerup(Posn pos) {
    this.pos = pos;
  }

  abstract void affect(Player player);

  abstract WorldImage render();

  boolean checkCollision(Player player) {
    return Utils.checkCollision(this.pos, 25, player.getPos(), player.hitCircle);
  }

  Posn getPos() {
    return this.pos;
  }
}
