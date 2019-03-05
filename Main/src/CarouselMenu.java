/*
  CarouselMenu is a class, intended as a pluggable component to show a carousel menu.
 */

/* Primary Class Developer: Willem van Gerwen */

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import javax.swing.JButton;
import javax.swing.JLayeredPane;

public class CarouselMenu extends JLayeredPane {

  CarouselButton[] buttons;
  int xPos, yPos;

  public CarouselMenu(CarouselButton[] buttons, int xPos, int yPos) {
    this.buttons = buttons;
    this.xPos = xPos;
    this.yPos = yPos;
    this.setBounds(20, 100, 100, 100);
    this.setPreferredSize(new Dimension(800, 150));
    this.setVisible(true);
    FlowLayout flowLayout = new FlowLayout();
    flowLayout.setHgap(0);
    flowLayout.setVgap(yPos+10);
    this.setLayout(flowLayout);
    this.setButtons();
    this.revalidate();
  }

  public void cycleRight() {
    CarouselButton last = buttons[buttons.length-1];
    System.arraycopy(buttons, 0, buttons, 1, buttons.length-1 );
    buttons[0] = last;
    this.setButtons();
  }

  public void cycleLeft() {
    CarouselButton first = buttons[0];
    System.arraycopy(buttons, 1, buttons, 0, buttons.length-1 );
    buttons[buttons.length-1] = first;
    this.setButtons();
  }

  public void selectMode() {
    buttons[2].onClick();
  }

  public void setButtons(CarouselButton[] buttons) {
    this.buttons = buttons;
  }

  @Override
  public void paintComponent(Graphics g) {
    super.paintComponent(g);

    Graphics2D g2 = (Graphics2D) g;
    int arrowLoc = (getWidth()/2)+5;

    Polygon arrow = new Polygon();
    arrow.addPoint(arrowLoc-15, yPos+2);
    arrow.addPoint(arrowLoc-5, yPos+12);
    arrow.addPoint(arrowLoc+5, yPos+2);

    Polygon arrow2 = new Polygon();
    arrow2.addPoint(arrowLoc-15, yPos+191);
    arrow2.addPoint(arrowLoc-5, yPos+181);
    arrow2.addPoint(arrowLoc+5, yPos+191);

    g2.setStroke(new BasicStroke(5));
    Polygon menu = new Polygon();
    menu.addPoint(xPos, yPos);
    menu.addPoint(xPos, yPos+192);
    menu.addPoint(xPos+getWidth()-50, yPos+192);
    menu.addPoint(xPos+getWidth()-50, yPos);
    g2.setClip(menu);
    g2.setColor(Color.WHITE);
    g2.fillRect(xPos, yPos, getWidth(), yPos+192);

    g2.setClip(null);
    g2.setColor(Color.BLUE);
    g2.drawPolygon(menu);
    g2.fillPolygon(arrow2);
    g2.fillPolygon(arrow);
  }

  public void setButtons() {
    for (int i = buttons.length-1; i >= 0; i -= 1) {
      this.add(buttons[i], 2);
    }
  }

}
