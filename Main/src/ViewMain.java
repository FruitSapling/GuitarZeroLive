import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

public class ViewMain {
  private JPanel panel;

  public ViewMain() {
    JFrame frame = new JFrame();
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.add(new AnimatePanel());
    frame.pack();
    frame.setSize(500,1000);
    frame.setLocationRelativeTo(null);
    frame.setVisible(true);
  }

  private class AnimatePanel extends JPanel {
    private static final int WIDTH = 500;
    private static final int HEIGHT = 1000;
    private static final int INC = 10;

    private BufferedImage background;

    private int dx1, dy1, dx2, dy2;
    private int srcx1, srcy1, srcx2, srcy2;
    private int img_height;

    public AnimatePanel() {
      initImages();
      initImagePoints();
      Timer timer = new Timer(40, new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          moveBackground();
          repaint();
        }
      });
      timer.start();

      FlowLayout layout = (FlowLayout)getLayout();
      layout.setHgap(0);
      layout.setVgap(0);
    }

    @Override
    protected void paintComponent(Graphics g) {
      super.paintComponent(g);
      g.setColor(Color.WHITE);
      g.fillRect(0,0,getWidth(), getHeight());
      g.drawImage(background, dx1, dy1, dx2, dy2, srcx1, srcy1, srcx2, srcy2, this);
    }

    public Dimension getPreferedSize() {
      return new Dimension(500, 1000);
    }

    private void initImagePoints() {
      dx1 = 0;
      dy1 = 0;

      dx2 = WIDTH;
      dy2 = HEIGHT;

      srcx1 = 0;
      srcy1 = 0;

      srcx2 = WIDTH;
      srcy2 = HEIGHT;
    }

    private void initImages() {
      try {
        background = ImageIO.read(getClass().getResource("background.png"));
        img_height = background.getHeight();
      }catch(Exception e) {
        System.out.println(e.getMessage());
      }
    }

    private void moveBackground() {
      if(srcy1 > img_height) {
        srcy1 = 0 - HEIGHT;
        srcy2 = 0;
      }else {
        srcy1 += INC;
        srcy2 += INC;
      }
    }
  }
}
