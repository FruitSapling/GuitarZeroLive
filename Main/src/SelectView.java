
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import javax.imageio.ImageIO;
import javax.swing.*;

/**
 * @author Tom
 * Refactored for Select Mode from Slash Mode by @Morgan
 */

public class SelectView extends JFrame implements PropertyChangeListener {

    public static String intendedTrack = "";

    private SelectModel model;
    private SelectController controller;
    private MainGuitarController controller2;

    private JPanel panel;

    private MainView.Guitar g;

    private CarouselMenu menu;

    public SelectView(SelectModel model, SelectController controller, MainGuitarController controller2) {
        this.model = model;
        this.model.addPropertyChangeListener(this);
        this.controller = controller;
        this.controller2 = controller2;

        this.g = new MainView.Guitar(Constants.w,Constants.h);

        this.panel = new JPanel();
        this.panel.setPreferredSize(new Dimension(Constants.w,Constants.h));

        CarouselButton[] buttons = setMenu(this);
        this.menu = new CarouselMenu(buttons, 20, 400);
        this.model.addPropertyChangeListener(menu);

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
                g.add(menu);
            } else if (model.menuOpen) {
                model.menuOpen = false;
                g.remove(menu);
            }
        }
        this.revalidate();
        this.repaint();
        this.pack();
    }


    private CarouselButton[] setMenu(JFrame frame) {
        /**
         * @author Morgan
         */
        File folder = new File(Constants.ZIP_FILE_PATH + "/");
        File[] list = folder.listFiles(new FileFilter() {
            @Override
            public boolean accept(File file) { return file.isDirectory(); }
        });

        if (list.length < 5) {
            //TODO: Handle Carousel Menu with less than 5 buttons
        }
        else if (list.length > 5) {
            //TODO: Handle Carousel Menu with more than 5 buttons
        }
        else {
            return menuHasFive(frame, new ArrayList<File>(Arrays.asList(list)));
        }
        return null;
    }

    private CarouselButton[] menuHasFive(JFrame frame, ArrayList<File> list) {
        CarouselButton[] buttons = new CarouselButton[5];
        for (int i = 0; i < list.size(); i++) {

            String zipName = list.get(i).getName();
            String zipPath = list.get(i).getPath();

            File folder = new File(Constants.ZIP_FILE_PATH + "/" +
                    zipName + "/");

            ArrayList<File> unzippedList = new ArrayList<File>(Arrays.asList(folder.listFiles()));

            String imagePath = Constants.DEFAULT_WHITE_IMAGE_PATH;
            for (int j = 0; j < unzippedList.size(); j++) {
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
                        IntendedTrack.setIntendedTrack(zipPath);
                        System.out.println(IntendedTrack.getIntendedTrack());
                        JOptionPane.showMessageDialog(null,
                                "Selected track has become:" + zipName
                                ,"Selection Info", JOptionPane.INFORMATION_MESSAGE);
                        frame.dispose();
                        MainModel model = new MainModel();
                        MainGuitarController controller1 = new MainGuitarController(model);
                        MainController controller2 = new MainController(model);
                        new MainView(model, controller2, controller1);

                    }
                };
            }
            catch (IOException e) {
                System.exit(0);
            }
        }
        return buttons;
    }

    private ArrayList<File> inputAllFiles() {
        File folder = new File(Constants.ZIP_FILE_PATH + "/");

        //TODO: Figure out why this validation check isnt working
//        for (int i = 0; i < list.size(); i++) {
//            int index = list.get(i).getName().lastIndexOf('.');
//            if (list.get(i).getName().substring(index) != "zip") {
//                list.remove(i);
//            }
//        }
        return new ArrayList<File>(Arrays.asList(folder.listFiles()));
    }

}
