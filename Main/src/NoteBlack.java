import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JLayeredPane;

public class NoteBlack extends JLayeredPane {

  private BufferedImage bi;

  private Point location;

  public NoteBlack(Point pt) {
    this.location = pt;
    this.setPreferredSize(new Dimension(100,115));
    try {
      this.bi = ImageIO.read(getClass().getResource("resources/pick.png"));
    }catch(IOException e) {

    }
  }

  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    g.drawImage(this.bi, 200, 100, null);
  }

  public Color color() {
    return Color.BLACK;
  }
}
