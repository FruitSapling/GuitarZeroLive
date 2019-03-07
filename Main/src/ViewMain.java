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
public class ViewMain extends JFrame implements PropertyChangeListener {

  public static final int w = 750;
  public static final int h = 1000;

  private ModelMain model1;
  private ControllerMain controller;
  private GuitarButtonController gbController;

  private JPanel panel;

  private guitar g;
  private CarouselMenu menu;

  public ViewMain(ModelMain model1, ControllerMain controller, GuitarButtonController gbController) {
    this.model1 = model1;
    this.model1.addPropertyChangeListener(this);
    this.controller = controller;
    this.gbController = gbController;

    this.g = new guitar(w,h);

    this.panel = new JPanel();
    this.panel.setPreferredSize(new Dimension(w,h));

    CarouselButton[] buttons = setMenu(this);
    this.menu = new CarouselMenu(buttons, 20, 400);
    model1.addPropertyChangeListener(menu);

    this.panel.add(g);

    this.addKeyListener(controller);
    this.addKeyListener(gbController);

    this.add(panel);
    this.pack();
    this.setVisible(true);
    this.setDefaultCloseOperation(EXIT_ON_CLOSE);

  }

  public void propertyChange(PropertyChangeEvent pce) {
    if (pce.getPropertyName() == null) {
      if (!model1.menuOpen) {
        model1.menuOpen = true;
        g.add(menu);
        System.out.println("added menu");
      } else if (model1.menuOpen) {
        model1.menuOpen = false;
        g.remove(menu);
        System.out.println("removed menu");
      }
    }
    this.revalidate();
    this.repaint();
    this.pack();
  }

  public static class guitar extends JPanel {
    public guitar(int w, int h) {
      this.setPreferredSize(new Dimension(w, h));
    }
    public void paintComponent(Graphics g) {
      super.paintComponent(g);
      Graphics2D g2 = (Graphics2D) g;

      g2.setColor(Color.DARK_GRAY);

      Polygon board = new Polygon();
      board.addPoint(0, 0);
      board.addPoint(0, getHeight());
      board.addPoint(getWidth(), getHeight());
      board.addPoint(getWidth(), 0);
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
      g2.drawLine(125, 0, 125, getHeight());
      g2.setColor(Color.GREEN);
      g2.drawLine(375, 0, 375, getHeight());
      g2.setColor(Color.BLUE);
      g2.drawLine(624, 0, 624, getHeight());


      g2.setClip(null);
    }
  }

  public CarouselButton[] setMenu(JFrame frame) {
    CarouselButton[] buttons = new CarouselButton[5];

    buttons[0] = new CarouselButton(Constants.EXIT_IMAGE_PATH, "Exit") {
      @Override public void onClick() {
        System.exit(0);
      }
    };

    buttons[1] = new CarouselButton(Constants.SELECT_IMAGE_PATH, "Select") {
      @Override public void onClick() {
        frame.dispose();
        ModelSelect model = new ModelSelect();
        ControllerSelect controller = new ControllerSelect(model);
        new ViewSelect(model, controller, gbController);
      }
    };

    buttons[2] = new CarouselButton(Constants.PLAY_IMAGE_PATH, "Play") {
      @Override public void onClick() {
        frame.dispose();
        PlayModel model = new PlayModel();
        PlayController1 controller = new PlayController1(model);
        PlayController2 controller2 = new PlayController2(model);
        new PlayView(model, controller2);
      }
    };

    buttons[3] = new CarouselButton(Constants.STORE_IMAGE_PATH, "Store") {
      @Override public void onClick() {
        frame.dispose();
        ModelStore model = new ModelStore();
        ControllerStore controller = new ControllerStore(model);
        new ViewStore(model, controller, gbController);
      }
    };

    buttons[4] = new CarouselButton(Constants.TUTORIAL_IMAGE_PATH, "Tutorial") {
      @Override public void onClick() { }
    };

    return buttons;
  }
}