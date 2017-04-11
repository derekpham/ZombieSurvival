import java.awt.*;

import javalib.worldimages.OutlineMode;
import javalib.worldimages.Posn;
import javalib.worldimages.RectangleImage;
import javalib.worldimages.WorldImage;

/**
 * Created by derek on 3/17/17.
 */
public class Wall extends Obstacle {
  Wall(Posn topLeft, Posn botRight) {
    super(topLeft, botRight);
  }

  public WorldImage render() {
    return new RectangleImage(this.botRight.x - this.topLeft.x,
            this.botRight.y - this.topLeft.y, OutlineMode.SOLID, Color.BLACK);
  }
}
