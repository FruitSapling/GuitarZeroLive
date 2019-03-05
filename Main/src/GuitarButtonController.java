/* Primary Class Developer: Willem van Gerwen */

import java.util.ArrayList;

public class GuitarButtonController implements GuitarButtonListener {

  public final ArrayList<GuitarButtonListener> listeners = new ArrayList<GuitarButtonListener>();
  GuitarPoller guitarPoller;

  public GuitarButtonController() {
    guitarPoller = new GuitarPoller(this);
    Thread t = new Thread(new GuitarPoller(this));
    t.start();
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
  public void guitarButtonPressReceived(GuitarButtonPressedEvent e) {

  }
}
