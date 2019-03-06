import java.awt.Point;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

/**
 * @author Tom
 */
public class ModelPlay {

  private PropertyChangeSupport support;

  public ModelPlay() {
    this.support = new PropertyChangeSupport(this);
    this.drop();
  }

  public void addPropertyChangeListener(PropertyChangeListener pcl) {
    this.support.addPropertyChangeListener(pcl);
  }

  public void drop() {
    this.support.firePropertyChange("New Note", null, 0);
  }

  public void down(int n) {
    this.support.firePropertyChange("Note Move", null, 100);
  }
}

