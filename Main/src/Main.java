public class Main {
  public static void main(String[] args) {
    MainModel model = new MainModel();
    GuitarButtonController controller1 = new GuitarButtonController(model);
    MainController controller2 = new MainController(model);
    MainView mainMenu = new MainView(model, controller2, controller1);
  }
}
