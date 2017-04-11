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
  int hitTimer;

  Zombie(Posn pos, int level, double direction) {
    super(pos, level, direction);
    this.hp = 30 + this.level * 10;
    this.dmg = this.hp / 4;
    if (level < 5) {
      this.sightRadius = 50;
    } else {
      this.sightRadius = 50 * (level - 4);
    }
    this.attackRadius = 25 + this.level;
    this.hitCircle = 25;
    this.turnCounter  = 0;
    this.moveSpeed = 7;
    this.hitTimer = 0;
  }

  void move(List<Obstacle> obstacles) {
    throw new UnsupportedOperationException();
  }

  void move(Player prey, List<Obstacle> obstacles) {
    if (Utils.distanceBetween(this.pos, prey.getPos()) < this.sightRadius) {
      this.dir = Utils.getDir(this.pos, prey.getPos());
    } else if (this.turnCounter <= 0) {
      this.turnCounter = TOTAL_TURNS + r.nextInt(TOTAL_TURNS/2);
      this.dir = r.nextDouble() * Math.PI * 2;
    } else {
      this.turnCounter -= 1;
    }

    int newX = (int)(this.moveSpeed * Math.cos(this.dir)) + this.pos.x;
    int newY = (int)(this.moveSpeed * Math.sin(this.dir)) + this.pos.y;
    Posn newPos = new Posn(newX, newY);
    if(Utils.isPosnValid(newPos, this.hitCircle, obstacles)) {
      this.pos = newPos;
    } else {
      this.dir = r.nextDouble() *360;
    }
  }

  boolean checkCollision(Bullet bullet) {
    return Utils.checkCollision(this.pos, this.hitCircle, bullet.pos, bullet.attackRadius);
  }

  void takeHit(Bullet bullet) {
    this.hp -= bullet.dmg;
  }

  void hit(Player player) {
    if (this.hitTimer <= 0) {
      player.hp -= this.dmg;
      this.hitTimer = 20;
    } else {
      this.hitTimer -= 1;
    }
  }

  boolean isDead() {
    return this.hp <= 0;
  }

  WorldImage render() {
    return new FromFileImage("src/zombie.png");
  }
}