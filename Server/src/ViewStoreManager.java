/*
 * @author Morgan Centini
 */

import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.text.View;

public class ViewStoreManager extends JFrame {
    private JFrame frame;
    private StoreManagerPanel storePanel;

    public ViewStoreManager() {
        frame = new JFrame();
        storePanel = new StoreManagerPanel() {
            public void paintComponent(Graphics g) {
                ArrayList<File> files = new ArrayList(3);
                JTextField tTitle = new JTextField(20);
                JTextField tCoverArt = new JTextField(20);
                JTextField tNotes = new JTextField(20);

                files.add(null);
                files.add(null);
                files.add(null);

                storePanel.setLayout(new BorderLayout());
                Graphics2D g2 = (Graphics2D) g;
                g2.setColor(Color.WHITE);
                g2.fillRect(0, 0, getWidth(), getHeight());
                labels(storePanel);
                textBoxes(storePanel, tTitle, tCoverArt, tNotes);
                buttons(storePanel, frame, tTitle, tCoverArt, tNotes, files);
                storePanel.revalidate();
                storePanel.repaint();
            }
        };

        frame.add(storePanel);
        frame.pack();
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation(dim.width/2-frame.getSize().width/2, dim.height/2-frame.getSize().height/2);
        frame.setVisible(true);
    }

    public JFrame getFrame() {
        return frame;
    }


    public static void main(String[] args) {
        ViewStoreManager view = new ViewStoreManager();
    }



}
