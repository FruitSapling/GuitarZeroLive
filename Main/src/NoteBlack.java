import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;

public class NoteBlack extends Note {
  public NoteBlack(Point pt) {
    this.location = pt;
    this.setPreferredSize(new Dimension(5,5));
  }

  public Color color() {
    return Color.BLACK;
  }
}
