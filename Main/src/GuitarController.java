import java.util.ArrayList;

public abstract class GuitarController {

  GuitarPoller guitarPoller;

  public GuitarController() {
    guitarPoller = new GuitarPoller(this);
    Thread t = new Thread(guitarPoller);
    t.start();
  }

  public abstract void guitarStrummed(ArrayList<GuitarButton> buttonsPressed);

  public abstract void strumUp();

  public abstract void strumDown();

  public abstract void zeroPowerPressed();

  public abstract void zeroPowerWhammyOrBenderPressed(ArrayList<GuitarButton> buttonsPressed);

}
