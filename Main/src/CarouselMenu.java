import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;

public class CarouselMenu extends JLayeredPane {

  JLayeredPane layeredPane;

  public CarouselMenu() {
    this.setSize(new Dimension(700, 150));
    this.setVisible(true);
  }

  @Override
  public void paintComponent(Graphics g) {
    Graphics2D g2 = (Graphics2D) g;
    int arrowLoc = 336;

    Polygon arrow = new Polygon();
    arrow.addPoint(arrowLoc, getHeight()/2+2);
    arrow.addPoint(arrowLoc+10, getHeight()/2+12);
    arrow.addPoint(arrowLoc+20, getHeight()/2+2);

    Polygon arrow2 = new Polygon();
    arrow2.addPoint(arrowLoc, getHeight()/2+191);
    arrow2.addPoint(arrowLoc+10, getHeight()/2+181);
    arrow2.addPoint(arrowLoc+20, getHeight()/2+191);

    g2.setStroke(new BasicStroke(5));
    Polygon menu = new Polygon();
    menu.addPoint(20, getHeight()/2);
    menu.addPoint(20, getHeight()/2+192);
    menu.addPoint(20+getWidth()-50, getHeight()/2+192);
    menu.addPoint(20+getWidth()-50, getHeight()/2);
    g2.setClip(menu);
    g2.setColor(Color.WHITE);
    g2.fillRect(20, getHeight()/2, getWidth(), getHeight());

    g2.setClip(null);
    g2.setColor(Color.BLUE);
    g2.drawPolygon(menu);
    g2.fillPolygon(arrow2);
    g2.fillPolygon(arrow);
  }

}
