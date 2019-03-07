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

    private SelectModel model1;
    private SelectController controller;
    private GuitarButtonController controller2;

    private JPanel panel;

    private MainView.guitar g;
    private CarouselMenu menu;

    public SelectView(SelectModel model1, SelectController controller, GuitarButtonController controller2) {
        this.model1 = model1;
        this.model1.addPropertyChangeListener(this);
        this.controller = controller;
        this.controller2 = controller2;

        this.g = new MainView.guitar(Constants.w,Constants.h);

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
                FileUnzipper zip = new FileUnzipper(Constants.ZIP_FILE_PATH + "/"
                + list.get(i).getName());
                zip.unzipFiles(list.get(i));

                File folder = new File(Constants.ZIP_FILE_PATH + "/"
                        + list.get(i).getName() + "/");
                ArrayList<File> unzippedList = new ArrayList<File>(Arrays.asList(folder.listFiles()));
                for (int j = 0; j < 3; j++) {
                    int index = unzippedList.get(j).getName().lastIndexOf('.');
                    if (unzippedList.get(j).getName().substring(index + 1) != "png") {
                        try {
                            Image img = ImageIO.read(list.get(j));
                            ImageIcon icon = new ImageIcon(img);
                        }
                        catch (IOException e) {
                            System.exit(0);
                        }
                    }
                }
            }
        }
        //TODO: Make Select Mode actually select mode
        CarouselButton[] buttons = new CarouselButton[5];
        for (int i = 0; i < 5; i++) {
            buttons[i] = new CarouselButton(Constants.EXIT_IMAGE_PATH, "Empty") {
                @Override public void onClick() {}
            };
        }
        return buttons;
    }

    public ArrayList<File> inputAllFiles() {
        File folder = new File(Constants.ZIP_FILE_PATH + "/");
        ArrayList<File> list = new ArrayList<File>(Arrays.asList(folder.listFiles()));

        for (int i = 0; i < list.size(); i++) {
            int index = list.get(i).getName().lastIndexOf('.');
            if (list.get(i).getName().substring(index + 1) != "zip") {
                list.remove(i);
            }
        }
        return list;
    }

}
