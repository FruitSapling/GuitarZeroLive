import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

  /**
   * @Author Morgan
   */
  public static class CarouselHandlerMain implements ActionListener {
    private JFrame frame;
    private CarouselButton button;
    private GuitarButtonController gbController;

    public CarouselHandlerMain(CarouselButton button, JFrame frame){
      this.button = button;
      this.frame = frame;
    }

    public CarouselHandlerMain(CarouselButton button, JFrame frame, GuitarButtonController gbController){
      this.button = button;
      this.frame = frame;
      this.gbController = gbController;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
      if (button.getButtonName() == "Exit") {
        System.exit(0);
      }
      if (button.getButtonName() == "Select") {
        frame.dispose();
        ModelSelect model = new ModelSelect();
        ControllerSelect controller = new ControllerSelect(model);
        new ViewSelect(model, controller, gbController);
      }
      if (button.getButtonName() == "Play") {
        frame.dispose();
        PlayModel model = new PlayModel();
        new PlayView(model);
      }
      if (button.getButtonName() == "Store") {
        frame.dispose();
        ModelStore model = new ModelStore();
        ControllerStore controller = new ControllerStore(model);
        new ViewStore(model, controller, gbController);
      }
      if (button.getButtonName() == "Tutorial") {
        //TODO: Tutorial Mode Handler
      }
    }
  }

}

