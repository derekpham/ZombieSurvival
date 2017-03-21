import java.awt.*;

import javalib.funworld.World;
import javalib.worldimages.CircleImage;
import javalib.worldimages.OutlineMode;
import javalib.worldimages.Posn;
import javalib.worldimages.WorldImage;

/**
 * Created by derek on 3/17/17.
 */

// to represent a bullet
public class Bullet extends Entity {
  Bullet(Posn pos, int level, double direction) {
    super(pos, level, direction);
    this.attackRadius = 5;
    this.dmg = 15 + this.level * 2;
  }

  // return a new bullet with updated pos and same dx, dy
  void move() {

  }

  void hit(Zombie zombie) {

  }

  WorldImage render() {
    return new CircleImage(5, OutlineMode.SOLID, Color.GREEN);
  }
}
