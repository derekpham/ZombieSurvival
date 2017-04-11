import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javalib.impworld.World;
import javalib.impworld.WorldScene;
import javalib.worldimages.OutlineMode;
import javalib.worldimages.Posn;
import javalib.worldimages.RectangleImage;

/**
 * Created by derek on 3/17/17.
 */
public class DeadWorld extends World {
  final static int WIDTH = 1300;
  final static int HEIGHT = 700;
  Player player;
  List<Bullet> bullets;
  List<Zombie> zombies;
  List<Obstacle> obstacles;
  int level;
  Posn topLeft;
  Posn botRight;

  // initial constructor
  DeadWorld() {
    this.player = new Player(new Posn(20, HEIGHT / 2), 0, 0);
    this.bullets = new ArrayList<Bullet>();
    this.zombies = new ArrayList<Zombie>();
    this.obstacles = new ArrayList<Obstacle>();
    this.level = 0;
    this.topLeft = new Posn(0, 0);
    this.botRight = new Posn(WIDTH, HEIGHT);
    this.obstacles.add(new Room(this.topLeft, this.botRight));
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
    Posn halfBoundary = new Posn(WIDTH / 2, 0);
    //List<Zombie> zombies = new ArrayList<Zombie>(this.level * 5);
    while (this.zombies.size() < this.level * 5) {
      this.zombies.add(this.getRandomZombie(halfBoundary, this.botRight));
    }
  }

  Zombie getRandomZombie(Posn topLeft, Posn botRight) {
    Random random = new Random();
    int x = random.nextInt(botRight.x - topLeft.x) + topLeft.x;
    int y = random.nextInt(botRight.y - topLeft.y) + topLeft.y;
    Posn pos = new Posn(x, y);
    return new Zombie(pos, this.level, 180);
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
      zombie.move(this.player, this.obstacles);
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

  public WorldScene makeScene() {
    WorldScene result = new WorldScene(WIDTH, HEIGHT);
    result.placeImageXY(new RectangleImage(WIDTH, HEIGHT, OutlineMode.SOLID, Color.GRAY),
            WIDTH / 2, HEIGHT / 2);

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

}