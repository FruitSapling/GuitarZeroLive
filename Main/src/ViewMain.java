import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.Dimension;
import javax.swing.*;

public class ViewMain extends JFrame {
  private MainPanel panel;

  public ViewMain() {
    panel = new MainPanel() {
      public void paintComponent(Graphics g) {
        panel.setLayout(null);
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(Color.BLACK);
        g2.fillRect(0, 0, getWidth(), getHeight());
        guitar(g2);
        new NoteIcon(1, g2);
        menu(g2);

        ImageIcon icon = new ImageIcon("Main/src/exit.png");
        JButton button = new JButton(icon);

        Dimension size;

        Insets inset = panel.getInsets();
        size = button.getPreferredSize();
        button.setBounds(25 + inset.left, 5 + inset.top,
                size.width, size.height);

        panel.add(button);
        panel.revalidate();
        panel.repaint();

      }
    };

    this.add(panel);
    this.pack();
    this.setDefaultCloseOperation(EXIT_ON_CLOSE);
    this.setVisible(true);
  }


  //Pseudo Code for Carousel Menu
  /*
  public CarouselMain() {
      JFrame carousel = new JFrame("Slash Mode");
      carousel.setSize((128*5), 128);
      //carousel.setLocation(0,0); //To be determined

      ImageIcon exit = new ImageIcon("exit.png");
      ImageIcon select = new ImageIcon("select.png");
      ImageIcon play = new ImageIcon("play.png");
      ImageIcon store = new ImageIcon("store.png");
      ImageIcon tutorial = new ImageIcon("tutorial.png");
	  
	  JButton exitButton = new JButton(exit);
	  JButton selectButton = new JButton(select);
	  JButton playButton = new JButton(play);
	  JButton storeButton = new JButton(store);
	  JButton tutorialButton = new JButton(tutorial);
	  
	  carousel.add(exitButton);
	  carousel.add(selectButton);
	  carousel.add(playButton);
	  carousel.add(storeButton);
	  carousel.add(tutorialButton);
  }
  */
}
