import javax.swing.JFrame;
import javax.swing.JPanel;

public class ViewMain extends JFrame {
  private JPanel panel;

  public ViewMain() {
    panel = new JPanel();

    this.add(panel);
    this.pack();
    this.setSize(500, 1000);
  }
}
