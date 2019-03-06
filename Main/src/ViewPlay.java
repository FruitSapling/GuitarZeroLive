import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class ViewPlay extends JFrame implements PropertyChangeListener {

  private ViewMain.guitar guitar;

  private JPanel panel;

  public ViewPlay() {
    guitar = new ViewMain.guitar(ViewMain.w, ViewMain.h);
    panel = new JPanel();

    panel.add(guitar);

    this.add(panel);
    this.pack();
    this.setVisible(true);
    this.setDefaultCloseOperation(EXIT_ON_CLOSE);
  }

  public void propertyChange(PropertyChangeEvent pce) {

  }

  public static void main(String[] args) {
    ViewPlay vp = new ViewPlay();
  }

}
