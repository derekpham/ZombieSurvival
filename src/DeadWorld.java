import java.io.*;
import sun.audio.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import javalib.impworld.World;
import javalib.impworld.WorldScene;
import javalib.worldimages.FromFileImage;
import javalib.worldimages.OutlineMode;
import javalib.worldimages.Posn;
import javalib.worldimages.RectangleImage;
import javalib.worldimages.TextImage;

/**
 * Created by derek on 3/17/17.
 */
public class DeadWorld extends World {
  final static int WIDTH = 1900;
  final static int HEIGHT = 1000;
  final static int ZOMBIES_MULTIPLIER = 5;
  Player player;
  List<Bullet> bullets;
  List<Zombie> zombies;
  List<Obstacle> obstacles;
  List<AbstractPowerup> powerups;
  int level;
  Posn topLeft;
  Posn botRight;
  boolean hasLost;
  int tickPast;
  List<Boss> bosses;
  List<Bullet> enemiesBullets;

  // initial constructor
  DeadWorld() {
    this.player = new Player(new Posn(20, HEIGHT / 2), 0, 0);
    this.bullets = new ArrayList<Bullet>();
    this.zombies = new ArrayList<Zombie>();
    this.obstacles = new ArrayList<Obstacle>();
    this.powerups = new ArrayList<AbstractPowerup>();
    this.level = 0;
    this.topLeft = new Posn(0, 0);
    this.botRight = new Posn(WIDTH, HEIGHT);
    this.obstacles.add(new Room(this.topLeft, this.botRight));
    this.initWalls(0);
    this.initPowerUp(1);
    this.hasLost = false;
    this.tickPast = 0;
    this.bosses = new ArrayList<Boss>();
    this.enemiesBullets = new ArrayList<Bullet>();


  }

  public void onTick() {
    if (!this.hasLost) {
      if (this.zombies.size() == 0 && this.bosses.size() == 0) {
        this.levelUp();
      }

      if (new Random().nextDouble() < 0.005) {
        this.initPowerUp(1);
      }

      for (Boss boss : this.bosses) {
        boss.attack(this.enemiesBullets);
      }

      this.collisionsHandleZombiesBullets();
      this.collisionsHandleZombiesPlayer();
      this.collisionsHandlePlayerEnemies();
      this.collisionsHandleBossesBullets();
      this.zombieMovementHandle();
      this.bulletMovementHandle();
      this.powerUpHandle();
      this.hasLost = this.player.hp <= 0;
      tickPast += 1;
    }
  }

  void levelUp() {
    this.level += 1;
    this.player.levelUp();
    this.player.pos = new Posn(20, HEIGHT / 2);
    this.initZombies();
    this.initPowerUp(2);
    this.bosses = new ArrayList<Boss>();
    if (this.level == 5) {
      this.bosses.add(new Boss(new Posn((int)(WIDTH * 0.90), HEIGHT / 2),
              5, 0, "Carnegie", "src/carnegie.jpg"));

    } else if (this.level == 10) {
      this.bosses.add(new Boss(new Posn((int)(WIDTH * 0.90), HEIGHT / 2),
              10, 0, "Alien", "src/alien.jpg"));
    } else if (this.level > 14) {
      this.bosses.add(new Boss(new Posn((int)(WIDTH * 0.90), HEIGHT / 2),
              this.level, 0, "Death", "src/death.jpg"));
    }
  }

  void initZombies() {
    Posn halfBoundary = new Posn(WIDTH / 2, 0);
    this.zombies = new ArrayList<Zombie>();
    while (this.zombies.size() < this.level * ZOMBIES_MULTIPLIER) {
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

  void initWalls(int toAdd) {
    Random r = new Random();
    int thickness = 50;
    int length = 100;
    for (int i = 0; i < toAdd; i += 1) {
      length = r.nextInt(100) + 100;
      this.obstacles.add(this.getRandomWall(thickness, length, r.nextBoolean()));
    }
  }

  Wall getRandomWall(int thickness, int length, boolean horizontal) {
    Random r = new Random();
    int r1 = r.nextInt(WIDTH - length - thickness);
    int r2 = r.nextInt(HEIGHT - length - thickness);
    Posn tL;
    Posn bR;
    if (horizontal) {
      tL = new Posn(r1, r2);
      bR = new Posn(r1 + length, r2 + thickness);
    } else {
      tL = new Posn(r1, r2);
      bR = new Posn(r1 + thickness, r2 + length);
    }
    return new Wall(tL, bR);
  }

  void initPowerUp(int toAdd) {
    for (int i = 0; i < toAdd; i += 1) {
      this.powerups.add(this.getRandomPowerUp());
    }
  }

  AbstractPowerup getRandomPowerUp() {
    Random r = new Random();
    Posn randPos = new Posn(r.nextInt(9*WIDTH/10), r.nextInt(HEIGHT));
    if (r.nextBoolean()) {
      return new AmmoUp(randPos);
    } else {
      return new HealthUp(randPos);
    }
  }

  void collisionsHandleZombiesBullets() {
    for (int bulletIdx = 0; bulletIdx < this.bullets.size(); bulletIdx += 1) {
      if (bulletIdx <= 0) {
        bulletIdx = 0;
      }
      Bullet bullet = this.bullets.get(bulletIdx);
      for (int zombieIdx = 0; zombieIdx < this.zombies.size(); zombieIdx += 1) {
        Zombie zombie = this.zombies.get(zombieIdx);
        if (zombie.checkCollision(bullet)) {
          zombie.takeHit(bullet);
          if (zombie.isDead()) {
            this.zombies.remove(zombieIdx);
            zombieIdx -= 1;
          }
          if (bulletIdx >= 0 && bulletIdx < this.bullets.size()) {
            this.bullets.remove(bulletIdx);
            bulletIdx -= 1;
          }
        }
      }
    }
  }

  void collisionsHandleZombiesPlayer() {
    for (int idx = 0; idx < this.zombies.size(); idx += 1) {
      Zombie zombie = this.zombies.get(idx);
      if (Utils.checkCollision(zombie.getPos(), zombie.attackRadius,
              this.player.getPos(), this.player.hitCircle)) {
        zombie.hit(this.player);
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
      if (idx <= 0){
        idx = 0;
      }
      Bullet bullet = this.bullets.get(idx);
      bullet.move(this.obstacles);
      for (Obstacle obstacle : this.obstacles) {
        if (obstacle.collides(bullet.pos, bullet.hitCircle)) {
          this.bullets.remove(idx);
          idx -= 1;
        }
      }
    }

    for (int idx = 0; idx < this.enemiesBullets.size(); idx += 1) {
      if (idx <= 0){
        idx = 0;
      }
      Bullet bullet = this.enemiesBullets.get(idx);
      bullet.move(this.obstacles);
    }
  }

  void powerUpHandle() {
    for (int idx = 0; idx < this.powerups.size(); idx += 1) {
      AbstractPowerup cur = this.powerups.get(idx);
      if(cur.checkCollision(this.player)) {
        cur.affect(this.player);
        this.powerups.remove(idx);
        idx -= 1;
      }
    }
  }

  public void onKeyEvent(String key) {
    if (key.equals("r")) {

    } else if (key.equals("u")) {
      this.levelUp();
    }
    else {
      this.player.inputMove(key, this.obstacles);
    }
  }

  void collisionsHandlePlayerEnemies() {
    for (int idx = 0; idx < this.enemiesBullets.size(); idx += 1) {
      Bullet bullet = this.enemiesBullets.get(idx);
      if (Utils.checkCollision(bullet.getPos(), bullet.attackRadius, this.player.getPos(), this.player.hitCircle)) {
        this.player.hp -= bullet.dmg;
        if (idx >= 0 && idx < this.enemiesBullets.size()) {
          this.enemiesBullets.remove(idx);
        }
      }
    }
  }

  void collisionsHandleBossesBullets() {
    for (int bulletIdx = 0; bulletIdx < this.bullets.size(); bulletIdx += 1) {
      if (bulletIdx < 0) {
        bulletIdx = 0;
      }
      Bullet bullet = this.bullets.get(bulletIdx);
      for (int bossIdx = 0; bossIdx < this.bosses.size(); bossIdx += 1) {
        Boss boss = this.bosses.get(bossIdx);
        if (Utils.checkCollision(boss.getPos(), boss.hitCircle, bullet.getPos(), bullet.attackRadius)) {
          boss.hp -= bullet.dmg;
          if (boss.hp < 0) {
            this.bosses.remove(bossIdx);
            bossIdx -= 1;
          }
          if (bulletIdx >= 0 && bulletIdx < this.bullets.size()) {
            this.bullets.remove(bulletIdx);
            bulletIdx -= 1;
          }
        }
      }
    }
  }

  void playBGMusic() {

  }

  public void onMouseClicked(Posn pos) {
    this.player.shoot(pos, this.bullets, this.tickPast);
  }

  public WorldScene makeScene() {
    WorldScene result = new WorldScene(WIDTH, HEIGHT);
    if (this.hasLost) {
      result.placeImageXY(new TextImage("YOU LOST!!!", 30, Color.BLUE),
              WIDTH / 2, HEIGHT / 2);
      return result;
    }
    result.placeImageXY(new RectangleImage(WIDTH, HEIGHT, OutlineMode.SOLID, Color.GRAY),
            WIDTH / 2, HEIGHT / 2);

    for (AbstractPowerup powerup : this.powerups) {
      result.placeImageXY(powerup.render(), powerup.getPos().x, powerup.getPos().y);
    }

    result.placeImageXY(this.player.render(), this.player.getPos().x, this.player.getPos().y);

    for (Bullet bullet : this.bullets) {
      result.placeImageXY(bullet.render(), bullet.getPos().x, bullet.getPos().y);
    }

    for (Zombie zombie : this.zombies) {
      result.placeImageXY(zombie.render(), zombie.getPos().x, zombie.getPos().y);
    }

    for (Boss boss : this.bosses) {
      result.placeImageXY(boss.render(), boss.getPos().x, boss.getPos().y);
    }

    for (Bullet bullet : this.enemiesBullets) {
      result.placeImageXY(bullet.render(), bullet.getPos().x, bullet.getPos().y);
    }

    result.placeImageXY(new FromFileImage("src/bullet.png"),
            (int)(WIDTH * 0.93), (int)(HEIGHT * 0.95));
    result.placeImageXY(new TextImage(this.player.numAmmos + "", 20, Color.BLACK),
            (int)(WIDTH * 0.97), (int)(HEIGHT * 0.95));
    result.placeImageXY(new TextImage("Day: " + this.level, 30, Color.BLACK),
            (int)(WIDTH * 0.93), 30);

    return result;
  }
}