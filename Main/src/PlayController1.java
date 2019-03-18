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

    timer.scheduleAtFixedRate(new TimerTask() {
      public void run() {
//        long time = System.currentTimeMillis();
//        System.out.println(time);
        model.fireNotes();
        model.move();
        //model.flip();
      }
    }, 0 , 1);
  }

}
