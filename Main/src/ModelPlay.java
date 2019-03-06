import java.awt.Point;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

/**
 * @author Tom
 */
public class ModelPlay {

  public boolean menuOpen = true;

  private PropertyChangeSupport support;

  private Note current;

  public ModelPlay() {
    this.support = new PropertyChangeSupport(this);
  }

  public void addPropertyChangeListener(PropertyChangeListener pcl) {
    this.support.addPropertyChangeListener(pcl);
  }

  public void drop() {
    current = Note.pick(new Point(200, 100), "A");
  }

  public void down(int n) {
    current.move(n);
  }
}

