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
  }

  public void startMovingNotes() {
    timer.scheduleAtFixedRate(new TimerTask() {
      public void run() {
        model.fireNotes();
        model.move();
      }
    }, 0 , 5);
  };

}
