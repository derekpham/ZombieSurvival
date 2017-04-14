public class Main {
  public static void main(String[] args) {
    DeadWorld deadWorld = new DeadWorld();
    double tickRate = 0.05;
    deadWorld.bigBang(DeadWorld.WIDTH, DeadWorld.HEIGHT, tickRate);
    CPSound cpSound = new CPSound();
    cpSound.run();

  }
}

// NOTE: we wanna thank this site: https://icons8.com/license/