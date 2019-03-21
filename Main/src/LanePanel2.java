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

  public LanePanel2(CopyOnWriteArrayList<Note> notes) {
    try {
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

  public void paintComponent(Graphics g) {
    for(Note n : this.notes) {
      if(n.getLane().equals(NoteInfo.LANE_TWO)) {
        if(n.getColour().equals(NoteInfo.WHITE)) {
          g.drawImage(white, n.getX(), n.getY(), null);
        } else if(n.getColour().equals(NoteInfo.BLACK)) {
          g.drawImage(black, n.getX(), n.getY(), null);
        }
      }
    }
  }

}
