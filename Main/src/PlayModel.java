import java.awt.Point;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;

/**
 * @author Tom
 */
public class PlayModel {

  private PropertyChangeSupport support;

  private Note current;

  public PlayModel() {
    this.current = Note.pick(new Point(100, 100), "A");
    this.support = new PropertyChangeSupport(this);
    this.drop();
  }

  public void addPropertyChangeListener(PropertyChangeListener pcl) {
    this.support.addPropertyChangeListener(pcl);
  }

  public void drop() {
    this.support.firePropertyChange("New Note", null, this.current);
  }
  
  public void down(int n) {
   //this.support.firePropertyChange("Note Move", null, this.current.getLocation().getY()+n);
  }
}

