/* GUI Class that handles the implementation of Store Manager Mode */
/* Store Manager Mode is a pseudo mode allowing for maintenance of */
/* the Store Mode, specifically the creation of track 'bundles'    */

/* Primary Class Developer: Morgan Centini */
/* Secondary Class Developers:             */

import java.awt.*;
import javax.swing.*;

public class ViewStoreManager extends JFrame {
    private JFrame frame;
    private StoreManagerPanel storePanel;

    public ViewStoreManager() {
        frame = new JFrame();
        storePanel = new StoreManagerPanel() {
            public void paintComponent(Graphics g) {
                JTextField tTitle = new JTextField(20);
                JTextField tCoverArt = new JTextField(20);
                JTextField tNotes = new JTextField(20);

                storePanel.setLayout(new BorderLayout());
                Graphics2D g2 = (Graphics2D) g;
                g2.setColor(Color.WHITE);
                g2.fillRect(0, 0, getWidth(), getHeight());
                labels(storePanel);
                textBoxes(storePanel, tTitle, tCoverArt, tNotes);
                buttons(storePanel, frame, tTitle, tCoverArt, tNotes);
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



}
