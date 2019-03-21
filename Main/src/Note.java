/**
 * @author Tom Updated to add perspective by Tom
 */
public class Note {

  private NoteInfo colour;
  private NoteInfo lane;
  private int y;
  private int x;

  public Note(NoteInfo lane, int y, int x, NoteInfo colour) {
    this.lane = lane;
    this.y = y;
    this.x = x;
    this.colour = colour;
  }

  public NoteInfo getLane() {
    return this.lane;
  }

  public int getY() {
    return this.y;
  }

  public void setY(int y) {
    this.y = y;
  }

  public int getX() {
    return this.x;
  }

  public void setX(int x) {
    this.x = x;
  }

  public NoteInfo getColour() {
    return this.colour;
  }

  public void move() {
    this.y = this.y + 1;
    if (this.lane.equals(NoteInfo.LANE_ONE)) {
      //Likely can be done neater but this functions.
      this.x = (int) ((200 - this.x) - (this.y * (Constants.tanTheta - 0.15)) + this.x);
    } else if (this.lane.equals(NoteInfo.LANE_THREE)) {
      this.x = (int) ((this.y * Constants.tanTheta) - this.x);
    }
  }
}
