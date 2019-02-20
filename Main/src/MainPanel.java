import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Polygon;
import javax.swing.*;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MainPanel extends JPanel {
  @Override
  public Dimension getPreferredSize() {
    return new Dimension(700, 750);
  }

  public void menu(Graphics2D g2) {
    Polygon arrow = new Polygon();
    arrow.addPoint(getWidth()/2-14, getHeight()/2+2);
    arrow.addPoint(getWidth()/2-4, getHeight()/2+12);
    arrow.addPoint(getWidth()/2+6, getHeight()/2+2);

    Polygon arrow2 = new Polygon();
    arrow2.addPoint(getWidth()/2-14, getHeight()/2+151);
    arrow2.addPoint(getWidth()/2-4, getHeight()/2+141);
    arrow2.addPoint(getWidth()/2+6, getHeight()/2+151);

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

  public JButton generateButton(String imageFileName, int mode) {
    ImageIcon image = new ImageIcon(imageFileName);
    JButton button = new JButton(image);
    button.setBackground(Color.WHITE);
    button.setBorderPainted(false);

    button.addMouseListener( new MouseAdapter() {
        public void mouseClicked(MouseEvent e) {
            switch(mode) {
                case 0: System.exit(0);
                case 1: System.out.println("");//TODO: Play Mode
                case 2: System.out.println("");//TODO: Store Mode
                case 3: System.out.println("");//TODO: Store Mode
                case 4: System.out.println("");//TODO: Tutorial Mode
            }
        }
    });
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
    JButton exit = generateButton("Main/src/resources/exit.png", 0);
    JButton play = generateButton("Main/src/resources/play.png", 1);
    JButton select = generateButton("Main/src/resources/select.png", 2);
    JButton store = generateButton("Main/src/resources/store.png", 3);
    JButton tutorial = generateButton("Main/src/resources/tutorial.png", 4);

    exit.setBounds(25, 388, 128, 128); panel.add(exit);
    play.setBounds(281, 388, 128, 128); panel.add(play);
    select.setBounds(153, 388, 128, 128); panel.add(select);
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
