import java.util.Random;

import javalib.worldimages.FromFileImage;
import javalib.worldimages.Posn;
import javalib.worldimages.WorldImage;

/**
 * Created by derek on 4/12/17.
 */
public class HealthUp extends AbstractPowerup {
  int heal;
  int type;

  public HealthUp(Posn pos, int heal) {
    super(pos);
    this.heal = heal;
    this.type = new Random().nextInt(5);
  }

  @Override
  void affect(Player player) {
    int newHp = player.hp + this.heal;
    if (newHp > player.maxHP) {
      player.hp = player.maxHP;
    } else {
      player.hp = newHp;
    }
  }

  @Override
  WorldImage render() {
    return new FromFileImage("src/heal" + this.type + ".png");
  }
}