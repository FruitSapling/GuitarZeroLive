import java.awt.Color;
import java.awt.Point;

public class NoteBlack extends Note {
  public NoteBlack(Point pt) {
    this.location = pt;
  }

  public Color color() {
    return Color.BLACK;
  }
}
