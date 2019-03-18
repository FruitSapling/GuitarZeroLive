import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.concurrent.CopyOnWriteArrayList;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class LanePanel2 extends JPanel {

  private CopyOnWriteArrayList<Note> notes;
  private BufferedImage white;
  private BufferedImage black;

  private int streak;

  public LanePanel2(CopyOnWriteArrayList<Note> notes, int streak) {
    try {
      this.streak = streak;
      this.white = ImageIO.read(new FileInputStream("Main/src/resources/pick.png"));
      this.black = ImageIO.read(new FileInputStream("Main/src/resources/pick1.png"));
    }catch(IOException e) {
      System.out.println(e.getMessage());
      System.exit(1);
    }
    this.notes = notes;
    this.setOpaque(false);
  }

  public void setNotes(CopyOnWriteArrayList<Note> notes) {
    this.notes = notes;
  }

  public void setStreak(int streak) { this.streak = streak; }

  public void paintComponent(Graphics g) {
    System.out.println(getWidth());
    Graphics2D g2 = (Graphics2D) g;
    g2.setColor(Color.PINK);
    g2.setFont(new Font("TimesRoman", Font.PLAIN, 50));
    g2.drawString("Streak: " + Integer.toString(streak), getWidth()/2-110, 100);
    for(Note n : this.notes) {
      if(n.getLane() == 1) {
        if(n.getColour() == 1) {
          g.drawImage(white, n.getX(), n.getY(), null);
        } else {
          g.drawImage(black, n.getX(), n.getY(), null);
        }
      }
    }
  }

}
