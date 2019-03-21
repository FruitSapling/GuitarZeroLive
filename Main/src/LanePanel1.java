import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.concurrent.CopyOnWriteArrayList;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

/**
 * @author Tom Contributed to by: Willem - Planned the structure of lanes on the Guitar (following
 * MVC) with Tom Adapted by Tom to facilitate the three lanes containing different information.
 *
 * LanePanel1 contains the notes on the left most string, as well as the score, multiplier, ingame
 * currency and the album art.
 */
public class LanePanel1 extends JPanel {

  private CopyOnWriteArrayList<Note> notes;

  private BufferedImage white, black, star, mult1, mult2, mult3, mult4, mult5, mult6, mult7, mult8, mult9, mult10, album;

  private int score, currency, mult;

  private boolean artFound = true;

  public LanePanel1(CopyOnWriteArrayList<Note> notes, int score, int currency, int mult) {
    try {
      this.score = score;
      this.currency = currency;
      this.mult = mult;
      this.white = ImageIO.read(new FileInputStream("Main/src/resources/pick.png"));
      this.black = ImageIO.read(new FileInputStream("Main/src/resources/pick1.png"));
      this.star = ImageIO.read(new FileInputStream("Main/src/resources/currency.png"));
      this.mult1 = ImageIO.read(new FileInputStream("Main/src/resources/1.png"));
      this.mult2 = ImageIO.read(new FileInputStream("Main/src/resources/2.png"));
      this.mult3 = ImageIO.read(new FileInputStream("Main/src/resources/3.png"));
      this.mult4 = ImageIO.read(new FileInputStream("Main/src/resources/4.png"));
      this.mult5 = ImageIO.read(new FileInputStream("Main/src/resources/5.png"));
      this.mult6 = ImageIO.read(new FileInputStream("Main/src/resources/6.png"));
      this.mult7 = ImageIO.read(new FileInputStream("Main/src/resources/7.png"));
      this.mult8 = ImageIO.read(new FileInputStream("Main/src/resources/8.png"));
      this.mult9 = ImageIO.read(new FileInputStream("Main/src/resources/9.png"));
      this.mult10 = ImageIO.read(new FileInputStream("Main/src/resources/10.png"));

      String track = IntendedTrack.getIntendedTrack();
      track = track.substring(0, track.length() - 9) + ".jpg";

      //Code to read and resize the album art to half its original size
      try {
        this.album = ImageIO.read(new FileInputStream(track));
        int w = album.getWidth();
        int h = album.getHeight();
        BufferedImage after = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        AffineTransform at = new AffineTransform();
        at.scale(0.5, 0.5);
        AffineTransformOp scaleOp =
            new AffineTransformOp(at, AffineTransformOp.TYPE_BILINEAR);
        after = scaleOp.filter(album, after);
        this.album = after;
      } catch (IOException e) {
        System.out.println("Album Art Not Found, Continuing Without");
        this.artFound = false;
      }

    } catch (IOException e) {
      System.out.println(e.getMessage());
      System.exit(1);
    }
    this.notes = notes;
    this.setOpaque(false);
  }

  public void setNotes(CopyOnWriteArrayList<Note> notes) {
    this.notes = notes;
  }

  public void setScore(int score) {
    this.score = score;
  }

  public void setCurrency(int currency) {
    this.currency = currency;
  }

  public void setMult(int mult) {
    this.mult = mult;
  }

  public void paintComponent(Graphics g) {
    Graphics2D g2 = (Graphics2D) g;
    g2.setColor(Color.BLACK);
    g2.setFont(new Font("TimesRoman", Font.PLAIN, 64));

    g2.drawString("Score:", 0, 700);
    g2.drawString(Integer.toString(score), 0, 750);

    //Draws a number of stars based on the amount of in-game currency the player has
    int interval = 0;
    for (int i = 0; i < currency; i++) {
      g2.drawImage(star, interval, 600, null);
      interval += 50;
    }

    switch (mult) {
      case 1:
        g2.drawImage(mult1, 0, 300, null);
        break;
      case 2:
        g2.drawImage(mult2, 0, 300, null);
        break;
      case 3:
        g2.drawImage(mult3, 0, 300, null);
        break;
      case 4:
        g2.drawImage(mult4, 0, 300, null);
        break;
      case 5:
        g2.drawImage(mult5, 0, 300, null);
        break;
      case 6:
        g2.drawImage(mult6, 0, 300, null);
        break;
      case 7:
        g2.drawImage(mult7, 0, 300, null);
        break;
      case 8:
        g2.drawImage(mult8, 0, 300, null);
        break;
      case 9:
        g2.drawImage(mult9, 0, 300, null);
        break;
      case 10:
        g2.drawImage(mult10, 0, 300, null);
        break;
    }

    if (artFound) {
      g.drawImage(album, 0, 0, null);
    }

    //Paint notes on string
    for (Note n : this.notes) {
      if (n.getLane().equals(NoteInfo.LANE_ONE)) {
        if (n.getColour().equals(NoteInfo.WHITE)) {
          g.drawImage(white, n.getX(), n.getY(), null);
        } else if (n.getColour().equals(NoteInfo.BLACK)) {
          g.drawImage(black, n.getX(), n.getY(), null);
        }
      }
    }
  }
}