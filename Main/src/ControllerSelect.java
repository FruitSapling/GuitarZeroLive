/**
 * @author Morgan
 * Skeleton code of ControllerMain used to create class
 */

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class ControllerSelect implements KeyListener {

    private ModelSelect model;

    public ControllerSelect(ModelSelect model) {
        this.model = model;
    }

    public void keyTyped(KeyEvent e) {
    }

    public void keyReleased(KeyEvent e) {
    }

    public void keyPressed(KeyEvent evt) {
        switch (evt.getKeyCode()) {
            case KeyEvent.VK_ESCAPE:
                if (model.menuOpen) {
                    model.hideMenu();
                } else {
                    model.showMenu();
                }
                break;
        }
    }

}
