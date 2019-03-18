import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import javax.sound.midi.Instrument;

public class PlayController2 implements KeyListener {

  private PlayModel model;
  private Instrument g;

  public PlayController2(PlayModel model) {
    this.model = model;
    g = PlayGuitar.play(29);
  }

  public void keyTyped(KeyEvent e) { }
  public void keyReleased(KeyEvent e) { }

  public void keyPressed(KeyEvent e) {
    switch(e.getKeyCode()) {
      case KeyEvent.VK_SPACE:
        // TODO play midi sound of inputted note
//      try {
//        PlayGuitar.playNote(g,"G#",4,207);
//      } catch (InterruptedException ex) {
//        ex.printStackTrace();
//      }
//        model.strum();
        ArrayList<GuitarButton> buttonsPressed = new ArrayList<>();
        buttonsPressed.add(GuitarButton.WHITE_1);
        buttonsPressed.add(GuitarButton.WHITE_2);
        buttonsPressed.add(GuitarButton.WHITE_3);
        buttonsPressed.add(GuitarButton.BLACK_1);
        buttonsPressed.add(GuitarButton.BLACK_2);
        buttonsPressed.add(GuitarButton.BLACK_3);
        model.guitarStrummed(buttonsPressed);
        break;
    }
  }

}
