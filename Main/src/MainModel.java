import javax.swing.*;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

/**
 * @author Tom
 * Contributed to by:
 * Willem - All the code to update the carousel menu
 */
public class MainModel {

  public boolean menuOpen;

  private PropertyChangeSupport support;
  public CarouselMenu carouselMenu;

  public MainModel() {
    this.support = new PropertyChangeSupport(this);
    support.addPropertyChangeListener(carouselMenu);
    support.firePropertyChange("modelConstructed", null, null);
  }

  public void setCarouselMenu(CarouselButton[] buttons) {
    this.carouselMenu = new CarouselMenu(buttons, 0, 400);
    carouselMenu.revalidate();
    carouselMenu.repaint();
  }

  public void cycleCarouselLeft() {
    this.support.firePropertyChange("cycleCarousel", null, "left");
  }

  public void cycleCarouselRight() {
    this.support.firePropertyChange("cycleCarousel", null, "right");
  }

  public void selectMode() {
    this.support.firePropertyChange("selectMode", null, null);
  }

  public void addPropertyChangeListener(PropertyChangeListener pcl) {
    this.support.addPropertyChangeListener(pcl);
    support.firePropertyChange("modelConstructed", null, null);
  }

  public void showMenu() {
    this.support.firePropertyChange(null,null,null);
  }
  public void hideMenu() {
    this.support.firePropertyChange(null,null,null);
  }
  public void strummed() {
  }

  public CarouselButton[] setMenu(JFrame frame) {
    CarouselButton[] buttons = new CarouselButton[5];

    buttons[0] = new CarouselButton(Constants.EXIT_IMAGE_PATH, "Exit") {
      @Override public void onClick() {
        System.exit(0);
      }
      @Override public void onHighlight() {}
    };

    buttons[1] = new CarouselButton(Constants.SELECT_IMAGE_PATH, "Select") {
      @Override public void onClick() {
        frame.dispose();
        SelectModel model = new SelectModel();
        SelectController controller = new SelectController(model);
        SelectGuitarController controller2 = new SelectGuitarController(model);
        SelectView view = new SelectView(model, controller, controller2);

        // Hot fix for carousel bug.
        model.menuOpen = true;
        model.showMenu();
        model.showMenu();

        view.requestFocusInWindow();
      }
      @Override public void onHighlight() {}
    };

    buttons[2] = new CarouselButton(Constants.PLAY_IMAGE_PATH, "Play") {
      @Override public void onClick() {
        if (IntendedTrack.getIntendedTrack().equals("")) {
          JOptionPane.showMessageDialog(null, "You must select a track before attempting to play!"
                  , "Selection Error", JOptionPane.ERROR_MESSAGE);
        }
        else {
          frame.dispose();
          PlayModel model = new PlayModel();
          PlayController2 controller2 = new PlayController2(model);
          PlayGuitarController guitarController = new PlayGuitarController(model);
          PlayView view = new PlayView(model, controller2, guitarController);
          PlayController1 controller = new PlayController1(model);
        }
      }
      @Override public void onHighlight() {}
    };

    buttons[3] = new CarouselButton(Constants.STORE_IMAGE_PATH, "Store") {
      @Override public void onClick() {
        frame.dispose();
        StoreModel model = new StoreModel();
        StoreController controller = new StoreController(model);
        StoreGuitarController guitarController = new StoreGuitarController(model);
        StoreView view = new StoreView(model, controller, guitarController);

        // Hot fix for carousel bug.
        model.menuOpen = true;
        model.showMenu();
        model.showMenu();

        view.requestFocusInWindow();
      }
      @Override public void onHighlight() {}
    };

    buttons[4] = new CarouselButton(Constants.TUTORIAL_IMAGE_PATH, "Tutorial") {
      @Override public void onClick() {
        frame.dispose();
        TutorialModel model = new TutorialModel();
        TutorialController controller = new TutorialController(model);
        new TutorialView(model, controller);
      }
      @Override public void onHighlight() {}
    };

    return buttons;
  }
}

