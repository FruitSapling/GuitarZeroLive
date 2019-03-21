import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * A View class for tutorial mode This class shows the current step stored in the model.
 *
 * @author Willem
 */
public class TutorialView extends JFrame implements PropertyChangeListener {

  private TutorialModel model;

  private JPanel panel;

  private MainView.Guitar g;

  public TutorialView(TutorialModel model, TutorialController controller) {
    this.model = model;

    this.panel = new TutorialPanel();
    this.panel.setPreferredSize(new Dimension(Constants.w, Constants.h));

    this.addKeyListener(controller);

    this.model.addPropertyChangeListener(this);

    this.add(panel);
    this.pack();
    this.setVisible(true);
    this.setLocationRelativeTo(null);
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

      // Draw the current tutorial step to the screen
      g2.drawImage(model.currentStep, 0, 0, this);
    }
  }

  public static void main(String[] args) {
    TutorialModel model = new TutorialModel();
    TutorialController controller = new TutorialController(model);
    new TutorialView(model, controller);
  }
}
