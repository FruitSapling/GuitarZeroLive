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
  }

  public void showMenu() {
    this.support.firePropertyChange(null,null,null);
  }
  public void hideMenu() {
    this.support.firePropertyChange(null,null,null);
  }
  public void strummed() {
    System.out.println("Strum Me Daddy");
  } //TODO: Take this out on friday ;)
  public void down() {

  }
}

