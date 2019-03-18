import java.util.Timer;
import java.util.TimerTask;

/**
 * @author Tom
 */
public class PlayController1 {

  private PlayModel model;
  private Timer timer;

  public PlayController1(PlayModel model) {
    this.model = model;
    this.timer = new Timer();

    timer.schedule(new TimerTask() {
      public void run() {
        model.fireNotes();
        model.move();
        //model.flip();
      }
    }, 0 , 1);
  }

}
