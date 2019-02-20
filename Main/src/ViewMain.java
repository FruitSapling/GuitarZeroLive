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
        g2.setColor(Color.BLACK);
        g2.fillRect(0, 0, getWidth(), getHeight());
        buttons(panel);
        guitar(g2);
        new NoteIcon(1, g2);
        menu(g2);

      }
    };

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
        g2.drawLine(125, 0, 125, getHeight());
        g2.drawLine(175, 0, 175, getHeight());
        g2.setStroke(new BasicStroke(4));
        g2.drawLine(225, 0, 225, getHeight());
        g2.setStroke(new BasicStroke(3));
        g2.setColor(Color.GRAY);
        g2.drawLine(275, 0, 275, getHeight());
        g2.setStroke(new BasicStroke(2));
        g2.drawLine(325, 0, 325, getHeight());
        g2.setStroke(new BasicStroke(2));
        g2.drawLine(375, 0, 375, getHeight());
    }

}
