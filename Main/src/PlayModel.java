import java.awt.Point;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.Random;

/**
 * @author Tom
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

  //Test function to randomly generate some notes
  public void testFill(int n) {
    Random rand = new Random();
    for(int i = 0; i < n; i++) {
      current.add(new Note(rand.nextInt(3), rand.nextInt(500), rand.nextInt(2)));
    }
  }

  //Moves the notes down the screen
  public void move() {
    for(Note n : this.current) {
      n.move();
    }
  }

  //Refreshes the notes
  public void fireNotes() {
    support.firePropertyChange("Notes", null, this.current);
  }

  //Removes the note if in range of pickup
  public void strum() {
    for(Note n : this.current) {
      if(n.getY() > 600 && n.getY() < 650) {
        this.current.remove(n);
      }
    }
  }

  //Test function to loop the notes back to y=0
  public void flip() {
    for(Note n : this.current) {
      if(n.getY() == Constants.h) {
        n.setY(0);
      }
    }
  }

}

