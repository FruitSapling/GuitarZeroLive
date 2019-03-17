import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class TutorialModel {

  private PropertyChangeSupport support;

  public TutorialModel() {
    this.support = new PropertyChangeSupport(this);
  }

  public void addPropertyChangeListener(PropertyChangeListener pcl) {
    this.support.addPropertyChangeListener(pcl);
  }

}
