import javalib.worldimages.Posn;
import javalib.worldimages.WorldImage;

import java.util.List;

// to represent an entity
public abstract class Entity {
  Posn pos;
  int level;
  int hp;
  double dir;
  int dmg;
  int hitCircle;
  int sightRadius;
  int attackRadius;
  int moveSpeed;

  Entity(Posn pos, int level, double direction) {
    this.pos = pos;
    this.level = level;
    this.dir = direction;
    this.hp = 0;
    this.dmg = 0;
    this.sightRadius = 0;
    this.attackRadius = 0;
    this.hitCircle = 0;
  }

  abstract void move(List<Obstacle> obstacles);

  abstract WorldImage render();


}