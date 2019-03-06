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
  private GuitarButtonController controller2;

  private JPanel panel;
  private guitar g;
  private CarouselMenu menu;

  public ViewMain(ModelMain model1, ControllerMain controller, GuitarButtonController controller2) {
    this.model1 = model1;
    this.model1.addPropertyChangeListener(this);
    this.controller = controller;
    this.controller2 = controller2;

    this.g = new guitar(w, h);

    this.panel = new JPanel();
<<<<<<< HEAD
    this.panel.setPreferredSize(new Dimension(w,h));
    this.panel.add(g);

    this.addKeyListener(controller);
    this.add(panel);
    this.pack();
    this.setVisible(true);
    this.setDefaultCloseOperation(EXIT_ON_CLOSE);
  }

  public void propertyChange(PropertyChangeEvent pce) {
    if(model1.menuOpen) {
      menu();
    } else {
      this.g.remove(menu);
    }
    this.repaint();
    this.revalidate();
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
=======
    this.panel.setPreferredSize(new Dimension(w, h));
>>>>>>> origin/master

    CarouselButton[] buttons = new CarouselButton[5];

    buttons[0] = new CarouselButton(Constants.EXIT_IMAGE_PATH) {
      @Override
      public void onClick() {
        System.exit(0);
      }
    };
    buttons[1] = new CarouselButton(Constants.SELECT_IMAGE_PATH) {
      @Override
      public void onClick() {

      }
    };
    buttons[2] = new CarouselButton(Constants.PLAY_IMAGE_PATH) {
      @Override
      public void onClick() {

      }
    };
    buttons[3] = new CarouselButton(Constants.STORE_IMAGE_PATH) {
      @Override
      public void onClick() {

      }
    };
    buttons[4] = new CarouselButton(Constants.TUTORIAL_IMAGE_PATH) {
      @Override
      public void onClick() {

      }
    };

    this.menu = new CarouselMenu(buttons, 20, 400);

    model1.addPropertyChangeListener(menu);

    this.panel.add(g);

    this.addKeyListener(controller);
    this.addKeyListener(controller2);
    this.add(panel);
    this.pack();
    this.setVisible(true);
    this.setDefaultCloseOperation(EXIT_ON_CLOSE);
  }

  public void propertyChange(PropertyChangeEvent pce) {
    if (!model1.menuOpen) {
      model1.menuOpen = true;
      g.add(menu);
      System.out.println("added menu");
    }
    else {
      this.g.remove(menu);
    }
    this.pack();
    this.revalidate();
    this.repaint();
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
        board.addPoint(100, 0);
        board.addPoint(100, getHeight());
        board.addPoint(getWidth() - 100, getHeight());
        board.addPoint(getWidth() - 100, 0);
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

    public void setMenu() {
      CarouselButton[] buttons = new CarouselButton[5];
      buttons[0] = new CarouselButton(Constants.EXIT_IMAGE_PATH) {
        @Override
        public void onClick() {
          System.exit(0);
        }
      };

      buttons[1] = new CarouselButton(Constants.SELECT_IMAGE_PATH) {
        @Override
        public void onClick() {
          dispose();
          new ViewStore(model1, controller, controller2);
        }
      };

      buttons[2] = new CarouselButton(Constants.PLAY_IMAGE_PATH) {
        @Override
        public void onClick() {

        }
      };

      buttons[3] = new CarouselButton(Constants.STORE_IMAGE_PATH) {
        @Override
        public void onClick() {
          dispose();
          new ViewStore(model1, controller, controller2);
        }
      };

      buttons[4] = new CarouselButton(Constants.TUTORIAL_IMAGE_PATH) {
        @Override
        public void onClick() {

        }
      };

      this.menu = new CarouselMenu(buttons, 20, 400);
    }
  }
