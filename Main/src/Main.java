public class Main {
  public static void main(String[] args) {
    GuitarButtonController controller1 = new GuitarButtonController();
    ModelMain model = new ModelMain();
    ControllerMain controller2 = new ControllerMain(model);
    ViewMain mainMenu = new ViewMain(model, controller2, controller1);
  }
}
