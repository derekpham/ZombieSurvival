import java.awt.*;
import java.util.List;

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
    this.moveSpeed = 20;
  }

  void move(List<Obstacle> obstacles) {
    int newX = (int) (this.moveSpeed * Math.cos(this.dir)) + this.pos.x;
    int newY = (int) (this.moveSpeed * Math.sin(this.dir)) + this.pos.y;
    Posn newPos = new Posn(newX, newY);
    this.pos = newPos;
  }

  boolean hit(Zombie zombie) {
    if (new Utils().checkCollision(this.pos, this.attackRadius, zombie.pos, zombie.hitCircle)) {
      zombie.hp -= this.dmg;
      return true;
    }
    return false;
  }

  WorldImage render() {
    return new CircleImage(5, OutlineMode.SOLID, Color.GREEN);
  }
}
