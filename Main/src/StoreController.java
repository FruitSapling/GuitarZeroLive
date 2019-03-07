/**
 * @author Tom
 * Refactored for Store Mode from Slash Mode by @Morgan
 */
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class StoreController implements KeyListener {

    private StoreModel model;

    public StoreController(StoreModel model) {
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
}