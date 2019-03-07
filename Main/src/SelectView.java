/**
 * @author Tom
 * Refactored for Select Mode from Slash Mode by @Morgan
 */
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import javax.imageio.ImageIO;
import javax.swing.*;

public class SelectView extends JFrame implements PropertyChangeListener {

    public static String intendedTrack = "";

    private SelectModel model;
    private SelectController controller;
    private GuitarButtonController controller2;

    private JPanel panel;

    private MainView.guitar g;
    private CarouselMenu menu;

    public SelectView(SelectModel model, SelectController controller, GuitarButtonController controller2) {
        this.model = model;
        this.model.addPropertyChangeListener(this);
        this.controller = controller;
        this.controller2 = controller2;

        this.g = new MainView.guitar(Constants.w,Constants.h);

        this.panel = new JPanel();
        this.panel.setPreferredSize(new Dimension(Constants.w,Constants.h));

        CarouselButton[] buttons = setMenu(this);
        this.model.carouselMenu = new CarouselMenu(buttons, 20, 400);
        model.addPropertyChangeListener(this.model.carouselMenu);

        this.panel.add(g);

        this.addKeyListener(controller);
        this.addKeyListener(controller2);
        this.add(panel);
        this.pack();
        this.setVisible(true);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

    }

    public void propertyChange(PropertyChangeEvent pce) {
        if (pce.getPropertyName() == null) {
            if (!model.menuOpen) {
                model.menuOpen = true;
                g.add(model.carouselMenu);
            } else if (model.menuOpen) {
                model.menuOpen = false;
                g.remove(model.carouselMenu);
            }
        }
        this.revalidate();
        this.repaint();
        this.pack();
    }


    public CarouselButton[] setMenu(JFrame frame) {
        /**
         * @author Morgan
         */
        ArrayList<File> list = inputAllFiles();

        if (list.size() < 5) {
            //TODO: Handle Carousel Menu with less than 5 buttons
        }
        else if (list.size() > 5) {
            //TODO: Handle Carousel Menu with more than 5 buttons
        }
        else {
            CarouselButton[] buttons = new CarouselButton[5];
            for (int i = 0; i < list.size(); i++) {

                String zipName = list.get(i).getName();
                String zipPath = list.get(i).getPath();

                //TODO: Carry on validation of this method from here once FileUnzipper issue is resolved
//                FileUnzipper zip = new FileUnzipper(Constants.ZIP_FILE_PATH + "/"
//                + zipName + "/" + zipName.split("\\.")[0]);
//                zip.unzipFiles(list.get(i));

                //NOTE: File Unzipper replaced with temporary solution


                File folder = new File(Constants.ZIP_FILE_PATH + "/" +
                        zipName + "/");

                ArrayList<File> unzippedList = new ArrayList<File>(Arrays.asList(folder.listFiles()));

                String imagePath = Constants.DEFAULT_WHITE_IMAGE_PATH;
                for (int j = 0; j < 2; j++) {
                    if (unzippedList.get(j).getName().split("\\.")[1].equals("jpg")) {
                        imagePath = unzippedList.get(j).getPath();
                    }
                }
                File img = new File(imagePath);

                try {
                    Image image = ImageIO.read(img);
                    Image newImage = image.getScaledInstance(
                            Constants.BUTTON_WIDTH,
                            Constants.BUTTON_HEIGHT,
                            Image.SCALE_DEFAULT
                    );

                    ImageIcon icon = new ImageIcon(newImage);
                    buttons[i] = new CarouselButton(icon, zipName) {
                        @Override
                        public void onClick() {
                            IntendedTrack.intendedTrack = zipPath;
                            JOptionPane.showMessageDialog(null,
                                    "Selected track has become:" + zipName
                                    ,"", JOptionPane.INFORMATION_MESSAGE);
                        }
                    };
                }
                catch (IOException e) {
                    System.exit(0);
                }
            }
            return buttons;
        }
        return null;
    }

    public ArrayList<File> inputAllFiles() {
        File folder = new File(Constants.ZIP_FILE_PATH + "/");
        ArrayList<File> list = new ArrayList<File>(Arrays.asList(folder.listFiles()));

//        for (int i = 0; i < list.size(); i++) {
//            int index = list.get(i).getName().lastIndexOf('.');
//            if (list.get(i).getName().substring(index) != "zip") {
//                list.remove(i);
//            }
//        }
        return list;
    }

}
