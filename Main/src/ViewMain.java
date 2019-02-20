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

    public void guitar(Graphics2D g2) {
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

        g2.setColor(Color.orange);
        g2.drawLine(150, 0, 150, getHeight());
        g2.drawLine(235, 0, 235, getHeight());
        g2.setStroke(new BasicStroke(4));
        g2.drawLine(320, 0, 320, getHeight());
        g2.setStroke(new BasicStroke(3));
        g2.setColor(Color.GRAY);
        g2.drawLine(405, 0, 405, getHeight());
        g2.setStroke(new BasicStroke(2));
        g2.drawLine(490, 0, 490, getHeight());
        g2.setStroke(new BasicStroke(2));
        g2.drawLine(575, 0, 575, getHeight());
    }

}
