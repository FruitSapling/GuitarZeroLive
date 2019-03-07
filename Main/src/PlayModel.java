import java.awt.Point;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
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
    //Random rand = new Random();
    //for(int i = 0; i < n; i++) {
      //current.add(new Note(rand.nextInt(3), rand.nextInt(500), rand.nextInt(2)));
    //}
    for(int[] arr : genNotes("Main/src/MrBrightside.midnotes")) {
      current.add(new Note(arr[0], 0-arr[2]+550, arr[1]));
    }
  }

  public ArrayList<int[]> genNotes(String file) {
    try {
      ArrayList<int[]> results = new ArrayList<>();
      File fileObj = new File(file);
      FileReader fw = new FileReader(fileObj);
      BufferedReader bw = new BufferedReader(fw);

      String line;
      while((line = bw.readLine()) != null) {
        if(line.equals("1")) {
          continue;
        }
        String[] split = line.split(",");
        String noteString = split[1];
        char note = noteString.charAt(0);
        int[] result = new int[3];
        if((note == 'A') || (note == 'B')) {
          result[0] = 0;
          result[1] = 1;
          result[2] = Integer.parseInt(split[2]);
          results.add(result);
        } else if((note == 'C')) {
          result[0] = 0;
          result[1] = 0;
          result[2] = Integer.parseInt(split[2]);
          results.add(result);
        } else if((note == 'D')) {
          result[0] = 1;
          result[1] = 0;
          result[2] = Integer.parseInt(split[2]);
          results.add(result);
        } else if((note == 'E')) {
          result[0] = 1;
          result[1] = 1;
          result[2] = Integer.parseInt(split[2]);
          results.add(result);
        } else if((note == 'F')) {
          result[0] = 2;
          result[1] = 0;
          result[2] = Integer.parseInt(split[2]);
          results.add(result);
        } else {
          result[0] = 2;
          result[1] = 1;
          result[2] = Integer.parseInt(split[2]);
          results.add(result);
        }
      }
      bw.close();
      return results;
    }catch(IOException e) {
      System.out.println(e.getMessage());
      System.exit(1);
    }
    return null;
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
      if(n.getY() == ViewMain.h) {
        n.setY(0);
      }
    }
  }

}

