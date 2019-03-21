import java.util.ArrayList;

/**
 * A Controller class used for play mode.
 * This class tells the model when the guitar is strummed.
 *
 * @author Willem
 */
public class PlayGuitarController extends GuitarController {

  PlayModel model;

  public PlayGuitarController(PlayModel model) {
    this.model = model;
  }

  @Override
  public void guitarStrummed(ArrayList<GuitarButton> buttonsPressed) {
    // Tell the model which buttons were pressed when the Guitar was strummed.
    model.guitarStrummed(buttonsPressed);
  }

  @Override
  public void strumUp() { }

  @Override
  public void strumDown() { }

  @Override
  public void zeroPowerPressed() { }

  @Override
  public void zeroPowerWhammyOrBenderPressed() {

  }

}
