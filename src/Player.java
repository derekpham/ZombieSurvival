import com.sun.istack.internal.NotNull;

import java.awt.*;
import java.util.List;

import javalib.worldimages.AlignModeY;
import javalib.worldimages.BesideAlignImage;
import javalib.worldimages.CircleImage;
import javalib.worldimages.EquilateralTriangleImage;
import javalib.worldimages.OutlineMode;
import javalib.worldimages.Posn;
import javalib.worldimages.RotateImage;
import javalib.worldimages.TriangleImage;
import javalib.worldimages.WorldImage;

/**
 * Created by derek on 3/17/17.
 */

// to represent a player
public class Player extends Entity {
  int numAmmos;
  Player(Posn pos, int level, double direction) {
    super(pos, level, direction);
    this.hp = 100 + this.level * 25;
    this.numAmmos = 50 + this.level * 5;
    this.sightRadius = 50 + this.level * 5;
    this.attackRadius = 5 + this.level;
    this.dmg = 5 + this.level;
    this.hitCircle = 10;
    this.moveSpeed = 5;
  }

  // returns a new player, moved in this.direction
  void move(List<Obstacle> obstacles) {
    throw new UnsupportedOperationException("not allowed for player");
  }

  void inputMove(String input, List<Obstacle> obstacles) {
    int newX = this.pos.x;
    int newY = this.pos.y;
    if(input.equals("up")) {
      newX -= this.moveSpeed;
    } else if(input.equals("down")) {
      newX += this.moveSpeed;
    } else if(input.equals("left")) {
      newY -= this.moveSpeed;
    } else if(input.equals("right")) {
      newY += this.moveSpeed;
    }
    Posn newPos = new Posn(newX, newY);

    if(new Utils().isPosnValid(newPos, obstacles)) {
      this.pos = newPos;
    }
  }

  // returns a new Bullet with dx, dy accoring to player direction
  void shoot(Posn target, List<Bullet> bullets) {
    bullets.add(new Bullet(this.pos, this.level, this.dir));
  }

  WorldImage render() {
    WorldImage image = new BesideAlignImage(AlignModeY.MIDDLE,
            new CircleImage(10, OutlineMode.OUTLINE, Color.BLACK),
            new EquilateralTriangleImage(5.0, OutlineMode.OUTLINE, Color.BLACK));
    return new RotateImage(image, this.dir);
  }

  void levelUp() {
    this.level += 1;
  }
}