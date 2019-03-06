/**
 * @author Morgan Centini
 */

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.io.File;

public class StoreManagerPanel extends JPanel {

    private void setTextField(JTextField tField, String str) {
        tField.setText(str);
        tField.revalidate();
        tField.repaint();
    }

    //Boolean used to determine whether zipping a bundle should be attempted upon exiting this GUI
    private boolean areAllFilesSelected(ArrayList<File> arrayList) {
        for (File file : arrayList) {
            if (file == null){
                return false;
            }
        }
        return true;
    }

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

    protected void textBoxes(StoreManagerPanel panel, JTextField tTitle, JTextField tCoverArt, JTextField tNotes){
        // Function generates and positions labels onto the SMM GUI

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

    protected JButton generateFileBrowserButton(StoreManagerPanel panel, JTextField field, String mode,
                                                ArrayList<File> files) {
        // Function generates buttons to be placed on GUI, with generic file browser event handling
        JButton button = new JButton("Browse...");
        button.addMouseListener( new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                File file = fileBrowser(panel, mode);
                if (file.exists()) {
                    if (mode == "png"){ files.set(0, file); }
                    if (mode == "midi"){ files.set(1, file); }
                    if (mode == "txt"){ files.set(2, file); }
                    setTextField(field, file.getName());
                }
            }
        });
        return button;
    }

    public File fileBrowser(JPanel panel, String mode) {

        File file;
        JFileChooser fb = new JFileChooser();

        if (mode == "png") { fb.setFileFilter(new FileNameExtensionFilter("PNG File", "png")); }
        if (mode == "midi") { fb.setFileFilter(new FileNameExtensionFilter("MIDI File", "mid", "midi")); }

        int returnVal = fb.showOpenDialog(panel);

        if (returnVal == JFileChooser.APPROVE_OPTION) {

            file = fb.getSelectedFile();
            return file;
        }
        return null;
    }

    protected void buttons(StoreManagerPanel panel, JFrame frame, JTextField tTitle, JTextField tCoverArt, JTextField tNotes,
                           ArrayList<File> files){
        // Function generates and positions buttons onto the SMM GUI
        JButton titleButton = generateFileBrowserButton(panel, tTitle, "png", files);
        JButton artButton = generateFileBrowserButton(panel, tCoverArt, "midi", files);
        JButton musicButton = generateFileBrowserButton(panel, tNotes, "txt", files);

        // Generate Exit Button which destroys SMM GUI
        JButton saveExit = new JButton("Save");
        saveExit.addMouseListener( new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                boolean allFiles = areAllFilesSelected(files);
                if (allFiles) {
                    boolean valid = FileZipper.createZipFile(files);
                    if (valid == true) {
                        JOptionPane.showMessageDialog(null, "Zip Bundle successfully created!"
                                ,"", JOptionPane.INFORMATION_MESSAGE);
                    }
                    else {
                        JOptionPane.showMessageDialog(null, "There was an error creating the bundle!"
                                ,"Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
                else {
                    JOptionPane.showMessageDialog(null, "All required files not provided: no action taken."
                            ,"Contents", JOptionPane.INFORMATION_MESSAGE);
                }
                panel.setVisible(false);
                frame.dispose();
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

    }
}

