/**
 * @author Morgan
 * Skeleton code of ControllerMain used to create class
 */

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class ControllerStore implements KeyListener {

    private ModelStore model;

    public ControllerStore(ModelStore model) {
        this.model = model;
    }

    public void keyTyped(KeyEvent e) { }

    public void keyReleased(KeyEvent e) { }

    public void keyPressed(KeyEvent evt) {
        switch(evt.getKeyCode()) {
            case KeyEvent.VK_ESCAPE :
                if(model.menuOpen) {
                    model.hideMenu();
                } else {
                    model.showMenu();
                }
                break;
        }
    }

    /**
     * @Author Morgan
     */
    public static class CarouselHandlerStore implements ActionListener {
        private JFrame frame;
        private CarouselButton button;
        private GuitarButtonController gbController;

        public CarouselHandlerStore(CarouselButton button, JFrame frame) {
            this.button = button;
            this.frame = frame;
        }

        public CarouselHandlerStore(CarouselButton button, JFrame frame, GuitarButtonController gbController) {
            this.button = button;
            this.frame = frame;
            this.gbController = gbController;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            //TODO: Select Mode Handling
        }
    }
}