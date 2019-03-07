import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * @author Tom
 */
public class PlayView extends JFrame implements PropertyChangeListener {

  private PlayModel model;

  private ViewMain.guitar guitar;
  private LanePanel jp1,jp2,jp3;

  public PlayView(PlayModel model) {
    this.model = model;
    this.model.addPropertyChangeListener(this);
    this.model.testFill(20);

    this.guitar = new ViewMain.guitar(ViewMain.w, ViewMain.h);
    this.guitar.setOpaque(false);
    this.guitar.setLayout(new GridLayout(1,3));

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
    PlayView vp = new PlayView(mp);
    PlayController1 cp = new PlayController1(mp);
  }

}
