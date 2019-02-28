import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
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
                labels(storePanel);
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

        public void labels(StoreManagerPanel panel) {
            JLabel header = new JLabel("Store Manager Mode");
            header.setHorizontalAlignment(JLabel.CENTER);
            header.setVerticalAlignment(JLabel.CENTER);
            header.setFont(new Font("Arial", Font.PLAIN, 32));

            JLabel lTitle = new JLabel("Title:");                 
            JLabel lCoverArt = new JLabel("Cover Art:");
            JLabel lNotes = new JLabel("Music:");

            lTitle.setFont(new Font("Arial", Font.PLAIN, 18));
            lCoverArt.setFont(new Font("Arial", Font.PLAIN, 18));
            lNotes.setFont(new Font("Arial", Font.PLAIN, 18));

            JPanel centrePanel = new JPanel();
            GridBagLayout gridBag = new GridBagLayout();
            GridBagConstraints c = new GridBagConstraints();
            centrePanel.setLayout(gridBag);
            centrePanel.setBackground(Color.WHITE);
            c.fill = GridBagConstraints.HORIZONTAL;
            c.gridx = 0;
            c.insets = new Insets(12, 0, 12, 0);

            c.gridy = 0;
            gridBag.setConstraints(lTitle, c);
            c.gridy = 1;
            gridBag.setConstraints(lCoverArt, c);
            c.gridy = 2;
            gridBag.setConstraints(lNotes, c);

            centrePanel.add(lTitle);
            centrePanel.add(lCoverArt);
            centrePanel.add(lNotes);

            panel.add(centrePanel, BorderLayout.CENTER);
            panel.add(header, BorderLayout.NORTH);
        }

        public JButton generateFileBrowserButton(StoreManagerPanel panel, int mode) {
            JButton button = new JButton("Browse...");
            button.addMouseListener( new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    FileZipper.fileBrowser(panel, mode);
                }
            });
            return button;
        }

        public void buttons(StoreManagerPanel panel){

            JButton titleButton = generateFileBrowserButton(panel, 1);
            JButton artButton = generateFileBrowserButton(panel, 2);
            JButton musicButton = generateFileBrowserButton(panel, 3);

            JButton saveExit = new JButton("Save");
            saveExit.addMouseListener( new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    FileZipper.createZipFile();
                    panel.setVisible(false);
                    dispose();
                }
            });
            saveExit.setPreferredSize(new Dimension(400, 50));

            JPanel eastPanel = new JPanel();
            GridBagLayout gridBag = new GridBagLayout();
            GridBagConstraints c = new GridBagConstraints();
            eastPanel.setLayout(gridBag);
            eastPanel.setBackground(Color.WHITE);
            c.fill = GridBagConstraints.HORIZONTAL;
            c.gridx = 0;
            c.insets = new Insets(10, 0, 10, 0);

            c.gridy = 0;
            gridBag.setConstraints(titleButton, c);
            c.gridy = 1;
            gridBag.setConstraints(artButton, c);
            c.gridy = 2;
            gridBag.setConstraints(musicButton, c);

            eastPanel.add(titleButton);
            eastPanel.add(artButton);
            eastPanel.add(musicButton);

            panel.add(eastPanel, BorderLayout.EAST);
            panel.add(saveExit, BorderLayout.SOUTH);

            panel.revalidate();
            panel.repaint();
        }
    }
}
