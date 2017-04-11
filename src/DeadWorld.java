import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javalib.impworld.World;
import javalib.impworld.WorldScene;
import javalib.worldimages.Posn;

/**
 * Created by derek on 3/17/17.
 */
public class DeadWorld extends World {
  final static int size = 500;
  Player player;
  List<Bullet> bullets;
  List<Zombie> zombies;
  List<Obstacle> obstacles;
  int level;
  Posn topLeft;
  Posn botRight;

  // initial constructor
  DeadWorld() {
    this.player = new Player(new Posn(15, size / 2), 0, 0);
    this.bullets = new ArrayList<Bullet>();
    this.zombies = new ArrayList<Zombie>();
    this.obstacles = new ArrayList<Obstacle>();
    this.level = 0;
    this.topLeft = new Posn(0, 0);
    this.botRight = new Posn(size, size);
  }

  public WorldScene makeScene() {
    int width = this.botRight.x - this.topLeft.x;
    int height = this.botRight.y - this.topLeft.y;
    WorldScene result = new WorldScene(width, height);

    for (Obstacle obstacle : this.obstacles) {
      result.placeImageXY(obstacle.render(), obstacle.getPos().x, obstacle.getPos().y);
    }

    result.placeImageXY(this.player.render(), this.player.getPos().x, this.player.getPos().y);

    for (Bullet bullet : this.bullets) {
      result.placeImageXY(bullet.render(), bullet.getPos().x, bullet.getPos().y);
    }

    for (Zombie zombie : this.zombies) {
      result.placeImageXY(zombie.render(), zombie.getPos().x, zombie.getPos().y);
    }

    return result;
  }

  public void onTick() {
    if (this.zombies.size() == 0) {
      this.level += 1;
      this.player.levelUp();
      this.initZombies();
    }

    this.collisionHandle();
    this.zombieMovementHandle();
    this.bulletMovementHandle();
  }

  void initZombies() {
    Posn halfBoundary = new Posn(250, 0);
    //List<Zombie> zombies = new ArrayList<Zombie>(this.level * 5);
    while (zombies.size() < this.level * 5) {
      this.zombies.add(this.getRandomZombie(halfBoundary));
    }

    //return zombies;
  }

  Zombie getRandomZombie(Posn halfTopLeft) {
    Random random = new Random();
    int randX;
    int randY;
    Posn posn;
    //do {
      randX =  250;
      randY = 250;
      posn = new Posn(randX, randY);
    //}
    //while (Utils.isWithinBoundary(posn, halfTopLeft, this.botRight)) ;

    return new Zombie(posn, this.level, 180);
  }

  void collisionHandle() {
    for (int bulletIdx = 0; bulletIdx < this.bullets.size(); bulletIdx += 1) {
      Bullet bullet = this.bullets.get(bulletIdx);
      for (int zombieIdx = 0; zombieIdx < this.zombies.size(); zombieIdx += 1) {
        Zombie zombie = this.zombies.get(zombieIdx);
        if (zombie.checkCollision(bullet)) {
          zombie.takeHit(bullet);
          if (zombie.isDead()) {
            this.zombies.remove(zombieIdx);
            zombieIdx -= 1;
          }
          this.bullets.remove(bulletIdx);
          bulletIdx -= 1;
        }
      }
    }
  }

  void zombieMovementHandle() {
    for (Zombie zombie : this.zombies) {
      zombie.move(this.obstacles);
    }
  }

  void bulletMovementHandle() {
    for (int idx = 0; idx < this.bullets.size(); idx += 1) {
      Bullet bullet = this.bullets.get(idx);
      bullet.move(this.obstacles);
      for (Obstacle obstacle : this.obstacles) {
        if(obstacle.collides(bullet.pos, bullet.hitCircle)) {
          this.bullets.remove(idx);
          idx -= 1;
        }
      }
    }
  }

  public void onKeyEvent(String key) {
    if(key.equals("r")) {
      // restart world
    } else {
      this.player.inputMove(key, this.obstacles);
    }
  }

  public void onMouseClicked(Posn pos) {
    this.player.shoot(pos, this.bullets);
  }
}