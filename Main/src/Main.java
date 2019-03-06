public class Main {
  public static void main(String[] args) {
    ModelMain model = new ModelMain();
    GuitarButtonController controller1 = new GuitarButtonController(model);
    ControllerMain controller2 = new ControllerMain(model);
    ViewMain mainMenu = new ViewMain(model, controller2, controller1);
  }
}
