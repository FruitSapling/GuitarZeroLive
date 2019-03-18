import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;
import javax.sound.midi.Instrument;
import javax.sound.midi.MidiChannel;

/**
 * @author Tom
 * Contributed to by:
 * Willem - Planned the PlayModel structure (following MVC) with Tom, and did some pair programming.
 */
public class PlayModel {

  private PropertyChangeSupport support;

  private CopyOnWriteArrayList<Note> current;

  private Scoring score;

  private Instrument g;

  public PlayModel() {
    this.current = new CopyOnWriteArrayList<>();
    this.support = new PropertyChangeSupport(this);
    this.score = new Scoring();
    this.g = PlayGuitar.play(29); // TODO read this number from first line of notes file
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
    for(int[] arr : genNotes("Main/src/AllTheSmallThings.midnotes")) {
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
    Iterator<Note> it = this.current.iterator();
    while(it.hasNext()) {
      Note currentNote = it.next();
      if(currentNote.getY() > 600 && currentNote.getY() < 650) {
        // if two/three notes at once, call noteHit() twice/three times
        this.current.remove(currentNote);
        this.score.noteHit();
        // TODO get note number, octave and duration from note file
        try {
          PlayGuitar.playNote(g,"G#",4,207);
        } catch (InterruptedException ex) {
          ex.printStackTrace();
        }
      } else {
        this.score.noteMissed();
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

