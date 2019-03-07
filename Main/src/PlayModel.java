import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.Random;

/**
 * @author Tom
 * Contributed to by:
 * Willem - Planned the PlayModel structure (following MVC) with Tom, and did some pair programming.
 */
public class PlayModel {

  private PropertyChangeSupport support;

  private ArrayList<Note> current;

  public PlayModel() {
    this.current = new ArrayList<>();
    this.support = new PropertyChangeSupport(this);
  }

  public void addPropertyChangeListener(PropertyChangeListener pcl) {
    this.support.addPropertyChangeListener(pcl);
  }

  public void testFill(int n) {
    Random rand = new Random();
    for(int i = 0; i < n; i++) {
      current.add(new Note(rand.nextInt(3), rand.nextInt(500), rand.nextInt(2)));
    }
  }

  public void move() {
    for(Note n : this.current) {
      n.move();
    }
  }

  public void fireNotes() {
    support.firePropertyChange("Notes", null, this.current);
  }

  public void strum() {
    for(Note n : this.current) {
      if(n.getY() > 600 && n.getY() < 650) {
        this.current.remove(n);
      }
    }
  }
  public void flip() {
    for(Note n : this.current) {
      if(n.getY() == ViewMain.h) {
        n.setY(0);
      }
    }
  }

}

