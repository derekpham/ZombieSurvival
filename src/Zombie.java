import java.util.*;
import javalib.worldimages.*;

/**
 * Created by derek on 3/17/17.
 */

// to represent a zombie
public class Zombie extends Entity {
  int turnCounter;
  Random r = new Random();
  final static int TOTAL_TURNS = 20;

  Zombie(Posn pos, int level, double direction) {
    super(pos, level, direction);
    this.hp = 30 + this.level * 10;
    this.dmg = this.hp / 4;
    if (level < 5) {
      this.sightRadius = 0;
    } else {
      this.sightRadius = 5 + (level - 5) * 2;
    }
    this.attackRadius = 5 + this.level * 2;
    this.hitCircle = 10;
    this.turnCounter  = 0;
    this.moveSpeed = 10;
  }

  void move(List<Obstacle> obstacles) {
    throw new UnsupportedOperationException();
  }

  void move(Player prey, List<Obstacle> obstacles) {
    if (Utils.distanceBetween(this.pos, prey.getPos()) < this.sightRadius) {
      this.dir = Utils.getDegreeDir(this.pos, prey.getPos());
    } else if (this.turnCounter <= 0) {
      this.turnCounter = TOTAL_TURNS;
      this.dir = r.nextDouble() * 360;
    } else {
      this.turnCounter -= 1;
    }

    int newX = (int)(this.moveSpeed * Math.cos(this.dir)) + this.pos.x;
    int newY = (int)(this.moveSpeed * Math.sin(this.dir)) + this.pos.y;
    Posn newPos = new Posn(newX, newY);
    if((new Utils()).isPosnValid(newPos, this.hitCircle, obstacles)) {
      this.pos = newPos;
    }
  }

  boolean checkCollision(Bullet bullet) {
    return new Utils().checkCollision(this.pos, this.hitCircle, bullet.pos, bullet.attackRadius);
  }

  void takeHit(Bullet bullet) {
    this.hp -= bullet.dmg;
  }

  boolean isDead() {
    return this.hp <= 0;
  }

  WorldImage render() {
    return new FromFileImage("src/zombie.png");
  }
}
