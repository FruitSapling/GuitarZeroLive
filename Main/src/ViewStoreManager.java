/* GUI Class that handles the implementation of Store Manager Mode */
/* Store Manager Mode is a pseudo mode allowing for maintenance of */
/* the Store Mode, specifically the creation of track 'bundles'    */

/* Primary Class Developer: Morgan Centini */
/* Secondary Class Developers:             */

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;

public class ViewStoreManager extends JFrame {
    private StoreManagerPanel storePanel;

    public ViewStoreManager() {
        storePanel = new StoreManagerPanel() {
            public void paintComponent(Graphics g) {
                storePanel.setLayout(new BorderLayout());
                Graphics2D g2 = (Graphics2D) g;
                g2.setColor(Color.WHITE);
                g2.fillRect(0, 0, getWidth(), getHeight());
                labels(storePanel);
                textBoxes(storePanel);
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

        //Boolean used to determine whether zipping a bundle should be attempted upon exiting this GUI
        private boolean allFilesSelected = false;

        @Override
        public Dimension getPreferredSize() {
            return new Dimension(400, 250);
        }

        protected void labels(StoreManagerPanel panel) {
        // Function generates and positions labels onto the SMM GUI
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

            //Internal Layout placed upon primary layout in order to position labels adequately
            JPanel westPanel = new JPanel();
            GridBagLayout gridBag = new GridBagLayout();
            GridBagConstraints c = new GridBagConstraints();
            westPanel.setLayout(gridBag);
            westPanel.setBackground(Color.WHITE);
            c.fill = GridBagConstraints.HORIZONTAL;
            c.gridx = 0;
            c.insets = new Insets(12, 5, 12, 0);

            c.gridy = 0;
            gridBag.setConstraints(lTitle, c);
            c.gridy = 1;
            gridBag.setConstraints(lCoverArt, c);
            c.gridy = 2;
            gridBag.setConstraints(lNotes, c);

            westPanel.add(lTitle);
            westPanel.add(lCoverArt);
            westPanel.add(lNotes);

            panel.add(westPanel, BorderLayout.WEST);
            panel.add(header, BorderLayout.NORTH);
        }

        protected void textBoxes(StoreManagerPanel panel){
            // Function generates and positions labels onto the SMM GUI
            JTextField tTitle = new JTextField(20);
            JTextField tCoverArt = new JTextField(20);
            JTextField tNotes = new JTextField(20);

            tTitle.setEditable(false);
            tCoverArt.setEditable(false);
            tNotes.setEditable(false);

            //Internal Layout placed upon primary layout in order to position labels adequately
            JPanel centrePanel = new JPanel();
            GridBagLayout gridBag = new GridBagLayout();
            GridBagConstraints c = new GridBagConstraints();
            centrePanel.setLayout(gridBag);
            centrePanel.setBackground(Color.WHITE);
            c.fill = GridBagConstraints.HORIZONTAL;
            c.gridx = 0;
            c.insets = new Insets(13, 0, 13, 0);

            c.gridy = 0;
            gridBag.setConstraints(tTitle, c);
            c.gridy = 1;
            gridBag.setConstraints(tCoverArt, c);
            c.gridy = 2;
            gridBag.setConstraints(tNotes, c);

            centrePanel.add(tTitle);
            centrePanel.add(tCoverArt);
            centrePanel.add(tNotes);

            panel.add(centrePanel, BorderLayout.CENTER);
        }

        protected JButton generateFileBrowserButton(StoreManagerPanel panel, int mode) {
        // Function generates buttons to be placed on GUI, with generic file browser event handling
            JButton button = new JButton("Browse...");
            button.addMouseListener( new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    FileZipper.fileBrowser(panel, mode);
                }
            });
            return button;
        }

        protected void buttons(StoreManagerPanel panel){
        // Function generates and positions buttons onto the SMM GUI
            JButton titleButton = generateFileBrowserButton(panel, 1);
            JButton artButton = generateFileBrowserButton(panel, 2);
            JButton musicButton = generateFileBrowserButton(panel, 3);

            // Generate Exit Button which destroys SMM GUI
            JButton saveExit = new JButton("Save");
            saveExit.addMouseListener( new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    if (allFilesSelected == true) {
                        try {
                            FileZipper.createZipFile();
                        }
                        catch(Exception ex) {
                            JOptionPane.showMessageDialog(null, "There was an error creating the bundle!"
                                    ,"Error", JOptionPane.ERROR_MESSAGE);
                        }
                        finally {
                            JOptionPane.showMessageDialog(null, "Zip Bundle successfully created!"
                                    ,"", JOptionPane.INFORMATION_MESSAGE);
                        }
                    }
                    JOptionPane.showMessageDialog(null, "All required files not provided: no action taken."
                            ,"Contents", JOptionPane.INFORMATION_MESSAGE);
                    panel.setVisible(false);
                    dispose();
                }
            });
            saveExit.setPreferredSize(new Dimension(400, 50));

            //Internal Layout placed upon primary layout in order to position buttons adequately
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
