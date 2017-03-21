import java.awt.*;

import javalib.worldimages.AlignModeY;
import javalib.worldimages.BesideAlignImage;
import javalib.worldimages.CircleImage;
import javalib.worldimages.EquilateralTriangleImage;
import javalib.worldimages.OutlineMode;
import javalib.worldimages.Posn;
import javalib.worldimages.RotateImage;
import javalib.worldimages.WorldImage;

/**
 * Created by derek on 3/17/17.
 */

// to represent a zombie
public class Zombie extends Entity {
  int turnCounter;

  Zombie(Posn pos, int level, double direction) {
    super(pos, level, direction);
    this.hp = 30 + this.level * 10;
    this.dmg = this.hp / 4;
    this.sightRadius = 25 + this.level * 5;
    this.attackRadius = 5 + this.level * 2;
    this.hitCircle = 10;
  }

  void move() {

  }

  WorldImage render() {
    WorldImage image = new BesideAlignImage(AlignModeY.MIDDLE,
            new CircleImage(10, OutlineMode.OUTLINE, Color.BLACK),
            new EquilateralTriangleImage(5.0, OutlineMode.OUTLINE, Color.BLACK));
    return new RotateImage(image, this.dir);
  }
}
