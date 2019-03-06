import java.util.Timer;
import java.util.TimerTask;

/**
 * @author Tom
 */
public class ControllerPlay {

  private ModelPlay model;
  private Timer timer;

  public ControllerPlay(ModelPlay model) {
    this.model = model;
    this.timer = new Timer();

    timer.schedule(new TimerTask() {
      public void run() {
        model.down(1);
      }
    }, 0 , 1000);
  }

}
