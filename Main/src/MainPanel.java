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
    arrow2.addPoint(arrowLoc, getHeight()/2+151);
    arrow2.addPoint(arrowLoc+10, getHeight()/2+141);
    arrow2.addPoint(arrowLoc+20, getHeight()/2+151);

    g2.setStroke(new BasicStroke(5));
    Polygon menu = new Polygon();
    menu.addPoint(20, getHeight()/2);
    menu.addPoint(20, getHeight()/2+152);
    menu.addPoint(20+getWidth()-50, getHeight()/2+152);
    menu.addPoint(20+getWidth()-50, getHeight()/2);
    g2.setClip(menu);
    g2.setColor(Color.WHITE);
    g2.fillRect(20, getHeight()/2, getWidth(), getHeight());

    g2.setClip(null);
    g2.setColor(Color.CYAN);
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



  public void buttons(MainPanel panel){
    JButton exit = generateButton("Main/src/resources/exit.png");
    exit.setBackground(Color.WHITE);
    exit.setBorderPainted(false);
    exit.addMouseListener( new MouseAdapter() {
      public void mouseClicked(MouseEvent e) {
        System.exit(0);
      }
      public void mouseEntered(MouseEvent e) {
        //TODO: Add code for showing which mode this button represents
      }
    });

    JButton select = generateButton("Main/src/resources/select.png");
    select.setBackground(Color.WHITE);
    select.setBorderPainted(false);
    select.addMouseListener( new MouseAdapter() {
      public void mouseClicked(MouseEvent e) {
        //TODO: Add code for changing GUI to select mode
      }
      public void mouseEntered(MouseEvent e) {
        //TODO: Add code for showing which mode this button represents
      }
    });

    JButton play = generateButton("Main/src/resources/play.png");
    play.setBackground(Color.WHITE);
    play.setBorderPainted(false);
    play.addMouseListener( new MouseAdapter() {
      public void mouseClicked(MouseEvent e) {
        //TODO: Add code for changing GUI to play mode
      }
      public void mouseEntered(MouseEvent e) {
        //TODO: Add code for showing which mode this button represents
      }
    });

    JButton store = generateButton("Main/src/resources/store.png");
    store.setBackground(Color.WHITE);
    store.setBorderPainted(false);
    store.addMouseListener( new MouseAdapter() {
      public void mouseClicked(MouseEvent e) {
        //TODO: Add code for changing GUI to store mode
      }
      public void mouseEntered(MouseEvent e) {
        //TODO: Add code for showing which mode this button represents
      }
    });

    JButton tutorial = generateButton("Main/src/resources/tutorial.png");
    tutorial.setBackground(Color.WHITE);
    tutorial.setBorderPainted(false);
    tutorial.addMouseListener( new MouseAdapter() {
      public void mouseClicked(MouseEvent e) {
        //TODO: Add code for changing GUI to tutorial mode
      }
      public void mouseEntered(MouseEvent e) {
        //TODO: Add code for showing which mode this button represents
      }
    });

    exit.setBounds(25, 388, 128, 128); panel.add(exit);
    select.setBounds(153, 388, 128, 128); panel.add(select);
    play.setBounds(281, 388, 128, 128); panel.add(play);
    store.setBounds(409, 388, 128, 128); panel.add(store);
    tutorial.setBounds(537, 388, 128, 128); panel.add(tutorial);

//    JLabel modeInfo = new JLabel("Welcome!");
//    modeInfo.setOpaque(true);
//    modeInfo.setBackground(Color.WHITE);
//    modeInfo.setFont(modeInfo.getFont().deriveFont(24f));
//    modeInfo.setBounds(300, 60, 128, 128); panel.add(modeInfo);

    panel.revalidate();
    panel.repaint();
  }

}
