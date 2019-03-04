import javafx.scene.control.Control;

/**
 *
 * @author Tom
 */
public class Main {
  public static void main(String[] args) {
    GuitarButtonController controller1 = new GuitarButtonController();
    Model model = new Model();
    Controller2 controller2 = new Controller2(model);
    ViewMain mainMenu = new ViewMain(model, controller2, controller1);
  }
}
