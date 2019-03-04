import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.*;

public class ViewMain extends JFrame implements PropertyChangeListener {

  private final int w = 750;
  private final int h = 1000;

  private Model model;
  private Controller2 controller;

  private JPanel panel;

  private guitar g;
  private menu m;

  public ViewMain(Model model, Controller2 controller, GuitarButtonController guitarButtonController) {
    this.model = model;
    this.model.addPropertyChangeListener(this);
    this.controller = controller;

    this.g = new guitar(w,h);
    this.m = new menu(500,500);

    if(model.menuOpen) {
      this.g.add(m);
    }

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
    if(model.menuOpen) {
      this.g.add(m);
    } else {
      this.g.remove(m);
    }
    this.pack();
  }

  public class guitar extends JPanel {
    public guitar(int w, int h) {
      this.setPreferredSize(new Dimension(w, h));
    }
    public void paintComponent(Graphics g) {
      super.paintComponent(g);
      Graphics2D g2 = (Graphics2D) g;

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

      g2.setColor(Color.orange);
      g2.drawLine(150, 0, 150, getHeight());
      g2.drawLine(235, 0, 235, getHeight());
      g2.setStroke(new BasicStroke(4));
      g2.drawLine(320, 0, 320, getHeight());
      g2.setStroke(new BasicStroke(3));
      g2.setColor(Color.GRAY);
      g2.drawLine(405, 0, 405, getHeight());
      g2.setStroke(new BasicStroke(2));
      g2.drawLine(490, 0, 490, getHeight());
      g2.setStroke(new BasicStroke(2));
      g2.drawLine(575, 0, 575, getHeight());

      g2.setClip(null);
    }
  }

  public class menu extends JLayeredPane {
    private int x;
    private int y;
    public menu(int x, int y) {
      this.x = x;
      this.y = y;
      this.setPreferredSize(new Dimension(750, 100));
      this.setVisible(true);
    }
    public void paintComponent(Graphics g) {
      super.paintComponent(g);
      Graphics2D g2 = (Graphics2D) g;
      g2.fillRect(0,0,750,100);
    }
  }

}
