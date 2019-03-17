import java.util.ArrayList;

public abstract class GuitarController {

  GuitarPoller guitarPoller;

  public GuitarController() {
    guitarPoller = new GuitarPoller(this);
    Thread t = new Thread(new GuitarPoller(this));
    t.start();
  }

  public abstract void guitarStrummed(ArrayList<GuitarButton> buttonsPressed);

}
