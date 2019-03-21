import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
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
  private boolean zeroPowerMode = false;

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

  public boolean isZeroPowerMode() {

    return this.zeroPowerMode;

  }

  public void setZeroPowerMode(boolean zeroPowerMode) {

    this.zeroPowerMode = zeroPowerMode;

  }

  public void generateNotes() {

    HashMap<NoteInfo[], Integer> fromNoteFile = readNotesFile(IntendedTrack.getIntendedTrack());
    Iterator it = fromNoteFile.entrySet().iterator();

    while(it.hasNext()){
      Map.Entry note = (Map.Entry) it.next();
      NoteInfo[] laneAndColour = (NoteInfo[]) note.getKey();
      int timeSig = (int) note.getValue();
      int position = (timeSig / 5) - 0;

      switch (laneAndColour[0]) {
        case LANE_ONE:
          current.add(new Note(laneAndColour[0], 0 - position, 200, laneAndColour[1]));
          break;
        case LANE_TWO:
          current.add(new Note(laneAndColour[0], 0 - position, 100, laneAndColour[1]));
          break;
        case LANE_THREE:
          current.add(new Note(laneAndColour[0], 0 - position, 0, laneAndColour[1]));
          break;
        case ZERO_ON:
          current.add(new Note(laneAndColour[0], 0 - position, 0, laneAndColour[1]));
          break;
        case ZERO_OFF:
          current.add(new Note(laneAndColour[0], 0 - position, 0, laneAndColour[1]));
          break;
      }
    }
  }

  public HashMap<NoteInfo[], Integer> readNotesFile(String file) {

    try {
      HashMap<NoteInfo[], Integer> results = new HashMap<>();
      File fileObj = new File(file);
      FileReader fw = new FileReader(fileObj);
      BufferedReader bw = new BufferedReader(fw);

      String line;
      while((line = bw.readLine()) != null) {
        if(line.length()==1){
          continue;
        }else{
          if(line.split(",")[0].equals("zero power mode started")){
            results.put(new NoteInfo[]{NoteInfo.ZERO_ON, NoteInfo.ZERO}, Integer.parseInt(line.split(",")[1]));
            continue;
          }else if(line.split(",")[0].equals("zero power mode finished")){
            results.put(new NoteInfo[]{NoteInfo.ZERO_OFF, NoteInfo.ZERO}, Integer.parseInt(line.split(",")[1]));
            continue;
          }
        }

        String[] split = line.split(",");
        if(split[0].equals("OFF")) {
          continue;
        }

        String noteString = split[1];
        char note = noteString.charAt(0);

        if((note == 'A') || (note == 'B')) {
          results.put(new NoteInfo[]{NoteInfo.LANE_ONE, NoteInfo.BLACK}, Integer.parseInt(split[2]));
        }
        else if((note == 'C')) {
          results.put(new NoteInfo[]{NoteInfo.LANE_ONE, NoteInfo.WHITE}, Integer.parseInt(split[2]));
        }
        else if((note == 'D')) {
          results.put(new NoteInfo[]{NoteInfo.LANE_TWO, NoteInfo.BLACK}, Integer.parseInt(split[2]));
        }
        else if((note == 'E')) {
          results.put(new NoteInfo[]{NoteInfo.LANE_TWO, NoteInfo.WHITE}, Integer.parseInt(split[2]));
        }
        else if((note == 'F')) {
          results.put(new NoteInfo[]{NoteInfo.LANE_THREE, NoteInfo.BLACK}, Integer.parseInt(split[2]));
        }
        else {
          results.put(new NoteInfo[]{NoteInfo.LANE_THREE, NoteInfo.WHITE}, Integer.parseInt(split[2]));
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
      if(n.getY() == 0) {
        if (n.getLane().equals(NoteInfo.ZERO_ON)) {
          setZeroPowerMode(true);
        } else if (n.getLane().equals(NoteInfo.ZERO_OFF)) {
          setZeroPowerMode(false);
        }
      }

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

    NoteInfo lane = note.getLane();
    boolean pressed = false;

    if(!zeroPowerMode){
      for (GuitarButton g: buttonsPressed) {
        if (note.getColour().equals(NoteInfo.WHITE)) { // If the note is white
          if ((lane.equals(NoteInfo.LANE_ONE) && g == GuitarButton.WHITE_1)
              || (lane.equals(NoteInfo.LANE_TWO) && g == GuitarButton.WHITE_2)
              || (lane.equals(NoteInfo.LANE_THREE) && g == GuitarButton.WHITE_3)) {

            pressed = true;

          }
        } else { // If the note is black
          if ((lane.equals(NoteInfo.LANE_ONE) && g == GuitarButton.BLACK_1)
              || (lane.equals(NoteInfo.LANE_TWO) && g == GuitarButton.BLACK_2)
              || (lane.equals(NoteInfo.LANE_THREE) && g == GuitarButton.BLACK_3)) {

            pressed = true;

          }
        }
      }
    } else{
      for(GuitarButton g: buttonsPressed){
        if(g == GuitarButton.ZERO_POWER || g == GuitarButton.BENDER || g == GuitarButton.WHAMMY || g == GuitarButton.BENDER_JOYSTICK){
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
        this.current.remove(currentNote);
        this.score.noteHit();
      }
    }
  }
}
