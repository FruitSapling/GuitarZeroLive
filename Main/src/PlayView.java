import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.OverlayLayout;

/**
 * @author Tom
 */
public class PlayView extends JFrame implements PropertyChangeListener {

  private PlayModel model;

  private ViewMain.guitar guitar;

  public PlayView(PlayModel model) {
    this.model = model;
    this.model.addPropertyChangeListener(this);

    this.guitar = new ViewMain.guitar(ViewMain.w, ViewMain.h);
    this.guitar.setOpaque(false);

    this.add(this.guitar);
    this.pack();
    this.setVisible(true);
    this.setLocationRelativeTo(null);
    this.setDefaultCloseOperation(EXIT_ON_CLOSE);
  }

  public void propertyChange(PropertyChangeEvent pce) {
    if(pce.getPropertyName().equals("Note Move")) {
      Note note = (Note) pce.getNewValue();
      System.out.println(note.getLocation().getY());
    } else if(pce.getPropertyName().equals("New Note")) {
      Note note = (Note)pce.getNewValue();
      note.setLocation(note.getLocation());
      this.guitar.add(note);
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
