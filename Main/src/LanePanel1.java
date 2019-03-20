import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
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
 * Willem - Planned the structure of lanes on the Guitar (following MVC) with Tom
 */
public class LanePanel1 extends JPanel {
  private CopyOnWriteArrayList<Note> notes;
  private BufferedImage white;
  private BufferedImage black;
  private BufferedImage star;
  private BufferedImage mult1,mult2,mult3;
  private BufferedImage album;
  private int score, currency, mult;
  public LanePanel1(CopyOnWriteArrayList<Note> notes, int score, int currency, int mult) {
    try{
      this.score = score;
      this.currency = currency;
      this.mult = mult;
      this.white = ImageIO.read(new FileInputStream("Main/src/resources/pick.png"));
      this.black = ImageIO.read(new FileInputStream("Main/src/resources/pick1.png"));
      this.star = ImageIO.read(new FileInputStream("Main/src/resources/currency.png"));
      this.mult1 = ImageIO.read(new FileInputStream("Main/src/resources/1.png"));
      this.mult2 = ImageIO.read(new FileInputStream("Main/src/resources/2.png"));
      this.mult3 = ImageIO.read(new FileInputStream("Main/src/resources/3.png"));

      String track = IntendedTrack.getIntendedTrack();
      String trackName = track.split("\\.")[0];
      String[] trackNameSplit = trackName.split("/");
      //this.album = ImageIO.read(new FileInputStream(trackNameSplit[0] + "/" + trackNameSplit[1] + "/resources/" + trackNameSplit[2] + ".jpg"));

      //Scale the album art to the desired size (Tom)
      //int w = album.getWidth();
      //int h = album.getHeight();
      //BufferedImage after = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
      //AffineTransform at = new AffineTransform();
      //at.scale(0.75, 0.75);
      //AffineTransformOp scaleOp =
         // new AffineTransformOp(at, AffineTransformOp.TYPE_BILINEAR);
     // after = scaleOp.filter(album, after);
      //this.album = after;

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

  public void setScore(int score) { this.score = score; }
  public void setCurrency(int currency) { this.currency = currency; }
  public void setMult(int mult) { this.mult = mult; }

  public void paintComponent(Graphics g) {
    Graphics2D g2 = (Graphics2D) g;
    g2.setColor(Color.PINK);
    g2.setFont(new Font("TimesRoman", Font.PLAIN, 64));
    g2.drawString(Integer.toString(score), 0, 750);
    int interval = 0;
    for(int i = 0; i < currency; i++) {
      g2.drawImage(star, interval, 650, null);
      interval += 50;
    }

    switch(mult) {
      case 1:
        g2.drawImage(mult1, 0, 600, null);
        break;
      case 2:
        g2.drawImage(mult2, 0, 600, null);
        break;
      case 3:
        g2.drawImage(mult3, 0, 600, null);
        break;
    }

    //g.drawImage(album, 0, 0, null);

    for(Note n : this.notes) {
      if(n.getLane() == 0) {
        if(n.getColour() == 1) {
          g.drawImage(white, n.getX(), n.getY(), null);
        } else {
          g.drawImage(black, n.getX(), n.getY(), null);
        }
      }
    }
  }
}