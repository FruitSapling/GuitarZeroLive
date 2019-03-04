import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;


/**
 *
 * @author Tom
 */
public class Model {

  public boolean menuOpen = true;

  private PropertyChangeSupport support;

  public Model() {
    this.support = new PropertyChangeSupport(this);
  }

  public void addPropertyChangeListener(PropertyChangeListener pcl) {
    this.support.addPropertyChangeListener(pcl);
  }

  public void drop() {
    //Something based on what note / string it is
  }

  public void showMenu() {
    menuOpen = true;
    System.out.println("Showing Menu");
    this.support.firePropertyChange(null,null,null);
  }
  public void hideMenu() {
    menuOpen = false;
    System.out.println("Hiding Menu");
    this.support.firePropertyChange(null,null,null);
  }
  public void strummed() {
    //If note is present at the y value of the pick up bar
    System.out.println("Strummed");
  }
  public void down() {

  }
}
