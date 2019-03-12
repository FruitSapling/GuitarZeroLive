import java.awt.GridLayout;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * @author Tom
 * Contributed to by:
 * Willem - Planned the Play mode MVC with Tom, and did some pair programming.
 */
public class PlayView extends JFrame implements PropertyChangeListener {

  private PlayModel model;
  private PlayController2 controller;

  private MainView.guitar guitar;
  private LanePanel jp1,jp2,jp3;
  private JPanel jpScore;
  private JLabel scoreLabel;

  public PlayView(PlayModel model, PlayController2 controller) {
    // 1 is the lead guitar on MrBrightside, should be read from notes file in future
    //ExtractNotes.playSong("MrBrightside.mid", 1, true, false);
    //ExtractNotes.playSong("MrBrightside.mid", 1,false, true);

    this.controller = controller;
    this.addKeyListener(controller);

    this.model = model;
    this.model.addPropertyChangeListener(this);
    this.model.testFill(50);

    this.guitar = new MainView.guitar(Constants.w, Constants.h);
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
    PlayView vp = new PlayView(mp,cp2);
    PlayController1 cp = new PlayController1(mp);
  }

}
