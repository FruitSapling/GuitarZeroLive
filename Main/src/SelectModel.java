/**
 * @author Tom
 * Refactored for Select Mode from Slash Mode by @Morgan
 */

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class SelectModel {
    public boolean menuOpen;

    private PropertyChangeSupport support;
    public CarouselMenu carouselMenu;

    public SelectModel() {
        this.support = new PropertyChangeSupport(this);
        support.addPropertyChangeListener(carouselMenu);
    }

    public void setCarouselMenu(CarouselButton[] buttons) {
        this.carouselMenu = new CarouselMenu(buttons, 0, 400);
        carouselMenu.revalidate();
        carouselMenu.repaint();
    }

    public void cycleCarouselLeft() {
        this.support.firePropertyChange("cycleCarousel", null, "left");
    }

    public void cycleCarouselRight() {
        this.support.firePropertyChange("cycleCarousel", null, "right");
    }

    public void selectMode() {
        this.support.firePropertyChange("selectMode", null, null);
    }

    public void addPropertyChangeListener(PropertyChangeListener pcl) {
        this.support.addPropertyChangeListener(pcl);
    }

    public void showMenu() {
        this.support.firePropertyChange(null,null,null);
    }
    public void hideMenu() {
        this.support.firePropertyChange(null,null,null);
    }

    CarouselButton[] setMenu(JFrame frame) {
        /**
         * @author Morgan
         */
        File folder = new File(Constants.ZIP_FILE_PATH + "/");
        File[] list = folder.listFiles(new FileFilter() {
            @Override
            public boolean accept(File file) { return file.isDirectory(); }
        });

        CarouselButton[] buttons;
        ArrayList<File> arrayList = new ArrayList<File>(Arrays.asList(list));

        if (list.length < 5) {
            buttons = new CarouselButton[5];
            int surplus = 5 - (5-list.length);

            for (int i = 0; i < list.length; i++) {
                buttons[i] = createMeaningfulButton(frame, arrayList, i);
            }
            for (int i = surplus; i < 5; i++) {
                buttons[i] = createEmptyButton();
            }
        }

        else {
            buttons = new CarouselButton[list.length];
            for (int i = 0; i < list.length; i++) {
                buttons[i] = createMeaningfulButton(frame, arrayList, i);
            }
        }
        return buttons;
    }

    CarouselButton createMeaningfulButton(JFrame frame, ArrayList<File> list, int count) {
        /**
         * @author Morgan
         */
        String zipName = list.get(count).getName();
        String zipPath = "";

        File folder = new File(Constants.ZIP_FILE_PATH + "/" + zipName + "/");
        ArrayList<File> unzippedList = new ArrayList<>(Arrays.asList(folder.listFiles()));
        String imagePath = Constants.DEFAULT_WHITE_IMAGE_PATH;

        for (int j = 0; j < unzippedList.size(); j++) {
            if (unzippedList.get(j).getName().split("\\.")[1].equals("jpg")) {
                imagePath = unzippedList.get(j).getPath();
            }
            if (unzippedList.get(j).getName().split("\\.")[1].equals("midnotes")) {
                zipPath = unzippedList.get(j).getPath();
            }
        }
        File img = new File(imagePath);
        final String intendedTrack = zipPath;

        try {
            Image image = ImageIO.read(img);
            Image newImage = image.getScaledInstance(Constants.BUTTON_WIDTH, Constants.BUTTON_HEIGHT, Image.SCALE_DEFAULT);
            ImageIcon icon = new ImageIcon(newImage);

            return new CarouselButton(icon, zipName) { @Override public void onClick() {
                if (IntendedTrack.getIntendedTrack().equals("")) {
                    JOptionPane.showMessageDialog(null, "Error - No Track Selected",
                            "Empty Button", JOptionPane.WARNING_MESSAGE);
                }
                else {
                    IntendedTrack.setIntendedTrack(intendedTrack.replace("\\", "/"));
                    System.out.println(IntendedTrack.getIntendedTrack());
                    JOptionPane.showMessageDialog(null, "Selected track has become: " + zipName,
                            "Selection Info", JOptionPane.INFORMATION_MESSAGE);
                }

                frame.dispose();
                MainModel model = new MainModel();
                MainController controller1 = new MainController(model);
                MainGuitarController controller2 = new MainGuitarController(model);
                new MainView(model, controller1, controller2);
            }
            };
        }
        catch (IOException e) {
            System.exit(2);
        }
        return null;
    }

    CarouselButton createEmptyButton() {
        /**
         * @author Morgan
         */
        String imagePath = Constants.DEFAULT_WHITE_IMAGE_PATH;
        File img = new File(imagePath);

        try {
            Image image = ImageIO.read(img);
            Image newImage = image.getScaledInstance(Constants.BUTTON_WIDTH, Constants.BUTTON_HEIGHT, Image.SCALE_DEFAULT);
            ImageIcon icon = new ImageIcon(newImage);

            return new CarouselButton(icon, "empty") {
                @Override
                public void onClick() {
                    JOptionPane.showMessageDialog(null, "Nothing to see here...",
                            "Empty Button", JOptionPane.WARNING_MESSAGE);
                }
            };
        }
        catch (IOException e) {
            System.exit(2);
        }
        return null;
    }

    //    private ArrayList<File> inputAllFiles() {
//        File folder = new File(Constants.ZIP_FILE_PATH + "/");
//
//        //TODO: Figure out why this validation check isnt working
////        for (int i = 0; i < list.size(); i++) {
////            int index = list.get(i).getName().lastIndexOf('.');
////            if (list.get(i).getName().substring(index) != "zip") {
////                list.remove(i);
////            }
////        }
//        return new ArrayList<File>(Arrays.asList(folder.listFiles()));
//    }
}
