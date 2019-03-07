import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * @author Tom
 */
public class ControllerMain implements KeyListener{

  private ModelMain model;
  public boolean playMode = false;

  public ControllerMain(ModelMain model) {
    this.model = model;
  }

  public void keyTyped(KeyEvent e) { }

  public void keyReleased(KeyEvent e) { }

  public void keyPressed(KeyEvent evt) {
    switch(evt.getKeyCode()) {
      case KeyEvent.VK_SPACE :
        if(playMode) {
          model.strummed();
        }
        break;
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

