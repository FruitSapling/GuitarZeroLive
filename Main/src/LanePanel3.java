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

  public LanePanel3(CopyOnWriteArrayList<Note> notes, boolean zeroPowerMode) {
    try {
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

  public void paintComponent(Graphics g) {
    if(zeroPowerMode) {
      g.drawImage(zeroPowerIcon, 200, 600, null);
    }
    for(Note n : this.notes) {
      if(n.getLane() == 2) {
        if(n.getColour() == 1) {
          g.drawImage(white, n.getX(), n.getY(), null);
        } else {
          g.drawImage(black, n.getX(), n.getY(), null);
        }
      }
    }
  }

}
