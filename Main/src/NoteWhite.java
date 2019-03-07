import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import javax.swing.ImageIcon;

public class NoteWhite extends Note {
  public NoteWhite(Point pt) {
    this.location = pt;
    ImageIcon img = new ImageIcon(getClass().getResource("resources/pick.png"));
    this.setIcon(img);
    this.setPreferredSize(new Dimension(100,115));
    this.setOpaque(true);
  }

  public Color color() {
    return Color.WHITE;
  }
}
