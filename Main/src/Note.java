public class Note {
  private String note;
  private int startTime, finishTime;

  Note(String note, int startTime, int finishTime) {
    this.note = note;
    this.startTime = startTime;
    this.finishTime = finishTime;
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

  public String getNote() {
    return note;
  }
}
