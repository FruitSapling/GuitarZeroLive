import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.*;

/**
 * @author Tom
 * Contributed to by Willem
 */
public class MainView extends JFrame implements PropertyChangeListener {

  private MainModel model1;
  private MainController controller;
  private MainGuitarController gbController;

  private JPanel panel;

  private Guitar g;
  private CarouselMenu menu;

  public MainView(MainModel model1, MainController controller, MainGuitarController gbController) {
    this.model1 = model1;
    this.model1.addPropertyChangeListener(this);
    this.controller = controller;
    this.gbController = gbController;

    this.g = new Guitar(Constants.w,Constants.h);

    this.panel = new JPanel();
    this.panel.setPreferredSize(new Dimension(Constants.w,Constants.h));

    CarouselButton[] buttons = model1.setMenu(this);
    this.menu = new CarouselMenu(buttons, 20, 400);
    model1.addPropertyChangeListener(menu);

    this.panel.add(g);

    this.addKeyListener(controller);
    this.addKeyListener(gbController);

    gbController.addListener(gbController);

    this.add(panel);
    this.pack();
    this.setVisible(true);
    this.setDefaultCloseOperation(EXIT_ON_CLOSE);

  }

  public void stopPoller() {
    this.gbController.guitarPoller.stop();
  }

  public void propertyChange(PropertyChangeEvent pce) {
    if (pce.getPropertyName() == null) {
      if (!model1.menuOpen) {
        model1.menuOpen = true;
        g.add(menu);
      } else if (model1.menuOpen) {
        model1.menuOpen = false;
        g.remove(menu);
      }
    } else if (pce.getPropertyName() == "modelConstructed") {
    }
    this.revalidate();
    this.repaint();
    this.pack();
  }

  public static class Guitar extends JPanel {
    public Guitar(int w, int h) {
      this.setPreferredSize(new Dimension(w, h));
    }
    public void paintComponent(Graphics g) {
      super.paintComponent(g);
      Graphics2D g2 = (Graphics2D) g;

      g2.setColor(Color.DARK_GRAY);

      Polygon board = new Polygon();
      board.addPoint(200, 0);
      board.addPoint(10, getHeight());
      board.addPoint(getWidth()-10, getHeight());
      board.addPoint(550, 0);
      g2.setClip(board);
      g2.fillPolygon(board);

      g2.setStroke(new BasicStroke(5));
      g2.setColor(Color.RED);
      g2.drawLine(0, 100, getWidth(), 100);
      g2.drawLine(0, 200, getWidth(), 200);
      g2.drawLine(0, 300, getWidth(), 300);
      g2.drawLine(0, 400, getWidth(), 400);
      g2.drawLine(0, 500, getWidth(), 500);
      g2.fillRect(0, 600, getWidth(), Constants.BAR_HEIGHT);

      g2.setColor(Color.WHITE);
      g2.drawLine(250, 20, 125, getHeight());
      g2.setColor(Color.GREEN);
      g2.drawLine(375, 20, 375, getHeight());
      g2.setColor(Color.BLUE);
      g2.drawLine(500, 20, 625, getHeight());

      g2.setClip(null);
    }
  }

}
