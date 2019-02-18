import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Polygon;
import javax.swing.*;

import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class MainPanel extends JPanel {
  @Override
  public Dimension getPreferredSize() {
    return new Dimension(500, 750);
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
    menu.addPoint(20+getWidth()-40, getHeight()/2+100);
    menu.addPoint(20+getWidth()-40, getHeight()/2);
    g2.setClip(menu);
    g2.setColor(Color.WHITE);
    g2.fillRect(20, getHeight()/2, getWidth(), getHeight());

    g2.setClip(null);
    g2.setColor(Color.BLUE);
    g2.drawPolygon(menu);
    g2.fillPolygon(arrow2);
    g2.fillPolygon(arrow);
  }

  private JButton generateButton(String imageFileName, Graphics2D g2) {
    BufferedImage bImage;
    ImageIcon image;

    try {
      bImage = ImageIO.read(new File(imageFileName));
      image = new ImageIcon(bImage);
      JButton button = new JButton(image);
      return button;
    }
    catch(IOException IOe) {
      //TODO: Handle the exception
    }
      return null;
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
