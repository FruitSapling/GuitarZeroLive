/**
 * @author Tom
 * Refactored for Select Mode from Slash Mode by @Morgan
 */

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class SelectController implements KeyListener {

    private SelectModel model;

    public SelectController(SelectModel model) {
        this.model = model;
    }

    public void keyTyped(KeyEvent e) {
    }

    public void keyReleased(KeyEvent e) {
    }

    public void keyPressed(KeyEvent evt) {
        if(evt.getKeyChar() == 'e') {
            model.cycleCarouselRight();
        }else if(evt.getKeyChar() == 'd') {
            model.cycleCarouselLeft();
        }
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
