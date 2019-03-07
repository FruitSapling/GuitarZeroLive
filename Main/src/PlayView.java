import java.awt.GridLayout;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import javax.swing.JFrame;

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

  public PlayView(PlayModel model, PlayController2 controller) {
    ExtractNotes.playBackingTrack("MrBrightside");
    ExtractNotes.playSoloGuitar("MrBrightside");

    this.controller = controller;
    this.addKeyListener(controller);

    this.model = model;
    this.model.addPropertyChangeListener(this);
    this.model.testFill(50);

    this.guitar = new MainView.guitar(Constants.w, Constants.h);
    this.guitar.setOpaque(false);
    this.guitar.setLayout(new GridLayout(1,3));

    //Initialize panels to avoid null pointers
    ArrayList<Note> blank = new ArrayList<>();
    this.jp1 = new LanePanel(blank,0);
    this.jp2 = new LanePanel(blank,1);
    this.jp3 = new LanePanel(blank,2);

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
    this.jp1.setNotes((ArrayList<Note>)pce.getNewValue());
    this.jp2.setNotes((ArrayList<Note>)pce.getNewValue());
    this.jp3.setNotes((ArrayList<Note>)pce.getNewValue());

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
