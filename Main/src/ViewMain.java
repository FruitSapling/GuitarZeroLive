import java.awt.*;
import javax.swing.*;
import java.awt.event.MouseAdapter;


public class ViewMain extends JFrame {
  private MainPanel panel;

  public ViewMain() {
    panel = new MainPanel() {
      public void paintComponent(Graphics g) {
        panel.setLayout(null);
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(Color.DARK_GRAY);
        g2.fillRect(0, 0, getWidth(), getHeight());
        buttons(panel);
        guitar(g2);
        new NoteIcon(1, g2);
        menu(g2);

      }
    };
    panel.addKeyListener(panel);
    panel.setFocusable(true);

    this.add(panel);
    this.pack();
    this.setDefaultCloseOperation(EXIT_ON_CLOSE);
    this.setVisible(true);
  }

}
