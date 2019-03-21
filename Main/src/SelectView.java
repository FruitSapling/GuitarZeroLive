
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.*;

/**
 * @author Tom
 * Refactored for Select Mode from Slash Mode by @Morgan
 */

public class SelectView extends JFrame implements PropertyChangeListener {

    private SelectModel model;
    private SelectController controller;
    private SelectGuitarController controller2;

    private JPanel panel;

    private MainView.Guitar g;

    private CarouselMenu menu;

    public SelectView(SelectModel model, SelectController controller, SelectGuitarController controller2) {
        this.model = model;
        this.model.addPropertyChangeListener(this);
        this.controller = controller;
        this.controller2 = controller2();

        this.g = new MainView.Guitar(Constants.w,Constants.h);

        this.panel = new JPanel();
        this.panel.setPreferredSize(new Dimension(Constants.w,Constants.h));

        CarouselButton[] buttons = model.setMenu(this);
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
        else if (pce.getPropertyName() == "backMode") {
            model.returnToMenu(this);
        }
        this.revalidate();
        this.repaint();
        this.pack();
    }

}
