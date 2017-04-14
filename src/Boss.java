import java.awt.*;
import java.util.List;
import java.util.Random;
import javalib.worldimages.*;

/**
 * Created by derek on 4/12/17.
 */
public class Boss extends Entity {
  String name;
  String sourceFile;
  Random random = new Random();
  int maxHP;

  public Boss(Posn pos, int level, double direction, String name, String sourceFile) {
    super(pos, level, direction);
    this.hp = this.level * 30;
    this.hitCircle = 100;
    this.moveSpeed = 0;
    this.name = name;
    this.sourceFile = sourceFile;
    this.maxHP = this.hp;
    if (this.level > 14) {
      this.hitCircle = 100;
    }
  }

  @Override
  void move(List<Obstacle> obstacles) {
    // does nothing, bosses don't move in this game
  }

  @Override
  WorldImage render() {
    WorldImage image = new FromFileImage(this.sourceFile);
    if (this.sourceFile.equals("src/alien.jpg")) {
      image = new ScaleImage(image, 0.5);
    }
    return new AboveImage(this.renderHP(), image);
  }

  WorldImage renderHP() {
    return new OverlayImage(new RectangleImage((int) (190 * (1.0 * this.hp) / this.maxHP), 12, OutlineMode.SOLID, Color.RED),
            new RectangleImage(190, 12, OutlineMode.OUTLINE, Color.BLACK));
  }

  void attack(List<Bullet> bullets) {
    double rando = this.random.nextDouble();
    if (rando > 0.9) {
      Posn pos = new Posn(this.pos.x - 20, this.pos.y + 55);
      Bullet bullet = new Bullet(pos, this.level, this.random.nextDouble() * Math.PI + Math.PI / 2, Color.RED);
      bullet.dmg = 30;
      bullets.add(bullet);
    }
  }
}
