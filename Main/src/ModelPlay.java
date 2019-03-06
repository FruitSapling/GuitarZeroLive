import java.awt.Point;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;

/**
 * @author Tom
 */
public class ModelPlay {

  private PropertyChangeSupport support;

  private ArrayList<Note> current;

  public ModelPlay() {
    for(int i = 0; i < 10; i++) {
      this.current.add(Note.pick(new Point(200, 100), "A"));
    }
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
    this.support.firePropertyChange("Note Move", null, this.current.getLocation().getY()+n);
  }
}

