import javalib.worldimages.FromFileImage;
import javalib.worldimages.Posn;
import javalib.worldimages.ScaleImage;
import javalib.worldimages.WorldImage;

/**
 * Created by derek on 4/12/17.
 */
public class AmmoUp extends AbstractPowerup {
  static final int ammoGain = 10;
  public AmmoUp(Posn pos) {
    super(pos);
  }

  void affect(Player player) {
    player.numAmmos += 10;
  }

  WorldImage render() {
    return new ScaleImage(new FromFileImage("src/bullet.png"), 0.5);
  }
}
