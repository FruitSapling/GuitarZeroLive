import java.awt.Dimension;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import javax.swing.*;

public class ViewSelect extends JFrame implements PropertyChangeListener {

    private final int w = 750;
    private final int h = 1000;

    private ModelMain model1;
    private ControllerMain controller;
    private GuitarButtonController controller2;

    private JPanel panel;
    private ViewMain.guitar g;
    private CarouselMenu menu;

    public ViewSelect(ModelMain model1, ControllerMain controller, GuitarButtonController controller2) {
        this.model1 = model1;
        this.model1.addPropertyChangeListener(this);
        this.controller = controller;
        this.controller2 = controller2;

        this.g = new ViewMain.guitar(w, h);

        this.panel = new JPanel();
        this.panel.setPreferredSize(new Dimension(w, h));

        CarouselButton[] buttons = new CarouselButton[5];

        buttons[0] = new CarouselButton(Constants.EXIT_IMAGE_PATH) {
            @Override
            public void onClick() {
            }
        };
        buttons[1] = new CarouselButton(Constants.SELECT_IMAGE_PATH) {
            @Override
            public void onClick() {

            }
        };
        buttons[2] = new CarouselButton(Constants.PLAY_IMAGE_PATH) {
            @Override
            public void onClick() {

            }
        };
        buttons[3] = new CarouselButton(Constants.STORE_IMAGE_PATH) {
            @Override
            public void onClick() {

            }
        };
        buttons[4] = new CarouselButton(Constants.TUTORIAL_IMAGE_PATH) {
            @Override
            public void onClick() {

            }
        };

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
        if (!model1.menuOpen) {
            model1.menuOpen = true;
            g.add(menu);
            System.out.println("added menu");
        }
        else {
            this.g.remove(menu);
        }
        this.pack();
        this.revalidate();
        this.repaint();
    }

    public void setMenu() {
        CarouselButton[] buttons = new CarouselButton[5];
        buttons[0] = new CarouselButton(Constants.EXIT_IMAGE_PATH) {
            @Override
            public void onClick() {

            }
        };

        buttons[1] = new CarouselButton(Constants.SELECT_IMAGE_PATH) {
            @Override
            public void onClick() {
            }
        };

        buttons[2] = new CarouselButton(Constants.PLAY_IMAGE_PATH) {
            @Override
            public void onClick() {

            }
        };

        buttons[3] = new CarouselButton(Constants.STORE_IMAGE_PATH) {
            @Override
            public void onClick() {
            }
        };

        buttons[4] = new CarouselButton(Constants.TUTORIAL_IMAGE_PATH) {
            @Override
            public void onClick() {

            }
        };

        this.menu = new CarouselMenu(buttons, 20, 400);
    }
}

