import java.util.ArrayList;

public class PlayGuitarController extends GuitarController {

  PlayModel model;

  public PlayGuitarController(PlayModel model) {
    this.model = model;
  }

  @Override
  public void guitarStrummed(ArrayList<GuitarButton> buttonsPressed) {
    // Tell the model which buttons were pressed when the guitar was strummed.
    model.guitarStrummed(buttonsPressed);
  }

}
