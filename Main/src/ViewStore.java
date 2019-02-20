import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;

public class ViewStore extends JFrame {
    private StoreManagerPanel storePanel;

    public ViewStore() {
        storePanel = new StoreManagerPanel() {
            public void paintComponent(Graphics g) {
                storePanel.setLayout(new BorderLayout());
                Graphics2D g2 = (Graphics2D) g;
                g2.setColor(Color.WHITE);
                g2.fillRect(0, 0, getWidth(), getHeight());
                buttons(storePanel);
            }
        };

        this.add(storePanel);
        this.pack();
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
        this.setVisible(true);
    }

    class StoreManagerPanel extends JPanel {

        @Override
        public Dimension getPreferredSize() {
            return new Dimension(400, 250);
        }

        public void buttons(StoreManagerPanel panel){
            JButton saveExit = new JButton("Save");
            saveExit.addMouseListener( new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    panel.setVisible(false);
                    dispose();
                }
            });

            panel.add(saveExit, BorderLayout.SOUTH);

            panel.revalidate();
            panel.repaint();
        }
    }
}
