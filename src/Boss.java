import java.awt.*;
import java.util.List;
import java.util.Random;

import javalib.worldimages.FromFileImage;
import javalib.worldimages.Posn;
import javalib.worldimages.WorldImage;

/**
 * Created by derek on 4/12/17.
 */
public class Boss extends Entity {
  String name;
  String sourceFile;
  Random random = new Random();

  public Boss(Posn pos, int level, double direction, String name, String sourceFile) {
    super(pos, level, direction);
    this.hp = this.level * 50;
    this.hitCircle = 100;
    this.moveSpeed = 0;
    this.name = name;
    this.sourceFile = sourceFile;
  }

  @Override
  void move(List<Obstacle> obstacles) {
    // does nothing, bosses don't move in this game
  }

  @Override
  WorldImage render() {
    return new FromFileImage(this.sourceFile);
  }

  void attack(List<Bullet> bullets) {
    double rando = this.random.nextDouble();
    if (rando > 0.7) {
      Posn pos = new Posn(this.pos.x - 20, this.pos.y + 55);
      bullets.add(new Bullet(pos, this.level, this.random.nextDouble() * Math.PI + Math.PI / 2, Color.RED));
    }
  }
}
