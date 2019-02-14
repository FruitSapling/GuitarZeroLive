import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JFrame;

public class ViewMain extends JFrame {
  private MainPanel panel;

  public ViewMain() {
    panel = new MainPanel() {
      public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(Color.BLACK);
        g2.fillRect(0,0, getWidth(), getHeight());
        guitar(g2);
        menu(g2);
      }
    };
    this.add(panel);
    this.pack();
    this.setDefaultCloseOperation(EXIT_ON_CLOSE);
    this.setVisible(true);
  }
}
