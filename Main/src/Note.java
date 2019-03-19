/**
 * @author Tom
 * Updated to add perspective by Tom
 */
public class Note {

  private int colour;
  private int lane;
  private int y;
  private int x;

  public Note(int lane, int y, int x, int colour) {
    this.lane = lane;
    this.y = y;
    this.x = x;
    this.colour = colour;
  }

  public int getLane() {
    return this.lane;
  }

  public int getY() {
    return this.y;
  }
  public void setY(int y) {
    this.y = y;
  }

  public int getX() { return this.x; }
  public void setX(int x) { this.x = x; }

  public int getColour() {
    return this.colour;
  }

  public void move() {
    this.y = this.y + 1;
    if(this.lane == 0) {
      //TODO: 19/03/2019 make this work for string 0
      this.x = (int) ((200-this.x) + (this.y * -Constants.tanTheta) + this.x);
    } else if(this.lane == 2) {
      this.x = (int) ((this.y * Constants.tanTheta) - this.x);
    }
  }
}
