import java.awt.Dimension;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.*;

/**
 * @author Tom
 * Contributed to by:
 * Morgan - Refactored for Store Mode from Slash Mode
 * Willem - Making the carousel work in store mode, mock store mode carousel menu
 * Mark - Adding functionality from model to display the correct files
 */

public class StoreView extends JFrame implements PropertyChangeListener {

    private StoreModel model;
    private StoreController controller;
    private MainGuitarController controller2;

    private JPanel panel;

    private MainView.Guitar g;

    public StoreView(StoreModel model, StoreController controller, MainGuitarController controller2) {
        this.model = model;
        this.model.addPropertyChangeListener(this);
        this.controller = controller;
        this.controller2 = controller2;

        controller2.addListener(controller2);

        this.g = new MainView.Guitar(Constants.w,Constants.h);

        this.panel = new JPanel();
        this.panel.setPreferredSize(new Dimension(Constants.w,Constants.h));

        CarouselButton[] buttons = this.model.getFilesFromServer(0, this);
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
}
