/**
 * @author Tom
 * Refactored for Store Mode from Slash Mode by @Morgan
 */
import java.awt.Dimension;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.*;

public class ViewStore extends JFrame implements PropertyChangeListener {

    private ModelStore model1;
    private ControllerStore controller;
    private GuitarButtonController controller2;

    private JPanel panel;

    private ViewMain.guitar g;
    private CarouselMenu menu;

    public ViewStore(ModelStore model1, ControllerStore controller, GuitarButtonController controller2) {
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
        CarouselButton[] buttons = new CarouselButton[5];

        buttons[0] = new CarouselButton(Constants.EXIT_IMAGE_PATH, "Exit") {
            @Override public void onClick() { }
        };

        buttons[1] = new CarouselButton(Constants.SELECT_IMAGE_PATH, "Select") {
            @Override public void onClick() { }
        };

        buttons[2] = new CarouselButton(Constants.PLAY_IMAGE_PATH, "Play") {
            @Override public void onClick() { }
        };

        buttons[3] = new CarouselButton(Constants.STORE_IMAGE_PATH, "Store") {
            @Override public void onClick() { }
        };

        buttons[4] = new CarouselButton(Constants.TUTORIAL_IMAGE_PATH, "Tutorial") {
            @Override public void onClick() { }
        };

        return buttons;
    }
}
