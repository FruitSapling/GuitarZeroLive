public class Main {
  public static void main(String[] args) {
    Thread t = new Thread(new GuitarPollingThread());
    t.start();
    ViewMain mainMenu = new ViewMain();
  }
}
