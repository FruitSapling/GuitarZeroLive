import java.util.ArrayList;

public class Guitar {
  private int instrumentNumber;
  private int trackNumber;
  private int channelNumber;
  private int noteCount;
  private ArrayList<String> notes = new ArrayList<String>();

  public Guitar(int trackNumber, int channelNumber, int instrumentNumber){
    this.trackNumber = trackNumber;
    this.channelNumber = channelNumber;
    this.instrumentNumber = instrumentNumber;
    this.noteCount = 1;
  }

  public int getInstrumentNumber(){
    return this.instrumentNumber;
  }

  public void setInstrumentNumber(int instrumentNumber){
    this.instrumentNumber = instrumentNumber;
  }

  public void addNote(String note){
    if(!notes.contains(note))
       this.notes.add(note);
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

  public void incrementNoteCount(){
    this.noteCount += 1;
  }

  public int getNoteCount() { return this.noteCount; }
}
