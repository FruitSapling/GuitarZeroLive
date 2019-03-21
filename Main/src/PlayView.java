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
  private PlayController2 controller2;

  private MainView.Guitar guitar;
  private LanePanel1 jp1;
  private LanePanel2 jp2;
  private LanePanel3 jp3;

  public PlayView(PlayModel model, PlayController2 controller2, PlayGuitarController guitarController) {
    this.controller2 = controller2;
    this.addKeyListener(controller2);

    this.model = model;
    this.model.addPropertyChangeListener(this);
    this.model.generateNotes();

    this.guitar = new MainView.Guitar(Constants.w, Constants.h);
    this.guitar.setOpaque(false);
    this.guitar.setLayout(new GridLayout(1,3));

    //Initialize panels to avoid null pointers
    CopyOnWriteArrayList<Note> blank = new CopyOnWriteArrayList<>();
    this.jp1 = new LanePanel1(blank, model.getScore().getScore(), model.getScore().getInGameCurrency(), model.getScore().getCurrentMultiplier());
    this.jp2 = new LanePanel2(blank, model.getScore().getCurrentStreak());
    this.jp3 = new LanePanel3(blank, model.isZeroPowerMode());

    this.guitar.add(jp1);
    this.guitar.add(jp2);
    this.guitar.add(jp3);

    this.add(this.guitar);
    this.pack();
    this.setVisible(true);
    this.setLocationRelativeTo(null);
    this.setDefaultCloseOperation(EXIT_ON_CLOSE);

    PlaySong.playMidi("HeyBrother.mid", 4, false, false);
  }

  public void propertyChange(PropertyChangeEvent pce) {
    this.jp1.setNotes((CopyOnWriteArrayList<Note>)pce.getNewValue());
    this.jp2.setNotes((CopyOnWriteArrayList<Note>)pce.getNewValue());
    this.jp3.setNotes((CopyOnWriteArrayList<Note>)pce.getNewValue());

    this.jp1.setScore(model.getScore().getScore());
    this.jp1.setCurrency(model.getScore().getInGameCurrency());
    this.jp1.setMult(model.getScore().getCurrentMultiplier());
    this.jp2.setStreak(model.getScore().getCurrentStreak());
    this.jp3.setZeroPowerMode(model.isZeroPowerMode());

    this.repaint();
  }

  public static void main(String[] args) {
    PlayModel mp = new PlayModel();
    PlayController2 cp2 = new PlayController2(mp);
    PlayGuitarController gp = new PlayGuitarController(mp);
    PlayView vp = new PlayView(mp,cp2,gp);
    PlayController1 cp = new PlayController1(mp);
  }

}
