import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * @author Tom Contributed to by: Morgan - Refactored for Store Mode from Slash Mode Willem - Made
 * the carousel menu work in Store Mode
 */
public class StoreController implements KeyListener {

  private StoreModel model;

  public StoreController(StoreModel model) {
    this.model = model;
  }

  public void keyTyped(KeyEvent e) {
  }

  public void keyReleased(KeyEvent e) {
  }

  public void keyPressed(KeyEvent evt) {
    if (evt.getKeyChar() == 'e') {
      model.cycleCarouselRight();
    } else if (evt.getKeyChar() == 'd') {
      model.cycleCarouselLeft();
    } else if (evt.getKeyChar() == 'q') {
      model.backMode();
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