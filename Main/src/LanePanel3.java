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

public class LanePanel3 extends JPanel {

  private CopyOnWriteArrayList<Note> notes;
  private BufferedImage white;
  private BufferedImage black;
  private BufferedImage zeroPowerIcon;

  private boolean zeroPowerMode;

  private int streak;

  public LanePanel3(CopyOnWriteArrayList<Note> notes, boolean zeroPowerMode, int streak) {
    try {
      this.streak = streak;
      this.white = ImageIO.read(new FileInputStream("Main/src/resources/pick.png"));
      this.black = ImageIO.read(new FileInputStream("Main/src/resources/pick1.png"));
      this.zeroPowerIcon = ImageIO.read(new FileInputStream("Main/src/resources/ZeroPower.png"));
      this.zeroPowerMode = zeroPowerMode;
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
  public void setZeroPowerMode(boolean zeroPowerMode) {
    this.zeroPowerMode = zeroPowerMode;
  }
  public void setStreak(int streak) {
    this.streak = streak;
  }

  public void paintComponent(Graphics g) {
    Graphics2D g2 = (Graphics2D) g;
    g2.setColor(Color.BLACK);
    g2.setFont(new Font("TimesRoman", Font.PLAIN, 30));
    g2.drawString("Streak: " + Integer.toString(streak), getWidth()/2, 50);
    if(zeroPowerMode) {
      g.drawImage(zeroPowerIcon, 200, 600, null);
    }
    for(Note n : this.notes) {
      if(n.getLane().equals(NoteInfo.LANE_THREE)) {
        if(n.getColour().equals(NoteInfo.WHITE)) {
          g.drawImage(white, n.getX(), n.getY(), null);
        } else if(n.getColour().equals(NoteInfo.BLACK)) {
          g.drawImage(black, n.getX(), n.getY(), null);
        }
      }
    }
  }

}
