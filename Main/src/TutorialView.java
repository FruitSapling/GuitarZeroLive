import java.awt.Dimension;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class TutorialView extends JFrame implements PropertyChangeListener {

  private TutorialModel model;
  private TutorialController controller;

  private JPanel panel;

  private MainView.Guitar g;

  public TutorialView(TutorialModel model, TutorialController controller) {
    this.model = model;
    this.controller = controller;

    this.panel = new JPanel();
    this.panel.setPreferredSize(new Dimension(Constants.w,Constants.h));

    this.g = new MainView.Guitar(Constants.w,Constants.h);

    this.addKeyListener(controller);

    this.panel.add(g);

    JLabel label = new JLabel();

    label.setText("hello");

    this.panel.add(label);

    this.add(panel);
    this.pack();
    this.setVisible(true);
    this.setDefaultCloseOperation(EXIT_ON_CLOSE);
  }

  @Override
  public void propertyChange(PropertyChangeEvent evt) {
  }

  public static void main(String[] args) {
    TutorialModel model = new TutorialModel();
    TutorialController controller = new TutorialController(model);
    new TutorialView(model, controller);
  }
}
