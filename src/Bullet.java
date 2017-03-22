import java.awt.*;
import java.util.List;

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
  void move(List<Obstacle> obstacles) {
    int newX = this.moveSpeed * (int) Math.cos(Math.toRadians(this.dir));
    int newY = this.moveSpeed * (int) Math.sin(Math.toRadians(this.dir));
    Posn newPos = new Posn(newX, newY);
    if((new Utils()).isPosnValid(newPos, obstacles)) {
      this.pos = newPos;
    }
  }

  boolean hit(Zombie zombie) {
    if(new Utils().checkCollision(this.pos, this.attackRadius, zombie.pos, zombie.hitCircle)) {
      zombie.hp -= this.dmg;
      return true;
    }
    return false;
  }

  WorldImage render() {
    return new CircleImage(5, OutlineMode.SOLID, Color.GREEN);
  }
}
