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

    this.guitar = new ViewMain.guitar(ViewMain.w, ViewMain.h);
    this.panel = new JPanel();

    this.panel.add(this.guitar);

    this.add(this.panel);
    this.pack();
    this.setVisible(true);
    this.setDefaultCloseOperation(EXIT_ON_CLOSE);
  }

  public void propertyChange(PropertyChangeEvent pce) {
    System.out.println("Property Change");
    if(pce.getPropertyName().equals("note")) {
      System.out.println("Note Dropped");
    }
  }

  public static void main(String[] args) {
    ModelPlay mp = new ModelPlay();
    ControllerPlay cp = new ControllerPlay(mp);
    ViewPlay vp = new ViewPlay(mp);
  }

}
