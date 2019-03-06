import java.awt.Point;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

/**
 * @author Tom
 */
public class ModelPlay {

  private PropertyChangeSupport support;

  private Note current;

  public ModelPlay() {
    this.support = new PropertyChangeSupport(this);
    //this.drop();
  }

  public void addPropertyChangeListener(PropertyChangeListener pcl) {
    this.support.addPropertyChangeListener(pcl);
  }

  public void drop() {
    this.support.firePropertyChange(null, null,null);
    this.current = Note.pick(new Point(200, 100), "A");
  }

  public void down(int n) {
    this.current.move(n);
  }
}

