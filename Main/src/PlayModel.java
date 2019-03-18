import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author Tom
 * Contributed to by:
 * Willem - Planned the PlayModel structure (following MVC) with Tom, and did some pair programming.
 */
public class PlayModel {

  private PropertyChangeSupport support;

  private CopyOnWriteArrayList<Note> current;

  private Scoring score;

  public PlayModel() {
    this.current = new CopyOnWriteArrayList<>();
    this.support = new PropertyChangeSupport(this);
    this.score = new Scoring();
  }

  public void addPropertyChangeListener(PropertyChangeListener pcl) {
    this.support.addPropertyChangeListener(pcl);
  }

  public Scoring getScore() {
    return this.score;
  }

  //Test function to randomly generate some notes
  public void testFill(int n) {
    //Random rand = new Random();
    //for(int i = 0; i < n; i++) {
    //current.add(new Note(rand.nextInt(3), rand.nextInt(500), rand.nextInt(2)));
    //}
    for (int[] arr : genNotes(IntendedTrack.getIntendedTrack())) {
      switch (arr[0]) {
        case 0:
          current.add(new Note(arr[0], 0 - arr[2] + 600, 250, arr[1]));
          break;
        case 1:
          current.add(new Note(arr[0], 0 - arr[2] + 600, 125, arr[1]));
          break;
        case 2:
          current.add(new Note(arr[0], 0 - arr[2] + 600, 0, arr[1]));
          break;
      }
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
        if(line.equals("10") || line.equals("zero power mode started") || line.equals("zero power mode finished")) {
          continue;
        }
        String[] split = line.split(",");
        if(split[0].equals("OFF")) {
          continue;
        }
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
      if(n.getY() > 622) {
        this.score.noteMissed();
        this.current.remove(n);
      }
    }
  }

  //Refreshes the notes
  public void fireNotes() {
    support.firePropertyChange("Notes", null, this.current);
  }

  private boolean isInBar(Note note) {
    return note.getY() > 572 && note.getY() < 622;
  }

  private boolean wasPressed(Note note, ArrayList<GuitarButton> buttonsPressed) {
    int lane = note.getLane();
    boolean pressed = false;

    for (GuitarButton g: buttonsPressed) {
        if (note.getColour() == 1) { // If the note is white
          if ((lane == 0 && g == GuitarButton.WHITE_1)
              || (lane == 1 && g == GuitarButton.WHITE_2)
              || (lane == 2 && g == GuitarButton.WHITE_3)) {
            pressed = true;
          }
        } else { // If the note is black
          if ((lane == 0 && g == GuitarButton.BLACK_1)
              || (lane == 1 && g == GuitarButton.BLACK_2)
              || (lane == 2 && g == GuitarButton.BLACK_3)) {
            pressed = true;
          }
        }
      }
      return pressed;
  }

  public void guitarStrummed(ArrayList<GuitarButton> buttonsPressed) {
    Iterator<Note> it = this.current.iterator();
    while(it.hasNext()) {
      Note currentNote = it.next();

      if (wasPressed(currentNote, buttonsPressed) && isInBar(currentNote)) {
        // if two/three notes at once, call noteHit() twice/three times
        this.current.remove(currentNote);
        this.score.noteHit();
      }
    }
  }
}
