import java.awt.Color;
import java.awt.Graphics2D;

public class NoteIcon {
  private int x,y;
  public NoteIcon(int string, Graphics2D g2) {
    int widthOfNote = 40;
    this.x = 125 + ((string-1)*50) - widthOfNote/2;
    this.y = 100;

    g2.setColor(Color.CYAN);
    g2.fillOval(this.x, this.y, 40, 40);
  }


  // TODO: 14/02/2019
  public void move() {
    this.y = this.y + 5;
  }
}
