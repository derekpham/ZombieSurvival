import java.util.ArrayList;
import java.util.List;
import javalib.impworld.World;
import javalib.impworld.WorldScene;
import javalib.worldimages.Posn;

/**
 * Created by derek on 3/17/17.
 */
public class DeadWorld extends World {
  Player player;
  List<Bullet> bullets;
  List<Zombie> zombies;
  List<Obstacle> obstacles;
  int level;
  Posn topLeft;
  Posn botRight;

  // initial constructor
  DeadWorld() {
    this.player = new Player(new Posn(0, 0), 0, 0);
    this.bullets = new ArrayList<Bullet>(100);
    this.zombies = new ArrayList<Zombie>(50);
    this.obstacles = new ArrayList<Obstacle>(50);
    this.level = 0;
    this.topLeft = new Posn(0, 0);
    this.botRight = new Posn(500, 500);
  }

  public WorldScene makeScene() {
    return null;
  }

  public void onTick() {
    if (this.zombies.size() == 0) {
      this.level += 1;
      this.player.levelUp();
      //this.initZombies(); TODO
    }

    this.collisionHandle();
    this.zombieMovementHandle();
    this.bulletMovementHandle();
  }

  void collisionHandle() {
    for (int bulletIdx = 0; bulletIdx < this.zombies.size(); bulletIdx += 1) {
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
        if(obstacle.checkCollision(bullet)) {
          this.bullets.remove(idx);
          idx -= 1;
        }
      }
    }
  }

  void onKeyEvent() {

  }
}