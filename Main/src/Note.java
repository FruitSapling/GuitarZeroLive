import java.awt.Color;

public class Note {

  private int colour;
  private int lane;
  private int y;

  public Note(int lane, int y, int colour) {
    this.lane = lane;
    this.y = y;
    this.colour = colour;
  }

  public int getLane() {
    return this.lane;
  }

  public int getY() {
    return this.y;
  }

  public int getColour() {
    return this.colour;
  }

  public void move() {
    this.y = this.y + 1;
  }
}
