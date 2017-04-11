import java.awt.*;
import java.util.*;

import javalib.worldimages.AlignModeY;
import javalib.worldimages.BesideAlignImage;
import javalib.worldimages.CircleImage;
import javalib.worldimages.EquilateralTriangleImage;
import javalib.worldimages.FromFileImage;
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
  Random r = new Random();
  Zombie(Posn pos, int level, double direction) {
    super(pos, level, direction);
    this.hp = 30 + this.level * 10;
    this.dmg = this.hp / 4;
    this.sightRadius = 25 + this.level * 5;
    this.attackRadius = 5 + this.level * 2;
    this.hitCircle = 10;
    this.turnCounter  = 0;
    this.moveSpeed = 4;
  }

  void move(java.util.List<Obstacle> obstacles) {
//    if(this.turnCounter <= 0) {
//      this.turnCounter = 10;
//      this.dir = r.nextDouble() * 360;
//    } else {
//      this.turnCounter -= 1;
//    }
//
//    int newX = this.moveSpeed * (int) Math.cos(Math.toRadians(this.dir));
//    int newY = this.moveSpeed * (int) Math.sin(Math.toRadians(this.dir));
//    Posn newPos = new Posn(newX, newY);
//    if((new Utils()).isPosnValid(newPos, this.hitCircle, obstacles)) {
//      this.pos = newPos;
//    }

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
