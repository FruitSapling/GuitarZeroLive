/**
 * @author Morgan
 * Skeleton code of ViewMain used to create class
 */
import java.awt.Dimension;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
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
        //TODO: Make Select Mode actually select mode
        CarouselButton[] buttons = new CarouselButton[5];
        for (int i = 0; i < 5; i++) {
            buttons[i] = new CarouselButton(Constants.EXIT_IMAGE_PATH, "Empty") {
                @Override public void onClick() {}
            };
        }
        return buttons;
    }
}
