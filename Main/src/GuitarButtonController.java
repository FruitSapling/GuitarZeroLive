import java.util.ArrayList;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * A 'controller' in the MVC design pattern, manipulating the model when
 * sending guitar button presses and key presses.
 *
 * @author Willem
 */
public class GuitarButtonController implements GuitarButtonListener, KeyListener {

  private ModelMain model;
  public final ArrayList<GuitarButtonListener> listeners = new ArrayList<>();
  GuitarPoller guitarPoller;

  public GuitarButtonController(ModelMain model) {
    this.model = model;
    guitarPoller = new GuitarPoller(this);
    Thread t = new Thread(new GuitarPoller(this));
    t.start();
  }

  @Override
  public void guitarButtonPressReceived(GuitarButtonPressedEvent e) {
    switch(e.getGuitarButton()) {
      case STRUM:
        if (e.getValue() == 1.0) {
          cycleCarouselRight();
        } else {
          cycleCarouselLeft();
        }
        break;
      case ZERO_POWER:
        selectMode();
        break;
    }
  }


  public synchronized void addListener(GuitarButtonListener listener){
    listeners.add(listener);
  }

  public synchronized void removeListener(GuitarButtonListener listener){
    listeners.remove(listener);
  }

  public void fireGuitarButtonPressedEvent(GuitarButton btn) {
    GuitarButtonPressedEvent e = new GuitarButtonPressedEvent(this, btn);
    for (GuitarButtonListener listener: listeners) {
      listener.guitarButtonPressReceived(e);
    }
  }

  public void fireGuitarButtonPressedEvent(GuitarButton btn, float value) {
    GuitarButtonPressedEvent e = new GuitarButtonPressedEvent(this, btn, value);
    for (GuitarButtonListener listener: listeners) {
      listener.guitarButtonPressReceived(e);
    }
  }

  public void cycleCarouselRight() {
    model.cycleCarouselRight();
  }

  public void cycleCarouselLeft() {
    model.cycleCarouselRight();
  }

  public void selectMode() {
    model.selectMode();
  }

  @Override
  public void keyTyped(KeyEvent e) {

  }

  @Override
  public void keyPressed(KeyEvent e) {
    if(e.getKeyChar() == 'e') {
      cycleCarouselRight();
    }else if(e.getKeyChar() == 'd') {
      cycleCarouselLeft();
    }else if(e.getKeyChar() == KeyEvent.VK_ENTER) {
      selectMode();
    }
  }

  @Override
  public void keyReleased(KeyEvent e) {

  }

}
