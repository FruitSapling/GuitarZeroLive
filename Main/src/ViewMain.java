import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Polygon;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class ViewMain extends JFrame {
  private BufferedImage background;
  private MainPanel panel;
  private int img_height;
  private int img_width;

  public ViewMain() {
    panel = new MainPanel();
    this.add(panel);
    this.pack();
    this.setDefaultCloseOperation(EXIT_ON_CLOSE);
    this.setVisible(true);
  }

  private class MainPanel extends JPanel {
    public MainPanel() {
      initImages();
    }

    @Override
    public Dimension getPreferredSize() {
      return new Dimension(img_width, img_height);
    }

    @Override
    protected void paintComponent(Graphics g) {
      super.paintComponent(g);
      Polygon p = new Polygon();
      p.addPoint(50, 0);
      p.addPoint(0, img_height);
      p.addPoint(img_width, img_height);
      p.addPoint(img_width-50, 0);
      g.setClip(p);
      g.drawImage(background, img_width/2-100,0, this);

      g.setClip(null);
      g.setColor(Color.BLUE);
      g.fillRect(0, img_height/2, img_width,6);
      int[] arrowXs = {img_width/2-10, img_width/2, img_width/2+10};
      int[] arrowYs = {img_height/2+2, img_height/2+16, img_height/2+2};
      g.fillPolygon(arrowXs, arrowYs, 3);
      g.fillRect(0, img_height/2+100, img_width,6);
    }

    private void initImages() {
      try {
        background = ImageIO.read(getClass().getResource("background.png"));
        img_height = background.getHeight();
        img_width = background.getWidth()+100;
      }catch(Exception e) {
        System.out.println(e.getMessage());
        System.exit(1);
      }
    }
  }
}
