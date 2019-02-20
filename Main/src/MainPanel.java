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

public class MainPanel extends JPanel implements KeyListener {
  int arrowLoc = 336;
  public void keyTyped(KeyEvent e) {
    //System.out.println("keyTyped: "+e);
  }
  public void keyPressed(KeyEvent e) {
    if(e.getKeyChar() == 'd') {
      if(arrowLoc < 20+getWidth()-50-128) {
        arrowLoc += 128;
      }
    }else if(e.getKeyChar() == 'e') {
      if(arrowLoc > 20+128) {
        arrowLoc -= 128;
      }
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
            switch(mode) {
                case 0: System.exit(0);
                case 3: ViewStore store = new ViewStore();
            }
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
