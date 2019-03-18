import java.util.ArrayList;

/**
 * This stores information about each Guitar found on the midi file
 *
 * @author Luke Sykes
 * @version 1.0
 * @since 2019-03-07
 */
public class Guitar {
  private int instrumentNumber;
  private int trackNumber;
  private int channelNumber;
  private int noteCount;
  private ArrayList<String> notes = new ArrayList<String>();

  /**
   * Assigns information about the current Guitar
   * @param trackNumber the track the Guitar is played on
   * @param channelNumber the channel the Guitar is played on
   * @param instrumentNumber the instrument number of the Guitar being played
   */
  public Guitar(int trackNumber, int channelNumber, int instrumentNumber){
    this.trackNumber = trackNumber;
    this.channelNumber = channelNumber;
    this.instrumentNumber = instrumentNumber;
    this.noteCount = 0;
  }

  /**
   * Add the given note to the notes array if its not already in there
   * @param note the note being played
   */
  public void addNote(String note){
    if(!notes.contains(note))
       this.notes.add(note);
  }

  /**
   * Increase the note count by 1
   */
  public void incrementNoteCount(){
    this.noteCount += 1;
  }

  public int getInstrumentNumber(){
    return this.instrumentNumber;
  }

  public void setInstrumentNumber(int instrumentNumber){
    this.instrumentNumber = instrumentNumber;
  }

  public ArrayList<String> getNotes(){
    return this.notes;
  }

  public int getTrackNumber(){
    return this.trackNumber;
  }

  public int getChannelNumber(){
    return this.channelNumber;
  }

  public int getNoteCount() {
    return this.noteCount;
  }
}
