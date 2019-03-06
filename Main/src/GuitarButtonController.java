/* Primary Class Developer: Willem van Gerwen */

import java.util.ArrayList;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GuitarButtonController implements GuitarButtonListener, KeyListener {

  private ModelMain model;
  public final ArrayList<GuitarButtonListener> listeners = new ArrayList<GuitarButtonListener>();
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

        } else {

        }
        break;
      case ZERO_POWER:
        //select mode
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

  @Override
  public void keyTyped(KeyEvent e) {

  }

  @Override
  public void keyPressed(KeyEvent e) {
    if(e.getKeyChar() == 'e') {
      model.cycleCarouselRight();
    }else if(e.getKeyChar() == 'd') {
      model.cycleCarouselLeft();
    }else if(e.getKeyChar() == KeyEvent.VK_ENTER) {
      model.selectMode();
    }
  }

  @Override
  public void keyReleased(KeyEvent e) {

  }

}
