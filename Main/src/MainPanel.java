import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.*;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MainPanel extends JPanel implements KeyListener, GuitarButtonListener {

  private int arrowLoc = 336;
  private int selectedMode = 2;

  public MainPanel(GuitarButtonController guitarButtonController) {
    //register this panel as a guitar button press listener
    guitarButtonController.addListener(this);
  }

  public void keyTyped(KeyEvent e) {
    //System.out.println("keyTyped: "+e);
  }
  public void keyPressed(KeyEvent e) {
    if(e.getKeyChar() == 'd') {
      movePointerRight();
    }else if(e.getKeyChar() == 'e') {
      movePointerLeft();
    }
  }

  public void movePointerLeft() {
    if(arrowLoc > 20+128) {
      arrowLoc -= 128;
      selectedMode -= 1;
    }
  }

  public void movePointerRight() {
    if(arrowLoc < 20+getWidth()-50-128) {
      arrowLoc += 128;
      selectedMode += 1;
    }
  }

  public void selectMode(int selectedMode) {
    switch(selectedMode) {
      case 0:
        System.exit(0);
        break;
      case 1:
        break;
      case 2:
        break;
      case 3:
        //TODO: Adjust this for when Store Mode is created
        ViewStoreManager store = new ViewStoreManager();
        break;
      case 4:
        break;
    }
  }

  @Override
  public void guitarButtonPressReceived(GuitarButtonPressedEvent e) {
    switch(e.getGuitarButton()) {
      case STRUM:
        if (e.getValue() == 1.0) {
          movePointerLeft();
        } else {
          movePointerRight();
        }
        break;
      case ZERO_POWER:
        selectMode(selectedMode);
    }
  }

  public void keyReleased(KeyEvent e) {
    //System.out.println("keyReleased: "+e);
  }

  @Override
  public Dimension getPreferredSize() {
    return new Dimension(700, 750);
  }

  public void menu(Graphics2D g2) {
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

  public JButton generateButton(String imageFileName, int mode) {
    ImageIcon image = new ImageIcon(imageFileName);
    JButton button = new JButton(image);
    button.setBackground(Color.WHITE);
    button.setBorderPainted(false);

    button.addMouseListener( new MouseAdapter() {
        public void mouseClicked(MouseEvent e) {
          selectMode(mode);
        }
    });
    return button;
  }

  public void buttons(MainPanel panel){
    JButton exit = generateButton("Main/src/resources/exit.png", 0);
    JButton play = generateButton("Main/src/resources/play.png", 1);
    JButton select = generateButton("Main/src/resources/select.png", 2);
    JButton store = generateButton("Main/src/resources/store.png", 3);
    JButton tutorial = generateButton("Main/src/resources/tutorial.png", 4);

    exit.setBounds(25, 388, 128, 168); panel.add(exit);
    play.setBounds(281, 388, 128, 168); panel.add(play);
    select.setBounds(153, 388, 128, 168); panel.add(select);
    store.setBounds(409, 388, 128, 168); panel.add(store);
    tutorial.setBounds(537, 388, 128, 168); panel.add(tutorial);

    panel.revalidate();
    panel.repaint();
  }
}
