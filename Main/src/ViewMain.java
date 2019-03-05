import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

public class ViewMain extends JFrame implements PropertyChangeListener {

  private final int w = 750;
  private final int h = 1000;

  private ModelMain model;
  private ControllerMain controller;
  private JPanel panel;
  private guitar g;
  private CarouselMenu menu;

  public ViewMain(ModelMain model, ControllerMain controller, GuitarButtonController controller2) {
    this.model = model;
    this.model.addPropertyChangeListener(this);
    this.controller = controller;

    this.g = new guitar(w,h);

    this.panel = new JPanel();
    this.panel.setPreferredSize(new Dimension(w,h));
    this.panel.add(g);

    this.addKeyListener(controller);
    this.add(panel);
    this.pack();
    this.setVisible(true);
    this.setDefaultCloseOperation(EXIT_ON_CLOSE);
  }

  public void propertyChange(PropertyChangeEvent pce) {
    if(!model.menuOpen) {
      menu();
    } else {
      this.g.remove(menu);
    }
    this.repaint();
    this.revalidate();
    this.pack();
  }

  public class guitar extends JPanel {
    public guitar(int w, int h) {
      this.setPreferredSize(new Dimension(w, h));
    }
    public void paintComponent(Graphics g) {
      super.paintComponent(g);
      Graphics2D g2 = (Graphics2D) g;

      g2.setColor(Color.DARK_GRAY);

      Polygon board = new Polygon();
      board.addPoint(100, 0);
      board.addPoint(100, getHeight());
      board.addPoint(getWidth()-100, getHeight());
      board.addPoint(getWidth()-100, 0);
      g2.setClip(board);
      g2.fillPolygon(board);

      g2.setStroke(new BasicStroke(5));
      g2.setColor(Color.RED);
      g2.drawLine(0, 100, getWidth(), 100);
      g2.drawLine(0, 200, getWidth(), 200);
      g2.drawLine(0, 300, getWidth(), 300);
      g2.drawLine(0, 400, getWidth(), 400);
      g2.drawLine(0, 500, getWidth(), 500);
      g2.fillRect(0, 600, getWidth(), 50);

      g2.setColor(Color.WHITE);
      g2.drawLine(200, 0, 200, getHeight());
      g2.setColor(Color.GREEN);
      g2.drawLine(375, 0, 375, getHeight());
      g2.setColor(Color.BLUE);
      g2.drawLine(550, 0, 550, getHeight());


      g2.setClip(null);
    }
  }

  public void menu() {
    CarouselButton[] buttons = new CarouselButton[5];
    buttons[0] = new CarouselButton(Constants.EXIT_IMAGE_PATH) {
      @Override
      public void onClick() {

      }
    };
    buttons[1] = new CarouselButton(Constants.EXIT_IMAGE_PATH) {
      @Override
      public void onClick() {

      }
    };
    buttons[2] = new CarouselButton(Constants.EXIT_IMAGE_PATH) {
      @Override
      public void onClick() {

      }
    };
    buttons[3] = new CarouselButton(Constants.EXIT_IMAGE_PATH) {
      @Override
      public void onClick() {

      }
    };
    buttons[4] = new CarouselButton(Constants.EXIT_IMAGE_PATH) {
      @Override
      public void onClick() {

      }
    };

    this.menu = new CarouselMenu(buttons, 20, 400);
    this.g.add(menu);
  }
}
