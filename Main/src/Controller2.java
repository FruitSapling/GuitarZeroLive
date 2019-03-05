import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Controller2 implements KeyListener {

  private Model model;
  public boolean playMode = false;

  public Controller2(Model model) {
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

