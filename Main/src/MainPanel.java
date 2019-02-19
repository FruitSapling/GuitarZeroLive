import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Polygon;
import javax.swing.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainPanel extends JPanel {
  @Override
  public Dimension getPreferredSize() {
    return new Dimension(660, 750);
  }

  public void menu(Graphics2D g2) {
    Polygon arrow = new Polygon();
    arrow.addPoint(getWidth()/2-10, getHeight()/2+2);
    arrow.addPoint(getWidth()/2, getHeight()/2+12);
    arrow.addPoint(getWidth()/2+10, getHeight()/2+2);

    Polygon arrow2 = new Polygon();
    arrow2.addPoint(getWidth()/2-10, getHeight()/2+98);
    arrow2.addPoint(getWidth()/2, getHeight()/2+86);
    arrow2.addPoint(getWidth()/2+10, getHeight()/2+98);

    g2.setStroke(new BasicStroke(5));
    Polygon menu = new Polygon();
    menu.addPoint(20, getHeight()/2);
    menu.addPoint(20, getHeight()/2+100);
    menu.addPoint(20+getWidth()-20, getHeight()/2+100);
    menu.addPoint(20+getWidth()-20, getHeight()/2);
    g2.setClip(menu);
    g2.setColor(Color.WHITE);
    g2.fillRect(20, getHeight()/2, getWidth(), getHeight());

    g2.setClip(null);
    g2.setColor(Color.BLUE);
    g2.drawPolygon(menu);
    g2.fillPolygon(arrow2);
    g2.fillPolygon(arrow);
  }

  public JButton generateButton(String imageFileName) {
    ImageIcon image = new ImageIcon(imageFileName);
    JButton button = new JButton(image);
    return button;
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

  public void buttons(MainPanel panel){
    JButton exit = generateButton("Main/src/resources/exit.png");
    exit.addActionListener(new ActionListener(){
      public void actionPerformed(ActionEvent e){
        System.exit(0);
      }
    });

    JButton select = generateButton("Main/src/resources/select.png");
    JButton play = generateButton("Main/src/resources/play.png");
    JButton store = generateButton("Main/src/resources/store.png");
    JButton tutorial = generateButton("Main/src/resources/tutorial.png");

    exit.setBounds(20, 350, 128, 128);
    select.setBounds(148, 350, 128, 128);
    play.setBounds(276, 350, 128, 128);
    store.setBounds(404, 350, 128, 128);
    tutorial.setBounds(532, 350, 128, 128);

    panel.add(exit);
    panel.add(select);
    panel.add(play);
    panel.add(store);
    panel.add(tutorial);

    panel.revalidate();
    panel.repaint();
  }
}
