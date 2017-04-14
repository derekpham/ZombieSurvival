import java.awt.*;
import java.util.List;
import java.util.Random;

import javalib.worldimages.AboveImage;
import javalib.worldimages.FromFileImage;
import javalib.worldimages.OutlineMode;
import javalib.worldimages.OverlayImage;
import javalib.worldimages.Posn;
import javalib.worldimages.RectangleImage;
import javalib.worldimages.WorldImage;

/**
 * Created by derek on 3/17/17.
 */

// to represent a zombie
public class Zombie extends Entity {
  final static int TOTAL_TURNS = 20;
  int turnCounter;
  Random r = new Random();
  int hitTimer;
  int maxHP;

  Zombie(Posn pos, int level, double direction) {
    super(pos, level, direction);
    this.hp = 30 + this.level * 10;
    this.dmg = this.hp / 4;
    if (level < 5) {
      this.sightRadius = 50;
    } else {
      this.sightRadius = 50 * (level - 4);
    }
    this.attackRadius = 10 + this.level;
    this.hitCircle = 25;
    this.turnCounter = 0;
    this.moveSpeed = 7;
    this.hitTimer = 0;
    this.maxHP = this.hp;
  }

  void move(List<Obstacle> obstacles) {
    throw new UnsupportedOperationException();
  }

  void move(Player prey, List<Obstacle> obstacles) {
    if (Utils.distanceBetween(this.pos, prey.getPos()) < this.sightRadius) {
      this.dir = Utils.getDir(this.pos, prey.getPos());
    } else if (this.turnCounter <= 0) {
      this.turnCounter = TOTAL_TURNS + r.nextInt(TOTAL_TURNS / 2);
      this.dir = r.nextDouble() * Math.PI * 2;
    } else {
      this.turnCounter -= 1;
    }

    int newX = (int) (this.moveSpeed * Math.cos(this.dir)) + this.pos.x;
    int newY = (int) (this.moveSpeed * Math.sin(this.dir)) + this.pos.y;
    Posn newPos = new Posn(newX, newY);
    if (Utils.isPosnValid(newPos, this.hitCircle, obstacles)) {
      this.pos = newPos;
    } else {
      this.dir = r.nextDouble() * 360;
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
    return new AboveImage(this.renderHP(), new FromFileImage("src/zombie.png"));
  }

  WorldImage renderHP() {
    return new OverlayImage(new RectangleImage((int) (20 * (1.0 * this.hp) / this.maxHP), 5, OutlineMode.SOLID, Color.RED),
            new RectangleImage(20, 5, OutlineMode.OUTLINE, Color.BLACK));
  }
}