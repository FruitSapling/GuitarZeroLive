import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class TutorialView extends JFrame implements PropertyChangeListener {

  private TutorialModel model;
  private TutorialController controller;

  private JPanel panel;

  private MainView.Guitar g;

  public TutorialView(TutorialModel model, TutorialController controller) {
    this.model = model;
    this.controller = controller;

    this.panel = new TutorialPanel();
    this.panel.setPreferredSize(new Dimension(Constants.w,Constants.h));

    this.addKeyListener(controller);

    this.model.addPropertyChangeListener(this);

    this.add(panel);
    this.pack();
    this.setVisible(true);
    this.setDefaultCloseOperation(EXIT_ON_CLOSE);
  }

  @Override
  public void propertyChange(PropertyChangeEvent evt) {
    if (evt.getPropertyName().equals("endTutorial")) {
      this.dispose();
      MainModel model = new MainModel();
      MainGuitarController controller1 = new MainGuitarController(model);
      MainController controller2 = new MainController(model);
      MainView mainMenu = new MainView(model, controller2, controller1);
    }
    this.revalidate();
    this.repaint();
    this.pack();
  }

  public class TutorialPanel extends JPanel {
    public void paintComponent(Graphics g) {
      Graphics2D g2 = (Graphics2D) g;
      g2.drawImage(model.currentStep, 0, 0, this);
////
////      g2.setColor(Color.DARK_GRAY);
////
////      Polygon board = new Polygon();
////      board.addPoint(200, 20);
////      board.addPoint(10, getHeight());
////      board.addPoint(getWidth()-10, getHeight());
////      board.addPoint(550, 20);
////      g2.setClip(board);
////      g2.fillPolygon(board);
////
////      g2.setStroke(new BasicStroke(5));
////      g2.setColor(Color.RED);
////      g2.drawLine(0, 100, getWidth(), 100);
////      g2.drawLine(0, 200, getWidth(), 200);
////      g2.drawLine(0, 300, getWidth(), 300);
////      g2.drawLine(0, 400, getWidth(), 400);
////      g2.drawLine(0, 500, getWidth(), 500);
////      g2.fillRect(0, 600, getWidth(), 50);
////
////      g2.setColor(Color.WHITE);
////      g2.drawLine(250, 20, 125, getHeight());
////      g2.setColor(Color.GREEN);
////      g2.drawLine(375, 20, 375, getHeight());
////      g2.setColor(Color.BLUE);
////      g2.drawLine(500, 20, 625, getHeight());
////
////      g2.setClip(null);


    }
  }

  public static void main(String[] args) {
    TutorialModel model = new TutorialModel();
    TutorialController controller = new TutorialController(model);
    new TutorialView(model, controller);
  }
}
