import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class LanePanel extends JPanel {
  private ArrayList<Note> notes;
  private BufferedImage white;
  private BufferedImage black;
  private int lane;
  public LanePanel(ArrayList<Note> notes, int lane) {
    try{
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

  public void setNotes(ArrayList<Note> notes) {
    this.notes = notes;
  }

  public void paintComponent(Graphics g) {
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