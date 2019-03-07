/*
* @author Mark Newell
*/

public class NoteInfo {
  private String note;
  private int startTime, finishTime, string, colour;

  NoteInfo(String note, int startTime, int finishTime, int string, int colour) {
    this.note = note;
    this.startTime = startTime;
    this.finishTime = finishTime;
    this.string = string;
    this.colour = colour;
  }

  public int getColour() {
    return colour;
  }

  public int getString() {
    return string;
  }

  public long getStartTime() {
    return startTime;
  }

  public long getFinishTime() {
    return finishTime;
  }

  public long getDuration() {
    return finishTime-startTime;
  }

  @Override
  public String toString() {
    return note;
  }
}
