import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import javax.swing.JButton;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

abstract class Note extends JButton {

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

  public Point getLocation() {
    return this.location;
  }

  public void move(int n) {
    location = new Point(location.x, location.y + n);
  }

  public abstract Color color();
}
