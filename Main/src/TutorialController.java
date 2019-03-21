import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

/**
 * A Controller class for tutorial mode This class tells the model when to progress to the next
 * step.
 *
 * @author Willem
 */
public class TutorialController extends GuitarController implements KeyListener {

  TutorialModel model;

  public TutorialController(TutorialModel model) {
    this.model = model;
  }

  @Override
  public void keyTyped(KeyEvent e) {

  }

  @Override
  public void keyPressed(KeyEvent e) {
    if (e.getKeyChar() == KeyEvent.VK_SPACE) {
      model.nextStep();
    } else if (e.getKeyChar() == 'q') {
      model.backMode();
    }
  }

  @Override
  public void keyReleased(KeyEvent e) {

  }

  @Override
  public void guitarStrummed(ArrayList<GuitarButton> buttonsPressed) {

  }

  @Override
  public void strumUp() {

  }

  @Override
  public void strumDown() {

  }

  @Override
  public void zeroPowerPressed() {
    model.nextStep();
  }

  @Override
  public void zeroPowerWhammyOrBenderPressed(ArrayList<GuitarButton> buttonsPressed) {

  }
}
