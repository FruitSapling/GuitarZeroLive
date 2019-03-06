import java.awt.Point;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * @author Tom
 */
public class ViewPlay extends JFrame implements PropertyChangeListener {

  private ModelPlay model;

  private ViewMain.guitar guitar;

  private JPanel panel;

  public ViewPlay(ModelPlay model) {
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
    if(pce.getPropertyName().equals("New Note")) {
      Note note = (Note) pce.getNewValue();
      System.out.println(note.getLocation().getY());
    } else if(pce.getPropertyName().equals("Note Move")) {
      this.guitar.add(Note.pick(new Point(200,(int)pce.getNewValue()), "A"));
      this.revalidate();
      this.repaint();
      this.pack();
      System.out.println("Moved?");
    }
  }

  public static void main(String[] args) {
    ModelPlay mp = new ModelPlay();
    ControllerPlay cp = new ControllerPlay(mp);
    ViewPlay vp = new ViewPlay(mp);
  }

}
