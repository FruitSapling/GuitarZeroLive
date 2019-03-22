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
    this.setLocationRelativeTo(null);
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
      board.addPoint(Constants.guitarStartTop, 0);
      board.addPoint(Constants.guitarStartBottom, getHeight());
      board.addPoint(getWidth()-Constants.guitarStartBottom, getHeight());
      board.addPoint(Constants.guitarEndTop, 0);
      g2.setClip(board);
      g2.fillPolygon(board);

      g2.setStroke(new BasicStroke(5));
      g2.setColor(Color.RED);
      g2.drawLine(0, Constants.fret1, getWidth(), Constants.fret1);
      g2.drawLine(0, Constants.fret2, getWidth(), Constants.fret2);
      g2.drawLine(0, Constants.fret3, getWidth(), Constants.fret3);
      g2.drawLine(0, Constants.fret4, getWidth(), Constants.fret4);
      g2.drawLine(0, Constants.fret5, getWidth(), Constants.fret5);
      g2.fillRect(0, Constants.strumBar, getWidth(), Constants.BAR_HEIGHT);

      g2.setColor(Color.WHITE);
      g2.drawLine(Constants.STR1_START, 0, Constants.STR1_END, getHeight());
      g2.setColor(Color.GREEN);
      g2.drawLine(Constants.STR2_START, 0, Constants.STR2_END, getHeight());
      g2.setColor(Color.BLUE);
      g2.drawLine(Constants.STR3_START, 0, Constants.STR3_END, getHeight());

      g2.setClip(null);
    }
  }

}
