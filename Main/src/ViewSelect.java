/**
 * @author Tom
 * Refactored for Select Mode from Slash Mode by @Morgan
 */
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import javax.imageio.ImageIO;
import javax.swing.*;

public class ViewSelect extends JFrame implements PropertyChangeListener {

    public static String intendedTrack = "";

    private ModelSelect model1;
    private ControllerSelect controller;
    private GuitarButtonController controller2;

    private JPanel panel;

    private ViewMain.guitar g;
    private CarouselMenu menu;

    public ViewSelect(ModelSelect model1, ControllerSelect controller, GuitarButtonController controller2) {
        this.model1 = model1;
        this.model1.addPropertyChangeListener(this);
        this.controller = controller;
        this.controller2 = controller2;

        this.g = new ViewMain.guitar(Constants.w,Constants.h);

        this.panel = new JPanel();
        this.panel.setPreferredSize(new Dimension(Constants.w,Constants.h));

        CarouselButton[] buttons = setMenu(this);
        this.menu = new CarouselMenu(buttons, 20, 400);
        model1.addPropertyChangeListener(menu);

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
            if (!model1.menuOpen) {
                model1.menuOpen = true;
                g.add(menu);
            } else if (model1.menuOpen) {
                model1.menuOpen = false;
                g.remove(menu);
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
        System.out.println(list.size());

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
                System.out.println(zipName);
                String[] strarr = zipName.split("\\.");
                System.out.println(strarr.length);
                //TODO: Carry on validation of this method from here once FileUnzipper issue is resolved
                FileUnzipper zip = new FileUnzipper(Constants.ZIP_FILE_PATH + "/"
                + zipName + "/" + strarr[0]);
                zip.unzipFiles(list.get(i));

                File folder = new File(Constants.ZIP_FILE_PATH + "/"
                        + zipName + "/");
                ArrayList<File> unzippedList = new ArrayList<File>(Arrays.asList(folder.listFiles()));
                File img = new File(Constants.DEFAULT_WHITE_IMAGE_PATH);
                for (int j = 0; j < 3; j++) {
                    int index = unzippedList.get(j).getName().lastIndexOf('.');
                    if (unzippedList.get(j).getName().substring(index + 1) == "png") {
                        img = unzippedList.get(j);
                        System.out.println(unzippedList.get(j).getName());
                        break;
                    }
                }
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
                            System.out.println(zipName);
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
        System.out.println(list.size());

//        for (int i = 0; i < list.size(); i++) {
//            int index = list.get(i).getName().lastIndexOf('.');
//            if (list.get(i).getName().substring(index) != "zip") {
//                list.remove(i);
//            }
//        }
        return list;
    }

}
