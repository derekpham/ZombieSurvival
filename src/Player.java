import java.awt.*;
import java.util.List;

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

// to represent a player
public class Player extends Entity {
  static final int ATTACK_SPEED = 5;
  int numAmmos;
  int lastTickShot;
  int maxHP;

  Player(Posn pos, int level, double direction) {
    super(pos, level, direction);
    this.hp = 50 + this.level * 25;
    this.numAmmos = 50 + this.level * 5;
    this.sightRadius = 50 + this.level * 5;
    this.attackRadius = 5 + this.level;
    this.dmg = 5 + this.level;
    this.hitCircle = 25;
    this.moveSpeed = 20;
    this.lastTickShot = -ATTACK_SPEED;
    this.maxHP = this.hp;
  }

  // returns a new player, moved in this.direction
  void move(List<Obstacle> obstacles) {
    throw new UnsupportedOperationException("not allowed for player");
  }

  void inputMove(String input, List<Obstacle> obstacles) {
    int newX = this.pos.x;
    int newY = this.pos.y;
    if (input.equals("up") || input.equals("w")) {
      newY -= this.moveSpeed;
    } else if (input.equals("down") || input.equals("s")) {
      newY += this.moveSpeed;
    } else if (input.equals("left") || input.equals("a")) {
      newX -= this.moveSpeed;
    } else if (input.equals("right") || input.equals("d")) {
      newX += this.moveSpeed;
    }
    Posn newPos = new Posn(newX, newY);

    if (new Utils().isPosnValid(newPos, this.hitCircle, obstacles)) {
      this.pos = newPos;
    }
  }

  // returns a new Bullet with dx, dy accoring to player direction
  void shoot(Posn target, List<Bullet> bullets, int curTick) {
    if (curTick > ATTACK_SPEED + this.lastTickShot && this.numAmmos > 0) {
      Double direction = Utils.getDir(this.pos, target);
      this.dir = direction;
      bullets.add(new Bullet(this.pos, this.level, this.dir));
      this.lastTickShot = curTick;
      this.numAmmos -= 1;
    }
  }

  WorldImage render() {
    return new AboveImage(this.renderHP(), new FromFileImage("src/torch.png"));
  }

  WorldImage renderHP() {
    return new OverlayImage(new RectangleImage((int) (20 * (1.0 * this.hp) / this.maxHP), 5, OutlineMode.SOLID, Color.GREEN),
            new RectangleImage(20, 5, OutlineMode.OUTLINE, Color.BLACK));
  }

  void levelUp() {
    this.level += 1;
    this.hp = 50 + this.level * 25;
    this.numAmmos = 50 + this.level * 5;
    this.sightRadius = 50 + this.level * 5;
    this.attackRadius = 5 + this.level;
    this.dmg = 5 + this.level;
    this.hitCircle = 25;
    this.moveSpeed = 20;
    this.lastTickShot = -ATTACK_SPEED;
    this.maxHP = this.hp;
  }
}