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
import javax.swing.OverlayLayout;

/**
 * @author Tom
 */
public class PlayView extends JFrame implements PropertyChangeListener {

  private PlayModel model;

  private ViewMain.guitar guitar;
  private JPanel jp1,jp2,jp3;

  public PlayView(PlayModel model) {
    this.model = model;
    this.model.addPropertyChangeListener(this);
    this.model.testFill(1000);

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
    this.guitar.remove(this.jp1);
    this.guitar.remove(this.jp2);
    this.guitar.remove(this.jp3);

    this.jp1 = new LanePanel((ArrayList<Note>)pce.getNewValue(),0);
    this.jp2 = new LanePanel((ArrayList<Note>)pce.getNewValue(),1);
    this.jp3 = new LanePanel((ArrayList<Note>)pce.getNewValue(),2);

    this.guitar.add(jp1);
    this.guitar.add(jp2);
    this.guitar.add(jp3);
    this.revalidate();
    this.repaint();
    this.pack();
  }

  public static void main(String[] args) {
    PlayModel mp = new PlayModel();
    PlayView vp = new PlayView(mp);
    PlayController1 cp = new PlayController1(mp);
  }

  public class LanePanel extends JPanel {
    private ArrayList<Note> notes;
    private BufferedImage white;
    private BufferedImage black;
    private int lane;
    public LanePanel(ArrayList<Note> notes, int lane) {
      try{
        this.white = ImageIO.read(new FileInputStream("Main/src/resources/pick.png"));
        this.black = ImageIO.read(new FileInputStream("Main/src/resources/pick1.png"));
      }catch(IOException e) {
        System.out.println(e.getMessage());
        System.exit(1);
      }
      this.lane = lane;
      this.notes = notes;
      this.setOpaque(false);
    }
    public void paintComponent(Graphics g) {
      for(Note n : this.notes) {
        if(n.getLane() == lane) {
          if(n.getColour() == 1) {
            g.drawImage(white, 100, n.getY(), null);
          } else {
            g.drawImage(black, 100, n.getY(), null);
          }
        }
      }
    }
  }

}
