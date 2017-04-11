public class Main {

  public static void main(String[] args) {
    DeadWorld deadWorld = new DeadWorld();
    double tickRate = 0.1;
    deadWorld.bigBang(DeadWorld.WIDTH, DeadWorld.HEIGHT, tickRate);
  }
}
