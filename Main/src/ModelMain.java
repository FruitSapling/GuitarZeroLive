import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class ModelMain {

  public boolean menuOpen = true;

  private PropertyChangeSupport support;

  public ModelMain() {
    this.support = new PropertyChangeSupport(this);
  }

  public void addPropertyChangeListener(PropertyChangeListener pcl) {
    this.support.addPropertyChangeListener(pcl);
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
    System.out.println("Strum Me Daddy");
  }
  public void down() {

  }
}

