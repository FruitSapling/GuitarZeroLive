public class Main {
  public static void main(String[] args) {
    MainModel model = new MainModel();
    MainGuitarController controller1 = new MainGuitarController(model);
    MainController controller2 = new MainController(model);
    MainView mainMenu = new MainView(model, controller2, controller1);
  }
}
