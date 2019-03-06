import java.awt.Color;
import java.awt.Point;

public class NoteWhite extends Note {
  public NoteWhite(Point pt) {
    this.location = pt;
  }

  public Color color() {
    return Color.WHITE;
  }
}
