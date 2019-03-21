import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Arrays;
import java.util.Collections;
import java.util.stream.IntStream;
import javax.swing.JLayeredPane;

/**
 * CarouselMenu holds a number of CarouselButtons, which can be cycled through and selected.
 * To use this menu, create a CarouselMenu and register it as a PropertyChangeListener.
 * It will listen for the property name "cycleCarousel" to cycle the carousel, and "selectMode"
 * to call the CarouselButton's "onClick" method.
 *
 * @author Willem
 * Morgan - Contributed the maths within cycleLeft & cycleRight to handle more than 5 buttons
 *
 */
public class CarouselMenu extends JLayeredPane implements PropertyChangeListener {

  public CarouselButton[] buttons;
  public CarouselButton[] tempArray;
  int xPos, yPos;

  public int farLeft;
  public int farRight;

  public CarouselMenu(CarouselButton[] buttons, int xPos, int yPos) {
    this.buttons = buttons;
    this.tempArray = new CarouselButton[5];
    this.farLeft = 0;
    this.farRight = 4;
    this.xPos = xPos;
    this.yPos = yPos;
    this.setBounds(20, 100, 100, 100);
    this.setSize(new Dimension(800, 150));
    this.setVisible(true);
    FlowLayout flowLayout = new FlowLayout();
    flowLayout.setHgap(0);
    flowLayout.setVgap(yPos+10);
    this.setLayout(flowLayout);
    this.addButtons(buttons);
    this.cycleLeft();
    this.cycleRight();
    this.revalidate();
  }

  // Cycle the carousel to the right
  public void cycleRight() {
      //farLeft and farRight hold expected array indexes, in order to allow for carousel menu movement off screen
    if (farLeft == 0) { farLeft = buttons.length-1; }
    else { farLeft = farLeft-1; }

    if (farRight == 0) { farRight = buttons.length-1; }
    else { farRight = farRight-1; }

    int left = farLeft; int right = farRight;
    int count = 0;

    //Fill the placeholder array with the newly sourced correct indexes
    for (int i = left; count < 5; i++) {
      try { tempArray[count] = buttons[i]; }
      catch (ArrayIndexOutOfBoundsException e) { //if we back around to 0, adjust i to avoid an exception
        i = 0;
        tempArray[count] = buttons[i];
      }
      count++;
    }
    this.addButtons(tempArray);
    tempArray[2].onHighlight();
  }

  // Cycle the carousel to the left
  public void cycleLeft() {
      //farLeft and farRight hold expected array indexes, in order to allow for carousel menu movement off screen
    if (farLeft == buttons.length) { farLeft = 0; }
    else { farLeft = farLeft+1; }

    if (farRight == buttons.length) { farRight = 0; }
    else { farRight = farRight+1; }

    int left = farLeft; int right = farRight;
    int count = 0;
      //Fill the placeholder array with the newly sourced correct indexes
    for (int i = left; count < 5; i++) {
      try { tempArray[count] = buttons[i]; }
      catch (ArrayIndexOutOfBoundsException e) { //if we back around to 0, adjust i to avoid an exception
        i = 0;
        tempArray[count] = buttons[i];
      }
      count++;
    }
    this.addButtons(tempArray);
    tempArray[2].onHighlight();
  }

  public void selectMode() {
    tempArray[2].onClick();
  }

  public void setButtons(CarouselButton[] buttons) {
    this.buttons = buttons;
  }

  // This method draws the menu surrounding the carousel items, including the arrows.
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

  public void addButtons(CarouselButton[] tempArray) {
    this.removeAll();
    for (int i = 4; i >= 0; i -= 1) {
      this.add(tempArray[i], 0);
    }
  }

  // Listen for propertyChange events
  @Override
  public void propertyChange(PropertyChangeEvent evt) {
    //assuming change in carousel items order
    if (evt.getPropertyName() != null) {
      if (evt.getPropertyName().equals("cycleCarousel")) {
        if (evt.getNewValue().equals("left")) {
          this.cycleLeft();
        } else {
          this.cycleRight();
        }
      } else if (evt.getPropertyName().equals("selectMode")) {
        this.selectMode();
      }
    }
    this.revalidate();
    this.repaint();
  }
}
