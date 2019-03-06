import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;

public class NoteWhite extends Note {
  public NoteWhite(Point pt) {
    this.location = pt;
    this.setPreferredSize(new Dimension(20,20));
    this.setOpaque(true);
    this.setBackground(this.color());
  }

  public Color color() {
    return Color.WHITE;
  }
}
