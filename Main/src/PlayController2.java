import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.sound.midi.Instrument;

public class PlayController2 implements KeyListener {

  private PlayModel model;

  public PlayController2(PlayModel model) {
    this.model = model;
  }

  public void keyTyped(KeyEvent e) { }
  public void keyReleased(KeyEvent e) { }

  public void keyPressed(KeyEvent e) {
    switch(e.getKeyCode()) {
      case KeyEvent.VK_SPACE:

        model.strum();
        break;
    }
  }

}
