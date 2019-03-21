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

    CarouselButton[] buttons = setMenu(this);
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
    } else if (pce.getPropertyName() == "modelConstructed") {
      System.out.println(g);
      System.out.println(menu);
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
      board.addPoint(200, 20);
      board.addPoint(10, getHeight());
      board.addPoint(getWidth()-10, getHeight());
      board.addPoint(550, 20);
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
      g2.drawLine(250, 20, 125, getHeight());
      g2.setColor(Color.GREEN);
      g2.drawLine(375, 20, 375, getHeight());
      g2.setColor(Color.BLUE);
      g2.drawLine(500, 20, 625, getHeight());

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
        SelectModel model = new SelectModel();
        SelectController controller = new SelectController(model);
        SelectGuitarController controller2 = new SelectGuitarController(model);
        new SelectView(model, controller, controller2);
      }
    };

    buttons[2] = new CarouselButton(Constants.PLAY_IMAGE_PATH, "Play") {
      @Override public void onClick() {
        if (IntendedTrack.getIntendedTrack().equals("")) {
          JOptionPane.showMessageDialog(null, "You must select a track before attempting to play!"
                  , "Selection Error", JOptionPane.ERROR_MESSAGE);
        }
        else {
          frame.dispose();
          PlayModel model = new PlayModel();
          PlayController1 controller = new PlayController1(model);
          PlayController2 controller2 = new PlayController2(model);
          PlayGuitarController guitarController = new PlayGuitarController(model);
          new PlayView(model, controller, controller2, guitarController);
        }
      }
    };

    buttons[3] = new CarouselButton(Constants.STORE_IMAGE_PATH, "Store") {
      @Override public void onClick() {
        frame.dispose();
        StoreModel model = new StoreModel();
        StoreController controller = new StoreController(model);
        StoreGuitarController guitarController = new StoreGuitarController(model);
        new StoreView(model, controller, guitarController);
      }
    };

    buttons[4] = new CarouselButton(Constants.TUTORIAL_IMAGE_PATH, "Tutorial") {
      @Override public void onClick() {
        frame.dispose();
        TutorialModel model = new TutorialModel();
        TutorialController controller = new TutorialController(model);
        new TutorialView(model, controller);
      }
    };

    return buttons;
  }
}