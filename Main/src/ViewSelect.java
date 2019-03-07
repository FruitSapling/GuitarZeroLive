/**
 * @author Morgan
 * Skeleton code of ViewMain used to create class
 */
import java.awt.Dimension;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.*;

public class ViewSelect extends JFrame implements PropertyChangeListener {

    public static final int w = 750;
    public static final int h = 1000;

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

        this.g = new ViewMain.guitar(w,h);

        this.panel = new JPanel();
        this.panel.setPreferredSize(new Dimension(w,h));

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

        inputAllFilesTest();
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

    public void inputAllFilesTest() {
        File folder = new File(Constants.ZIP_FILE_PATH + "/");
        File[] list = folder.listFiles();
        int count = 0;
        for (int i = 0; i < list.length; i++){
            if (list[i] != null){
                count++;
            }
        }
        System.out.println(count);

        for (File file : list) {
            if (file.isFile()) {
                System.out.println(file.getName());
            }
        }
    }

}
