import java.awt.Point;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * @author Tom
 */
public class PlayView extends JFrame implements PropertyChangeListener {

  private PlayModel model;

  private ViewMain.guitar guitar;

  private JPanel panel;

  public PlayView(PlayModel model) {
    this.model = model;
    this.model.addPropertyChangeListener(this);

    this.guitar = new ViewMain.guitar(ViewMain.w, ViewMain.h);
    this.panel = new JPanel();

    this.panel.add(this.guitar);

    this.add(this.panel);
    this.pack();
    this.setVisible(true);
    this.setDefaultCloseOperation(EXIT_ON_CLOSE);
  }

  public void propertyChange(PropertyChangeEvent pce) {
    if(pce.getPropertyName().equals("Note Move")) {
      Note note = (Note) pce.getNewValue();
      System.out.println(note.getLocation().getY());
    } else if(pce.getPropertyName().equals("New Note")) {
      this.guitar.add((Note)pce.getNewValue());
      this.revalidate();
      this.repaint();
      this.pack();
      System.out.println("Moved?");
    }
  }

  public static void main(String[] args) {
    PlayModel mp = new PlayModel();
    PlayController1 cp = new PlayController1(mp);
    PlayView vp = new PlayView(mp);
  }

}
