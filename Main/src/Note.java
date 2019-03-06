import java.awt.Color;
import java.awt.Point;

abstract class Note {

  protected Point location;

  public static Note pick(Point pt, String note) {
    switch(note) {
      case "A":
        return new NoteWhite(pt);
      case "B":
        return new NoteWhite(pt);
      case "C":
        return new NoteWhite(pt);
      case "D":
        return new NoteBlack(pt);
      case "E":
        return new NoteBlack(pt);
      case "F":
        return new NoteBlack(pt);
    }
    return null;
  }

  public void move(int n) {
    location = new Point(location.x, location.y + n);
  }

  public abstract Color color();
}
