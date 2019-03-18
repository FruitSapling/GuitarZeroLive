import java.awt.GridLayout;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.concurrent.CopyOnWriteArrayList;
import javax.swing.JFrame;

/**
 * @author Tom
 * Contributed to by:
 * Willem - Planned the Play mode MVC with Tom, and did some pair programming.
 */
public class PlayView extends JFrame implements PropertyChangeListener {

  private PlayModel model;
  private PlayController2 controller;

  private MainView.Guitar guitar;
  private LanePanel jp1,jp2,jp3;

  public PlayView(PlayModel model, PlayController2 controller, PlayGuitarController guitarController) {
    // 1 is the lead Guitar on MrBrightside, should be read from notes file in future

    this.controller = controller;
    this.addKeyListener(controller);

    this.model = model;
    this.model.addPropertyChangeListener(this);
    this.model.testFill(50);

    this.guitar = new MainView.Guitar(Constants.w, Constants.h);
    this.guitar.setOpaque(false);
    this.guitar.setLayout(new GridLayout(1,3));

    //Initialize panels to avoid null pointers
    CopyOnWriteArrayList<Note> blank = new CopyOnWriteArrayList<>();
    this.jp1 = new LanePanel(blank, 0, model.getScore().getScore());
    this.jp2 = new LanePanel(blank,1, model.getScore().getCurrentStreak());
    this.jp3 = new LanePanel(blank,2, model.getScore().getInGameCurrency());

    this.guitar.add(jp1);
    this.guitar.add(jp2);
    this.guitar.add(jp3);

    this.add(this.guitar);
    this.pack();
    this.setVisible(true);
    this.setLocationRelativeTo(null);
    this.setDefaultCloseOperation(EXIT_ON_CLOSE);

    //ExtractNotes.playSong("AllTheSmallThings.mid", 5,false, true);
    ExtractNotes.playSong("SmokeOnTheWater.mid", 3, false, true);
  }

  public void propertyChange(PropertyChangeEvent pce) {
    this.jp1.setNotes((CopyOnWriteArrayList<Note>)pce.getNewValue());
    this.jp2.setNotes((CopyOnWriteArrayList<Note>)pce.getNewValue());
    this.jp3.setNotes((CopyOnWriteArrayList<Note>)pce.getNewValue());

    this.jp1.setScore(model.getScore().getScore());
    this.jp2.setScore(model.getScore().getCurrentStreak());
    this.jp3.setScore(model.getScore().getInGameCurrency());

    this.revalidate();
    this.repaint();
    this.pack();
  }

  public static void main(String[] args) {
    PlayModel mp = new PlayModel();
    PlayController2 cp2 = new PlayController2(mp);
    PlayGuitarController gp = new PlayGuitarController(mp);
    PlayView vp = new PlayView(mp,cp2,gp);
    PlayController1 cp = new PlayController1(mp);
  }

}
