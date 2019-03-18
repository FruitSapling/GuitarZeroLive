import java.util.ArrayList;

public class PlayGuitarController extends GuitarController {

  PlayModel model;

  public PlayGuitarController(PlayModel model) {
    this.model = model;
  }

  @Override
  public void guitarStrummed(ArrayList<GuitarButton> buttonsPressed) {
    // Tell the model which buttons were pressed when the Guitar was strummed.
    System.out.println("Buttons pressed: ");
    model.guitarStrummed(buttonsPressed);
  }

  @Override
  public void strumUp() {
    System.out.println("STRUM UP");
  }

  @Override
  public void strumDown() {
    System.out.println("STRUM DOWN");
  }

  @Override
  public void zeroPowerPressed() {
    System.out.println("ZERO POWER");
  }

}
