import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

/**
 * @author Tom
 * Contributed to by:
 * Willem - Planned the structure of lanes on the guitar (following MVC) with Tom
 */
public class LanePanel extends JPanel {
  private CopyOnWriteArrayList<Note> notes;
  private BufferedImage white;
  private BufferedImage black;
  private int lane;
  private int print;
  public LanePanel(CopyOnWriteArrayList<Note> notes, int lane, int print) {
    try{
      this.print = print;
      this.white = ImageIO.read(new FileInputStream("Main/src/resources/pick.png"));
      this.black = ImageIO.read(new FileInputStream("Main/src/resources/pick1.png"));
    }catch(IOException e) {
      System.out.println(e.getMessage());
      System.exit(1);
    }
    this.lane = lane;
    this.notes = notes;
    this.setOpaque(false);
  }

  public void setNotes(CopyOnWriteArrayList<Note> notes) {
    this.notes = notes;
  }

  public void setScore(int score) { this.print = score; }

  public void paintComponent(Graphics g) {
    Graphics2D g2 = (Graphics2D) g;
    g2.setColor(Color.PINK);
    g2.setFont(new Font("TimesRoman", Font.PLAIN, 20));
    g2.drawString(Integer.toString(print), 0, 750);
    for(Note n : this.notes) {
      if(n.getLane() == lane) {
        if(n.getColour() == 1) {
          g.drawImage(white, 100, n.getY(), null);
        } else {
          g.drawImage(black, 100, n.getY(), null);
        }
      }
    }
  }
}